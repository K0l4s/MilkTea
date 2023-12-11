package alotra.milktea.controller;

import alotra.milktea.entity.Category;
import alotra.milktea.entity.Product;
import alotra.milktea.service.CategoryServiceImpl;
import alotra.milktea.service.ICategoryService;
import alotra.milktea.service.IProductService;
import alotra.milktea.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @Autowired
    ICategoryService categoryService = new CategoryServiceImpl();
    @GetMapping("/admin/product")
    public String findAll(Model model){
        model.addAttribute("products",productService.findAllByStatusNot((short) 0));
        return "admin/product/list";
    }
    @GetMapping("/admin/product/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Product> product = productService.findOne(id);
        if(product.isPresent()){
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("product", product.get());
            return "admin/product/edit";
        }
        return"error";
    }
    @GetMapping("/admin/product/add")
    public String addProduct(Model model){
        Product pro = new Product();
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product",pro);
        return "/admin/product/add";
    }
    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.saveProduct(product);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.DeleteProduct(id);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/search")
    public String searchProByName(@RequestParam("name") String name,Model model){
        if (name != "") {
            model.addAttribute("name", name);
            model.addAttribute("products",productService.findProductByName(name));
            return "/product/list";
        }
        else {
            model.addAttribute("products",productService.findAll());
            return "redirect:/admin/product";
        }
    }
}
