package alotra.milktea.controller;
import alotra.milktea.entity.Employee;
import alotra.milktea.service.EmployeeServiceImpl;
import alotra.milktea.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@Controller
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService = new EmployeeServiceImpl();

    @GetMapping("/admin/employee")
    public String findAll(Model model){
        model.addAttribute("employees",employeeService.findAll());
        return "admin/employee/listEmployee";
    }

    @GetMapping("/admin/employee/edit/{employeeID}")
    public String findOne(@PathVariable("employeeID") int employeeID, Model model){
        Optional<Employee> employee = employeeService.findOne(employeeID);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "admin/employee/editEmployee";
        } else {
            return "error";
        }
    }

    @GetMapping("admin/employee/add")
    public String addEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "admin/employee/addEmployee";
    }

    @GetMapping("/admin/employee/delete/{employeeID}")
    public String deleteEmployee(@PathVariable(value = "employeeID") int employeeID)
    {
        employeeService.deleteEmployee(employeeID);
        return "redirect:/admin/employee";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee)
    {
        employeeService.saveEmployee(employee);
        return "redirect:/admin/employee";
    }
    @GetMapping("/admin/employee/search")
    public String searchEmployeeByName(@RequestParam("name") String name, Model model){
        if (name != "") {
            model.addAttribute("name", name);
            model.addAttribute("employees",employeeService.findEmployeeByName(name));
            return "admin/employee/listEmployee";
        }
        else {
            model.addAttribute("employees",employeeService.findAll());
            return "redirect:/admin/employee";
        }
    }
}
