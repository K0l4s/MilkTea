package alotra.milktea.controller;

import alotra.milktea.entity.Shop;
import alotra.milktea.service.IShopService;
import alotra.milktea.service.ShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ShopController {
    @Autowired
    IShopService shopService = new ShopServiceImpl();
    @GetMapping("/admin/shop")
    public String findAll(Model model){
        model.addAttribute("shops", shopService.findAllBySatusNot((short) 0));
        return "admin/shop/list";
    }
    @GetMapping("/admin/shop/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Shop> shop = shopService.findOne(id);
        if(shop.isPresent()){
            model.addAttribute("shop",shop.get());
            return "admin/shop/edit";
        }
        return "error";
    }
    @GetMapping("/admin/shop/add")
    public String addShop(Model model){
        Shop shop = new Shop();
        model.addAttribute("shop",shop);
        return "admin/shop/add";
    }
    @PostMapping("/shop/update")
    public String updateShop(@ModelAttribute("shop") Shop shop){
        shopService.saveShop(shop);
        return "redirect:/admin/shop";
    }
    @PostMapping("/shop/save")
    public String saveShop(@ModelAttribute("shop") Shop shop){
        if (!shopService.isShopIdUnique(shop.getShopID())) {
            return "error";
        } else {
            shopService.saveShop(shop);
            return "redirect:/admin/shop";
        }
    }
    @GetMapping("/admin/shop/delete/{id}")
    public String deleteShop(@PathVariable("id") int id){
        shopService.deleteShop(id);
        return "redirect:/admin/shop";
    }
    @GetMapping("/admin/shop/search")
    public String searchProByCartName(@RequestParam("name") String name, Model model){
        if (name != "") {
            model.addAttribute("name", name);
            model.addAttribute("shops",shopService.findShopByName(name));
            return "admin/shop/list";
        }
        else {
            model.addAttribute("shops",shopService.findAll());
            return "redirect:/admin/shop";
        }
    }
}
