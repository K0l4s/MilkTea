package alotra.milktea.controller;

import alotra.milktea.entity.Product_Ingredients;
import alotra.milktea.service.IProduct_IngredientsService;
import alotra.milktea.service.Product_IngredientsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class Product_IngredientsController {
    @Autowired
    IProduct_IngredientsService productIngredientsService = new Product_IngredientsServiceImpl();

    @GetMapping("/admin/product_ingredients")
    public String findAll(Model model) {
        model.addAttribute("list", productIngredientsService.findAll());
        return "product_ingredients/list";
    }

    @GetMapping("/admin/product_ingredients/add")
    public String addProduct_Ingredients(Model model) {
        Product_Ingredients pi = new Product_Ingredients();
        model.addAttribute("list", pi);
        return "product_ingredients/add";
    }

    @PostMapping("/product_ingredients/save")
    public String saveProduct_Ingredients(@ModelAttribute("list") Product_Ingredients pi) {
        productIngredientsService.savePro_Ingredients(pi);
        return "redirect:/admin/product_ingredients";
    }

    @GetMapping("/admin/product_ingredients/edit/{id}")
    public String editProduct_Ingredients(@PathVariable("id") int id, Model model) {
        Optional<Product_Ingredients> pi = productIngredientsService.findOne(id);
        if (pi.isPresent()) {
            model.addAttribute("list", pi);
            return "product_ingredients/edit";
        }
        return "error";
    }
    @GetMapping("/admin/product_ingredients/delete/{id}")
    public String deleteProduct_Ingredients(@PathVariable("id") int id) {
        productIngredientsService.deletePro_Ingredients(id);
        return "redirect:/admin/product_ingredients";
    }
}
