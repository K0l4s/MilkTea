package alotra.milktea.controller;

import alotra.milktea.entity.Category;
import alotra.milktea.service.CategoryServiceImpl;
import alotra.milktea.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    ICategoryService categoryService = new CategoryServiceImpl();
    @GetMapping("/admin/category")
    public String findAll(Model model){
        model.addAttribute("listcate",categoryService.findAll());
        return "/category/list";
    }
    @GetMapping("/admin/category/edit/{categoryID}")
    public String findOne(@PathVariable("categoryID") int categoryID, Model model){
        Optional<Category> cate = categoryService.findOne(categoryID);
        if(cate.isPresent()){
            model.addAttribute("category",cate.get());
            return "/category/edit";
        }
        return "error";
    }
    @GetMapping("/admin/category/add")
    public String addCategory(Model model){
        Category category = new Category();
        model.addAttribute("category",category);
        return "/category/add";
    }
    @PostMapping("/category/save")
    public String saveCategory(@ModelAttribute("category") Category category){
        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }
    @GetMapping("/admin/category/delete/{categoryID}")
    public String deleteCategory(@PathVariable("categoryID") int categoryID){
        categoryService.deleteCategory(categoryID);
        return "redirect:/admin/category";
    }
    @GetMapping("/admin/category/search")
    public String searchCateByCateName(@RequestParam("name") String name, Model model){
        if (name != "") {
            model.addAttribute("name", name);
            model.addAttribute("listcate",categoryService.findProductByName(name));
            return "/category/list";
        }
        else {
            model.addAttribute("listcate",categoryService.findAll());
            return "redirect:/admin/category";
        }
    }
}
