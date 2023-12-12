package alotra.milktea.controller;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.CartProducts;

import alotra.milktea.entity.Customer;
import alotra.milktea.entity.User;
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

                                model.addAttribute("products", productService.findAllByStatusNot((short) 0));
                                model.addAttribute("list", cartProducts);
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
}
