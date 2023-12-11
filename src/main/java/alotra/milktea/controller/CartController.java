package alotra.milktea.controller;

import alotra.milktea.entity.Cart;
import alotra.milktea.service.CartProductsServiceImpl;
import alotra.milktea.service.CartServiceImpl;
import alotra.milktea.service.ICartProductsService;
import alotra.milktea.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    ICartService cartService = new CartServiceImpl();
    @Autowired
    ICartProductsService cartProductsService = new CartProductsServiceImpl();
    @GetMapping("/admin/cart")
    public String findAll(Model model){
        model.addAttribute("carts",cartService.findAll());
        return "/cart/list";
    }
    @GetMapping("/admin/cart/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Cart> cart = cartService.findOne(id);
        if(cart.isPresent()){
            model.addAttribute("cart",cart.get());
            return "/cart/edit";
        }
        return "error";
    }
    @GetMapping("/admin/cart/add")
    public String addCart(Model model){
        Cart cart = new Cart();
        model.addAttribute("cart",cart);
        return "/cart/add";
    }
    @PostMapping("/cart/save")
    public String saveCart(@ModelAttribute("cart") Cart cart){
        cartService.saveCart(cart);
        return "redirect:/admin/cart";
    }
    @GetMapping("/admin/cart/delete/{id}")
    public String deleteCart(@PathVariable("id") int id){
        cartService.deleteCart(id);
        return "redirect:/admin/cart";
    }
//  User
    @GetMapping("/user/cart")
    public String findAll_User(@RequestParam("id") int id, Model model){
        model.addAttribute("carts",cartService.finCartByCustomerID(id));
        return "/cart/list";
    }
    @GetMapping("/user/cart/view")
    public String DetailsCart(@RequestParam("id") int id, Model model){
        model.addAttribute("list",cartProductsService.findProByCartID(id));
        model.addAttribute("cartID",id);
        return "/cart_products/view";
    }
}
