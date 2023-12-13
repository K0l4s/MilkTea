package alotra.milktea.controller;

import alotra.milktea.entity.*;
import alotra.milktea.model.ProductModel;
import alotra.milktea.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Controller
public class ProductController {
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @Autowired
    ICategoryService categoryService = new CategoryServiceImpl();
    @Autowired
    IStorageService storageService;
    @Autowired
    ICartService cartService;
    @Autowired
    ICartProductsService cartProductsService;
    @Autowired
    IUserService userService;
    @Autowired
    private ViewResolver thymeleafViewResolver;
    @GetMapping("/admin/product")
    public String findAll(Model model){
        model.addAttribute("products",productService.findAllByStatusNot((short) 0));
        return "admin/product/list";
    }
    @GetMapping("/admin/product/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Product> product = productService.findOne(id);
        if(product.isPresent()){
            model.addAttribute("categories", categoryService.findAllByStatusNot((short) 0));
            model.addAttribute("product", product.get());
            return "admin/product/edit";
        }
        return"error";
    }
    @GetMapping("/admin/product/add")
    public String addProduct(Model model){
        Product pro = new Product();
        model.addAttribute("categories", categoryService.findAllByStatusNot((short) 0));
        model.addAttribute("product",pro);
        return "/admin/product/add";
    }
    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute("product") ProductModel proModel){
        Product entity = new Product();
        //Copy model sang entity
        BeanUtils.copyProperties(proModel, entity);

        Category cateEntity = new Category();
        cateEntity.setCategoryID(proModel.getCategory().getCategoryID());
        entity.setCategory(cateEntity);

        //Kiểm tra file
        if (!proModel.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            //Lưu file vào ImageURL
            entity.setImageURL(storageService.getStorageFilename(proModel.getImageFile(), uuString));
            storageService.store(proModel.getImageFile(), entity.getImageURL());
        }
        productService.saveProduct(entity);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.DeleteProduct(id);
        return "redirect:/admin/product";
    }
    @GetMapping("/product/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping("/admin/product/search")
    public String searchProByName(@RequestParam("name") String name,Model model){
        if (name != "") {
            model.addAttribute("name", name);
            model.addAttribute("products",productService.findProductByName(name));
            return "/product/list";
        }
        else {
            model.addAttribute("products",productService.findAll());
            return "redirect:/admin/product";
        }
    }
    @GetMapping("/product")
    public String findAllWeb(Model model, HttpServletRequest request){
        model.addAttribute("categories", categoryService.findAllByStatusNot((short) 0));

        // Lấy danh sách các cookie từ request
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    String username = cookie.getValue();

                    if (!username.isEmpty()) {
                        // Lấy thông tin người dùng từ username
                        User user = userService.findOne(username);

                        // Kiểm tra nếu người dùng tồn tại và có thông tin khách hàng
                        if (user != null && user.getCustomer() != null) {
                            Customer customer = user.getCustomer();

                            Cart userCart = cartService.findCartByCustomer(customer);

                            // Nếu giỏ hàng tồn tại, thêm cartId vào Model
                            if (userCart != null) {
                                List<CartProducts> cartProducts = cartProductsService.findProByCartID(userCart.getId());
                                int totalAmount = cartProducts.stream().mapToInt(CartProducts::getAmount).sum();

                                model.addAttribute("totalAmount", totalAmount);
                                model.addAttribute("cartId", userCart.getId());
                            }
                        }
                    }
                }
            }
        }

        model.addAttribute("products",productService.getProducts(0, 8));
        return "web/product/list";
    }
    @GetMapping("/loadMoreProducts")
    @ResponseBody
    public List<Product> loadMoreProducts(@RequestParam int offset, @RequestParam int limit) {
        // Sử dụng offset và limit để truy vấn sản phẩm từ db
        return productService.getProducts(offset, limit);
    }
    @PostMapping("/addToCart/add")
    @ResponseBody
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam("cartId") int cartId,
                            @RequestParam("amount") int amount, HttpServletRequest request, Model model) {

        try {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        String username = cookie.getValue();

                        if (!username.isEmpty()) {
                            // Lấy thông tin người dùng từ username
                            User user = userService.findOne(username);

                            // Kiểm tra nếu người dùng tồn tại và có thông tin khách hàng
                            if (user != null && user.getCustomer() != null) {
                                Customer customer = user.getCustomer();

                                Cart userCart = cartService.findCartByCustomer(customer);

                                // Nếu giỏ hàng tồn tại, thêm cartId vào Model
                                if (userCart != null) {
                                    model.addAttribute("cartId", userCart.getId());
                                }
                            }
                        }
                    }
                }
            }
            // Find the cart and product
            Cart cart = cartService.findByID(cartId);
            Optional<Product> product = productService.findOne(productId);

            // Check if both cart and product exist
            if (cart != null && product.isPresent()) {
                // Check if the product is already in the cart
                Optional<CartProducts> existingProduct = cartProductsService.findByCartAndProduct(cart, product.get());

                if (existingProduct.isPresent()) {
                    // If the product already exists, update the quantity
                    existingProduct.get().setAmount(existingProduct.get().getAmount() + amount);
                    cartProductsService.saveCartPro(existingProduct.get());
                } else {
                    // If the product isn't in the cart, create a new
                    CartProducts newProduct = new CartProducts();
                    newProduct.setCart(cart);
                    newProduct.setProduct(product.get());
                    newProduct.setAmount(amount);
                    cartProductsService.saveCartPro(newProduct);
                }

                return "Product added to cart successfully";
            } else {
                return "Error: Cart or product not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error adding product to cart";
        }
    }
    @GetMapping("/getProductDetails/{productId}")
    public String getProductDetails(@PathVariable int productId, Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    String username = cookie.getValue();

                    if (!username.isEmpty()) {
                        // Lấy thông tin người dùng từ username
                        User user = userService.findOne(username);

                        // Kiểm tra nếu người dùng tồn tại và có thông tin khách hàng
                        if (user != null && user.getCustomer() != null) {
                            Customer customer = user.getCustomer();

                            Cart userCart = cartService.findCartByCustomer(customer);

                            // Nếu giỏ hàng tồn tại, thêm cartId vào Model
                            if (userCart != null) {
                                List<CartProducts> cartProducts = cartProductsService.findProByCartID(userCart.getId());
                                int totalAmount = cartProducts.stream().mapToInt(CartProducts::getAmount).sum();
                                model.addAttribute("totalAmount", totalAmount);
                                model.addAttribute("cartId", userCart.getId());
                            }
                        }
                    }
                }
            }
        }
        Optional<Product> product = productService.findOne(productId);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "web/product/detail";
        }
        return "error";
    }
    @RequestMapping(value = "/product/searchPaginated", method = {RequestMethod.GET, RequestMethod.POST})
    public String search(Model model, HttpServletRequest request,
                         @RequestParam(name = "searchTerm", required = false, defaultValue = "") String searchTerm,
                         @RequestParam(name = "category", required = false, defaultValue = "") String categoryId,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "8") int size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    String username = cookie.getValue();

                    if (!username.isEmpty()) {
                        // Lấy thông tin người dùng từ username
                        User user = userService.findOne(username);

                        // Kiểm tra nếu người dùng tồn tại và có thông tin khách hàng
                        if (user != null && user.getCustomer() != null) {
                            Customer customer = user.getCustomer();

                            Cart userCart = cartService.findCartByCustomer(customer);

                            // Nếu giỏ hàng tồn tại, thêm cartId vào Model
                            if (userCart != null) {
                                List<CartProducts> cartProducts = cartProductsService.findProByCartID(userCart.getId());
                                int totalAmount = cartProducts.stream().mapToInt(CartProducts::getAmount).sum();
                                model.addAttribute("totalAmount", totalAmount);
                                model.addAttribute("cartId", userCart.getId());
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("categories", categoryService.findAllByStatusNot((short) 0));
        Page<Product> products = null;
        if (!searchTerm.isEmpty() && !categoryId.isEmpty()) {
            Optional<Category> category = categoryService.findOne(Integer.parseInt(categoryId));
            if (category.isPresent()) {
                products = productService.searchProductsByCategoryAndName(searchTerm, category.get(), (short) 0, PageRequest.of(page, size));
            }
        } else if (!categoryId.isEmpty()) {
            Optional<Category> category = categoryService.findOne(Integer.parseInt(categoryId));
            if (category.isPresent()) {
                products = productService.searchProductsByCategory(searchTerm, category.get(), (short) 0, PageRequest.of(page, size));
            }
        } else {
            products = productService.searchProducts(searchTerm, (short) 0, PageRequest.of(page, size));
        }
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("selectedCategory", categoryId);

        return "/web/product/search";
    }
}
