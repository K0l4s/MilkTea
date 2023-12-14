package alotra.milktea.controller;
import alotra.milktea.entity.Category;
import alotra.milktea.entity.Employee;
import alotra.milktea.entity.Product;
import alotra.milktea.entity.Shop;
import alotra.milktea.model.EmployeeModel;
import alotra.milktea.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@Controller
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService = new EmployeeServiceImpl();
    @Autowired
    IRoleService roleService = new RoleServiceImpl();
    @Autowired
    IShopService shopService = new ShopServiceImpl();
    @Autowired
    IUserService userService = new UserServiceImpl();
    @Autowired
    IStorageService storageService;
    @GetMapping("/admin/employee")
    public String findAll(Model model){
        model.addAttribute("employees",employeeService.findAllByStatusNot((short) 0));
        return "admin/employee/listEmployee";
    }

    @GetMapping("/admin/employee/edit/{employeeID}")
    public String findOne(@PathVariable("employeeID") int employeeID, Model model){
        Optional<Employee> employee = employeeService.findOne(employeeID);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());

            model.addAttribute("roles", roleService.findAllByStatusNot((short) 0));
            model.addAttribute("shops", shopService.findAllBySatusNot((short) 0));
            model.addAttribute("users", userService.findAll());
            return "admin/employee/editEmployee";
        } else {
            return "error";
        }
    }

    @GetMapping("admin/employee/add")
    public String addEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("roles", roleService.findAllByStatusNot((short) 0));
        model.addAttribute("shops", shopService.findAllBySatusNot((short) 0));
        model.addAttribute("users", userService.findAll());
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
    public String saveEmployee(@ModelAttribute("employee") EmployeeModel empModel)
    {
        Employee entity = new Employee();
        //Copy model sang entity
        BeanUtils.copyProperties(empModel, entity);

        Shop shopEntity = new Shop();
        shopEntity.setShopID(empModel.getShop().getShopID());
        entity.setShop(shopEntity);

        //Kiểm tra file
        if (!empModel.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            //Lưu file vào ImageURL
            entity.setPhoto(storageService.getStorageFilename(empModel.getImageFile(), uuString));
            storageService.store(empModel.getImageFile(), entity.getPhoto());
        }

        employeeService.saveEmployee(entity);
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
    @GetMapping("/employee/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=\"" + file.getFilename() + "\"").body(file);
    }
}
