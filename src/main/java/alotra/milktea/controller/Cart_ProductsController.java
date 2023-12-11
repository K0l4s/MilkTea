package alotra.milktea.controller;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.CartProducts;

import alotra.milktea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class Cart_ProductsController {
    @Autowired
    ICartProductsService cartProductsService = new CartProductsServiceImpl();
    @Autowired
    ICartService cartService = new CartServiceImpl();
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @GetMapping("/admin/cart_products")
    public String findAll(Model model){
        model.addAttribute("list",cartProductsService.findAll());
        return "cart_products/list";
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
}
