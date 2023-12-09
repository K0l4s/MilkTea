package alotra.milktea.controller;

import alotra.milktea.entity.Category;
import alotra.milktea.entity.Product;
import alotra.milktea.service.IProductService;
import alotra.milktea.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @GetMapping("/admin/product")
    public String findAll(Model model){
        model.addAttribute("products",productService.findAll());
        return "product/list";
    }
    @GetMapping("/admin/product/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Product> product = productService.findOne(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "product/edit";
        }
        return"error";
    }
    @GetMapping("/admin/product/add")
    public String addProduct(Model model){
        Product pro = new Product();
        model.addAttribute("product",pro);
        return "/product/add";
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
    public String searchProByCateName(@RequestParam("categoryName") String name,Model model){
//        productService.findProductByCategoryName(name);
        if (name != "") {
            model.addAttribute("categoryName", name);
            model.addAttribute("products",productService.findProductByCategoryName(name));
            return "/product/list";
        }
        else {
            model.addAttribute("products",productService.findAll());
            return "redirect:/admin/product";
        }
    }
}
