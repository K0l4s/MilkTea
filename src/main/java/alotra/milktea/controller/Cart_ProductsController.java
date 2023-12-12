package alotra.milktea.controller;

import alotra.milktea.entity.*;

import alotra.milktea.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class Cart_ProductsController {
    @Autowired
    ICartProductsService cartProductsService = new CartProductsServiceImpl();
    @Autowired
    ICartService cartService = new CartServiceImpl();
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @Autowired
    IUserService userService = new UserServiceImpl();
    @Autowired
    IBillService billService = new BillServiceImpl();
    @Autowired
    IBill_ProductsService billProductsService = new Bill_ProductsServiceImpl();
    @GetMapping("/cart_products")
    public String findAll(Model model){
        model.addAttribute("list",cartProductsService.findAll());
        return "web/cart/list";
    }
    @GetMapping("/admin/cart_products/add")
    public String addCart_Products(Model model){
        CartProducts cp = new CartProducts();
        model.addAttribute("list",cp);
        return "/cart_products/add";
    }
//    @PostMapping("/cart_products/save")
//    public String saveCart_Products(@ModelAttribute("list") CartProducts cp){
//        cartProductsService.saveCartPro(cp);
//        return "redirect:/admin/cart_products";
//    }
    @GetMapping("/admin/cart_products/edit/{id}")
    public String editCart_Products(@PathVariable("id") int id, Model model){
        Optional<CartProducts> cp = cartProductsService.findOne(id);
        if(cp.isPresent()){
            model.addAttribute("list",cp);
            return "cart_products/edit";
        }
        return "error";
    }
    @GetMapping("/admin/cart_products/delete/{id}")
    public String deleteCart_products(@PathVariable int id){
        cartProductsService.deleteCartPro(id);
        return "redirect:/admin/cart_products";
    }

//    User
    @GetMapping("/user/cart/view/cart_products/add")
    public String addCartProByUser(@RequestParam("cartID")int id, Model model){
        CartProducts cp = new CartProducts();
        cp.setCart(cartService.findByID(id));
        model.addAttribute("products", productService.findAllByStatusNot((short) 0));
        model.addAttribute("list",cp);
        return "/cart_products/add";
    }

    @GetMapping("/user/cart/view/cart_products/edit/{id}")
    public String editCart_ProductsByUser(@PathVariable("id") int id, Model model){
        Optional<CartProducts> cp = cartProductsService.findOne(id);
        if(cp.isPresent()){
            model.addAttribute("list",cp);
            model.addAttribute("products", productService.findAllByStatusNot((short) 0));

            return "cart_products/edit";
        }
        return "error";
    }
    @GetMapping("/user/cart/view/cart_products/delete/{id}")
    public String deleteCart_productsByUser(@PathVariable("id") int id){
        CartProducts cp = cartProductsService.findCartProductsById(id);
        int cartID = cp.getCart().getId();

        cartProductsService.deleteCartPro(id);

        return "redirect:/user/cart/view?id=" + cartID;
    }
    @PostMapping("/cart_products/save")
    public String saveCart_Products_User(@ModelAttribute("list") CartProducts cp){
        cartProductsService.saveCartPro(cp);
        int id = cp.getCart().getId();
        return "redirect:/user/cart/view?id=" + id;
    }
    @GetMapping("/detail_cart")
    public String viewCartProductsByUser(Model model, HttpServletRequest request) {
        // Lấy danh sách các cookie từ request
        Cookie[] cookies = request.getCookies();

        // Nếu danh sách cookie không null, lặp qua để tìm cookie có tên "username"
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    String username = cookie.getValue();

                    // Thực hiện các xử lý với giá trị username
                    // Ví dụ: Kiểm tra nếu username không rỗng thì tiếp tục xử lý
                    if (!username.isEmpty()) {
                        // Lấy thông tin người dùng từ username
                        User user = userService.findOne(username);

                        // Kiểm tra nếu người dùng tồn tại và có thông tin khách hàng
                        if (user != null && user.getCustomer() != null) {
                            Customer customer = user.getCustomer();

                            // Lấy giỏ hàng của khách hàng
                            Cart userCart = cartService.findCartByCustomer(customer);

                            // Nếu giỏ hàng tồn tại, lấy danh sách sản phẩm trong giỏ hàng
                            if (userCart != null) {
                                List<CartProducts> cartProducts = cartProductsService.findProByCartID(userCart.getId());
                                int totalAmount = cartProducts.stream().mapToInt(CartProducts::getAmount).sum();

                                // Tính tổng tiền
                                double total = calculateTotal(cartProducts);

                                model.addAttribute("products", productService.findAllByStatusNot((short) 0));
                                model.addAttribute("list", cartProducts);

                                model.addAttribute("total", total);

                                model.addAttribute("totalAmount", totalAmount);
                                return "web/cart/list";
                            } else {
                                // Xử lý khi giỏ hàng không tồn tại
                                return "redirect:/home";
                            }
                        }
                    }
                }
            }
        }
        // Xử lý khi không tìm thấy cookie hoặc không có giá trị
        return "redirect:/login";
    }
    @GetMapping("detail_cart/increase/{id}")
    public String increaseAmount(@PathVariable("id") int id) {
        // Lấy thông tin sản phẩm trong giỏ hàng cần tăng số lượng
        Optional<CartProducts> cartProductOptional = cartProductsService.findOne(id);

        if (cartProductOptional.isPresent()) {
            CartProducts cartProduct = cartProductOptional.get();

            // Tăng số lượng
            cartProduct.setAmount(cartProduct.getAmount() + 1);

            // Lưu lại thông tin sản phẩm
            cartProductsService.saveCartPro(cartProduct);
        }

        return "redirect:/detail_cart";
    }
    @GetMapping("detail_cart/decrease/{id}")
    public String decreaseAmount(@PathVariable("id") int id, Model model) {
        // Lấy thông tin sản phẩm trong giỏ hàng cần giảm số lượng
        Optional<CartProducts> cartProductOptional = cartProductsService.findOne(id);

        if (cartProductOptional.isPresent()) {
            CartProducts cartProduct = cartProductOptional.get();

            // Kiểm tra số lượng không dưới 1 trước khi giảm
            if (cartProduct.getAmount() > 1) {
                // Giảm số lượng
                cartProduct.setAmount(cartProduct.getAmount() - 1);

                // Lưu lại thông tin sản phẩm
                cartProductsService.saveCartPro(cartProduct);
                model.addAttribute("message", "Đã cập nhật số lượng.");
            }
            else {
                String message = "Số lượng không thể ít hơn 1.";
                model.addAttribute("message", message);
                System.out.println(message); // Add this line for debugging
            }
        }

        return "redirect:/detail_cart";
    }

    @GetMapping("/detail_cart/delete/{id}")
    public String deleteCart_Products(@PathVariable int id){
        cartProductsService.deleteCartPro(id);
        return "redirect:/detail_cart";
    }

    // Hàm tính tổng số tiền từ danh sách sản phẩm trong giỏ hàng
    private double calculateTotal(List<CartProducts> cartProducts) {
        double total = 0;
        for (CartProducts cartProduct : cartProducts) {
            double subtotal = cartProduct.getAmount() * cartProduct.getProduct().getPrice();
            total += subtotal;
        }
        return total;
    }

    @GetMapping("/checkout")
    public String payCart(Model model, HttpServletRequest request) {
        return "/payment/payment";
    }

    @GetMapping("/confirm")
    public String comfirmPayment(Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    String username = cookie.getValue();

                    if (!username.isEmpty()) {
                        User user = userService.findOne(username);

                        if (user != null && user.getCustomer() != null) {
                            Customer customer = user.getCustomer();

                            Bill bill = new Bill();
                            bill.setCreateDay(LocalDateTime.now());

                            // Set thông tin khách hàng
                            bill.setCustomer(customer);

                            billService.saveBill(bill);

                            // Lấy giỏ hàng của khách hàng
                            Cart userCart = cartService.findCartByCustomer(customer);

                            // Nếu giỏ hàng tồn tại, lấy danh sách sản phẩm trong giỏ hàng
                            if (userCart != null) {
                                List<CartProducts> cartProductsList = cartProductsService.findProByCartID(userCart.getId());
                                for (CartProducts cartProduct : cartProductsList) {
                                    Bill_Products bp =  new Bill_Products();
                                    bp.setProduct(cartProduct.getProduct());
                                    bp.setAmount(cartProduct.getAmount());
                                    bp.setBill(bill);

                                    billProductsService.saveBill_Products(bp);
                                }
                            }

                            return "redirect:/product"; // Hoặc trả về trang thành công sau khi đã tạo bill thành công
                        }
                    }
                }
            }
        }
        return "redirect:/home"; // Xử lý khi không tìm thấy cookie hoặc không có giá trị
    }
}
