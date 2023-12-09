package alotra.milktea.controller;

import alotra.milktea.entity.Customer;
import alotra.milktea.service.CustomerServiceImpl;
import alotra.milktea.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    ICustomerService customerService = new CustomerServiceImpl();
    @GetMapping("/admin/customer")
    public String findAll(Model model){
        model.addAttribute("customers",customerService.findAll());
        return "/customer/list";
    }
    @GetMapping("/admin/customer/edit/{customerID}")
    public String editCustomer(@PathVariable("customerID") int id, Model model){
        Optional<Customer> customer = customerService.findOne(id);
        if(customer.isPresent()){
            model.addAttribute("customer",customer.get());
            return "/customer/edit";
        }
        return "error";
    }
    @GetMapping("/admin/customer/add")
    public String addCustomer(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "/customer/add";
    }
    @PostMapping("/customer/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer){
        customerService.saveCustomer(customer);
        return"redirect:/admin/customer";
    }
    @GetMapping("/admin/customer/delete/{customerID}")
    public String deleteCustomer(@PathVariable("customerID") int id)
    {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customer";
    }
}
