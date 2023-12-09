package alotra.milktea.controller;

import alotra.milktea.entity.Shop;
import alotra.milktea.service.IShopService;
import alotra.milktea.service.ShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ShopController {
    @Autowired
    IShopService shopService = new ShopServiceImpl();
    @GetMapping("/admin/shop")
    public String findAll(Model model){
        model.addAttribute("shops", shopService.findAll());
        return "shop/list";
    }
    @GetMapping("/admin/shop/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Shop> shop = shopService.findOne(id);
        if(shop.isPresent()){
            model.addAttribute("shop",shop.get());
            return "shop/edit";
        }
        return "error";
    }
    @GetMapping("/admin/shop/add")
    public String addShop(Model model){
        Shop shop = new Shop();
        model.addAttribute("shop",shop);
        return "/shop/add";
    }
    @PostMapping("/shop/save")
    public String saveShop(@ModelAttribute("shop") Shop shop){
        shopService.saveShop(shop);
        return "redirect:/admin/shop";
    }
    @GetMapping("/admin/shop/delete/{id}")
    public String deleteShop(@PathVariable("id") int id){
        shopService.deleteShop(id);
        return "redirect:/admin/shop";
    }
}
