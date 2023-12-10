package alotra.milktea.controller;

import alotra.milktea.entity.Shop_Ingredients;
import alotra.milktea.entity.Shop_Products;
import alotra.milktea.service.IShop_ProductsService;
import alotra.milktea.service.Shop_ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class Shop_ProductsController {
    @Autowired
    IShop_ProductsService shopProductsService = new Shop_ProductsServiceImpl();
    @GetMapping("/admin/shop_products")
    public String findAll(Model model){
        model.addAttribute("list",shopProductsService.findAll());
        return "shop_products/list";
    }
    @GetMapping("/admin/shop_products/add")
    public String addShop_Products(Model model){
        Shop_Products sp = new Shop_Products();
        model.addAttribute("list", sp);
        return "/shop_products/add";
    }
    @PostMapping("/shop_products/save")
    public String saveShop_Products(@ModelAttribute("list") Shop_Products sp){
        shopProductsService.saveShop_Pro(sp);
        return "redirect:/admin/shop_products";
    }
    @GetMapping("/admin/shop_products/edit/{id}")
    public String editShop_Products(@PathVariable("id") int id, Model model){
        Optional<Shop_Products> sp = shopProductsService.findOne(id);
        if(sp.isPresent()){
            model.addAttribute("list",sp);
            return "/shop_products/edit";
        }
        return "error";
    }
    @GetMapping("/admin/shop_products/delete/{id}")
    public String deleteShop_Products(@PathVariable("id") int id){
        shopProductsService.deleteShop_Pro(id);
        return "redirect:/admin/shop_products";
    }

}
