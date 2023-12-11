package alotra.milktea.controller;

import alotra.milktea.entity.Role;
import alotra.milktea.service.IRoleService;
import alotra.milktea.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RoleController {
    @Autowired
    IRoleService roleService = new RoleServiceImpl();

    @GetMapping("/admin/role")
    public String findAll(Model model){
        model.addAttribute("roles",roleService.findAll());
        return "admin/role/list";
    }
    @GetMapping("/admin/role/edit/{roleID}")
    public String findOne(@PathVariable("roleID") int roleID, Model model){
        Optional<Role> role = roleService.findOne(roleID);
        if(role.isPresent()){
            model.addAttribute("role",role.get());
            return "admin/role/edit";
        }
        return "error";
    }
    @GetMapping("/admin/role/add")
    public String addRole(Model model){
        Role role = new Role();
        model.addAttribute("role",role);
        return "admin/role/add";
    }
    @PostMapping("/role/save")
    public String saveRole(@ModelAttribute("role") Role role){
        roleService.saveRole(role);
        return "redirect:/admin/role";
    }
    @GetMapping("/admin/role/delete/{roleID}")
    public String deleteRole(@PathVariable("roleID") int roleID){
        roleService.deleteRole(roleID);
        return "redirect:/admin/role";
    }
    @GetMapping("/admin/role/search")
    public String searchProByCartName(@RequestParam("name") String name, Model model){
        if (name != "") {
            model.addAttribute("name", name);
            model.addAttribute("roles",roleService.findRoleByName(name));
            return "admin/role/list";
        }
        else {
            model.addAttribute("roles",roleService.findAll());
            return "redirect:/admin/role";
        }
    }
}
