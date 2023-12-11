package alotra.milktea.controller;

import alotra.milktea.entity.Shop_Ingredients;
import alotra.milktea.service.IShop_IngredientsService;
import alotra.milktea.service.Shop_IngredientsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class Shop_IngredientsController {
    @Autowired
    IShop_IngredientsService shopIngredientsService = new Shop_IngredientsServiceImpl();
    @GetMapping("/admin/shop_ingredients")
    public String findAll(Model model){
        model.addAttribute("list",shopIngredientsService.findAll());
        return "shop_ingredients/list";
    }
    @GetMapping("/admin/shop_ingredients/add")
    public String addShop_Ingredients(Model model){
        Shop_Ingredients si = new Shop_Ingredients();
        model.addAttribute("list", si);
        return "/shop_ingredients/add";
    }
    @PostMapping("/shop_ingredients/save")
    public String saveShop_Ingredients(@ModelAttribute("list") Shop_Ingredients si){
        shopIngredientsService.saveShop_Ingredients(si);
        return "redirect:/admin/shop_ingredients";
    }
    @GetMapping("/admin/shop_ingredients/edit/{id}")
    public String editShop_Ingredients(@PathVariable("id") int id, Model model){
        Optional<Shop_Ingredients> si = shopIngredientsService.findOne(id);
        if(si.isPresent()){
            model.addAttribute("list",si);
            return "/shop_ingredients/edit";
        }
        return "error";
    }
    @GetMapping("/admin/shop_ingredients/delete/{id}")
    public String deleteShop_Ingredients(@PathVariable("id") int id){
        shopIngredientsService.deleteShop_Ingredietns(id);
        return "redirect:/admin/shop_ingredients";
    }
}
