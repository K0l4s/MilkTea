package alotra.milktea.controller;

import alotra.milktea.entity.Ingredients;
import alotra.milktea.service.IIngredientService;
import alotra.milktea.service.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class IngredientController {
    @Autowired
    IIngredientService ingredientService = new IngredientServiceImpl();
    @GetMapping("/admin/ingredient")
    public String findAll(Model model){
        model.addAttribute("ingredient",ingredientService.findAll());
        return "admin/ingredient/list";
    }
    @GetMapping("/admin/ingredient/add")
    public String addIngredient(Model model){
        Ingredients ingredient = new Ingredients();
        model.addAttribute("ingredients", ingredient);
        return "admin/ingredient/add";
    }
    @PostMapping("/ingredient/save")
    public String saveIngredient(@ModelAttribute("ingredients") Ingredients ingredients){
        ingredientService.saveIngredients(ingredients);
        return "redirect:/admin/ingredient";
    }
    @GetMapping("/admin/ingredient/edit/{id}")
    public String editIngredient(@PathVariable("id") int id,Model model){
        Optional<Ingredients> ingredients = ingredientService.findOne(id);
        if(ingredients.isPresent()){
            model.addAttribute("ingredients",ingredients);
            return "admin/ingredient/edit";
        }
        return "error";
    }
    @GetMapping("/admin/ingredient/delete/{id}")
    public String deleteIngredient(@PathVariable("id") int id){
        ingredientService.deleteIngredients(id);
        return "redirect:/admin/ingredient";
    }
}
