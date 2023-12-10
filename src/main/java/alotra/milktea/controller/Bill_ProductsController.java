package alotra.milktea.controller;

import alotra.milktea.entity.Bill_Products;
import alotra.milktea.service.Bill_ProductsServiceImpl;
import alotra.milktea.service.IBill_ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class Bill_ProductsController {
    @Autowired
    IBill_ProductsService billProductsService = new Bill_ProductsServiceImpl();
    @GetMapping("/admin/bill_products")
    public String findAll(Model model){
        model.addAttribute("list",billProductsService.findAll());
        return "bill_products/list";
    }
    @GetMapping("/admin/bill_products/add")
    public String addBill_Products(Model model){
        Bill_Products bp = new Bill_Products();
        model.addAttribute("bill_products", bp);
        return "bill_products/add";
    }
    @PostMapping("/bill_products/save")
    public String saveBill_Products(@ModelAttribute("bill_products") Bill_Products bp){
        billProductsService.saveBill_Products(bp);
        return "redirect:/admin/bill_products";
    }
    @GetMapping("admin/bill_products/edit/{id}")
    public String editBill_Products(@PathVariable("id") int id, Model model){
        Optional<Bill_Products> bp = billProductsService.findOne(id);
        if(bp.isPresent()){
            model.addAttribute("bill_products", bp);
            return "bill_products/edit";
        }
        return "error";
    }
    @GetMapping("admin/bill_products/delete/{id}")
    public String deleteBill_Products(@PathVariable("id") int id){
        billProductsService.deleteBill_Products(id);
        return "redirect:/admin/bill_products";
    }
}
