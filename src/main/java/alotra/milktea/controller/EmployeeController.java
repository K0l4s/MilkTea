package alotra.milktea.controller;
import alotra.milktea.entity.Employee;
import alotra.milktea.service.EmployeeServiceImpl;
import alotra.milktea.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService = new EmployeeServiceImpl();

    @GetMapping("/employee")
    public String findAll(Model model){
        model.addAttribute("employees",employeeService.findAll());
        return "/employee/listEmployee";
    }

    @GetMapping("/employee/id={employeeID}")
    public String findOne(@PathVariable("employeeID") int employeeID, Model model){
        model.addAttribute("employees",employeeService.findOne(employeeID));
        return "/employee/listEmployee";
    }

    @GetMapping("/employee/add")
    public String addEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "/employee/addEmployee";
    }

}
