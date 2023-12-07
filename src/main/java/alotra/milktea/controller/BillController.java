package alotra.milktea.controller;

import alotra.milktea.service.BillServiceImpl;
import alotra.milktea.service.IBillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BillController {
    @Autowired
    IBillService billService = new BillServiceImpl();

    @GetMapping("/bill")
    public String findAll(Model model){
        model.addAttribute("bills",billService.findAll());
        return "/bill/listBill";
    }
}
