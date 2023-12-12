package alotra.milktea.controller;

import alotra.milktea.entity.CartProducts;

import alotra.milktea.service.CartProductsServiceImpl;
import alotra.milktea.service.ICartProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class Cart_ProductsController {
    @Autowired
    ICartProductsService cartProductsService = new CartProductsServiceImpl();
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
    @PostMapping("/cart_products/save")
    public String saveCart_Products(@ModelAttribute("list") CartProducts cp){
        cartProductsService.saveCartPro(cp);
        return "redirect:/admin/cart_products";
    }
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
}
