package alotra.milktea.controller;

import alotra.milktea.entity.Role;
import alotra.milktea.entity.User;
import alotra.milktea.service.IRoleService;
import alotra.milktea.service.IUserService;
import alotra.milktea.service.RoleServiceImpl;
import alotra.milktea.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    IUserService userService = new UserServiceImpl();
    @Autowired
    IRoleService roleService = new RoleServiceImpl();

    @GetMapping("/view")
    protected String getView(@RequestParam(name = "page", required = false) Integer page, Model model) {
        int pageSize = 100; // Or any other default value

        if (page == null || page <= 1) {
            page = 1;
        } page -=1;
        Long total = userService.countAll();
        List<User> listUser = userService.findAll(page, pageSize);
        Long pageCount = total / pageSize;
        if(page <=0 )
            model.addAttribute("currentPage", 1);
        else
            model.addAttribute("currentPage", page+1);


        model.addAttribute("pageCount", pageCount);

        model.addAttribute("list", listUser);
        return "user/viewUser";
    }

    @GetMapping("/add")
    protected String getAdd(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("roles",roleService.findAll());
        return "user/addUser";
    }
    @PostMapping("/delete")
    protected  String postDelete(@RequestParam String username){
        if(userService.delete(username))
            return "redirect:/admin/user/view";
        return "redirect:/admin/user/view?error";
    }
    @PostMapping("/add")
    protected String postAdd(@ModelAttribute User user){
        if(userService.register(user))
            return "redirect:/admin/user/view";
        return "redirect:/admin/user/add";
    }

    @PostMapping("/enable")
    protected String postEnable(@RequestParam("username") String username){
        if(userService.enableAccount(username))
            return "redirect:/admin/user/view";
        return "redirect:/admin/user/view?error";
    }

    @GetMapping("/edit")
    protected String getEdit(@RequestParam("username")String username, Model model){
        model.addAttribute("user",userService.findOne(username));
        model.addAttribute("roles",roleService.findAll());
        return "user/editUser";
    }
    @PostMapping("/edit")
    protected String postEdit(@ModelAttribute User user){
        if(userService.updateUser(user))
            return "redirect:/admin/user/view";
        return "redirect:/admin/user/add";
    }

    @PostMapping("resetPssw")
    protected  String postResetPass(@RequestParam("username") String username){
        User user = userService.findOne(username);
        user.setPassword("Default~|Qksdjf");
        if(userService.updateUser(user))
            return "redirect:/admin/user/view";
        return "redirect:/admin/user/add";
    }
}
