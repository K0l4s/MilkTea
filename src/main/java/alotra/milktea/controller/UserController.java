package alotra.milktea.controller;

import alotra.milktea.entity.User;
import alotra.milktea.service.IUserService;
import alotra.milktea.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    IUserService userService = new UserServiceImpl();

    @GetMapping("/view")
    protected String getView(Model model){
        List<User> listUser = userService.findAll();
        model.addAttribute("list",listUser);
        return "user/viewUser";
    }
}
