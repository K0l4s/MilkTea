package alotra.milktea.controller;

import alotra.milktea.entity.Bill;
import alotra.milktea.entity.Category;
import alotra.milktea.service.BillServiceImpl;
import alotra.milktea.service.IBillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class BillController {
    @Autowired
    IBillService billService = new BillServiceImpl();

    @GetMapping("/admin/bill")
    public String findAll(Model model){
        model.addAttribute("bills",billService.findAll());
        return "/bill/list";
    }
    @GetMapping("/admin/bill/add")
    public String addBill(Model model){
        Bill bill = new Bill();
        model.addAttribute("bill",bill);
        return "/bill/add";
    }
    @PostMapping("/bill/save")
    public String saveBill(@ModelAttribute("bill") Bill bill) {
        bill.setCreateDay(LocalDateTime.now());
        billService.saveBill(bill);
        return "redirect:/admin/bill";
    }

    @GetMapping("/admin/bill/edit/{id}")
    public String editBill(@PathVariable("id") int id, Model model){
        Optional<Bill> bill = billService.findOne(id);
        if(bill.isPresent()){
            model.addAttribute("bill", bill);
            return "bill/edit";
        }
        return "error";
    }
    @GetMapping("/admin/bill/delete/{id}")
    public String deleteBill(@PathVariable("id") int id){
        billService.deleteBill(id);
        return "redirect:/admin/bill";
    }

//   User
    @GetMapping("/user/bill")
    public String findBilsByUserID(@RequestParam("id") int id, Model model){
        model.addAttribute("bills", billService.findBillsByCustomerID(id));
        return "bill/listForUser";
    }

}
