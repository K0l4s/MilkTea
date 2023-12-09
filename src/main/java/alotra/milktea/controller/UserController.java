package alotra.milktea.controller;

import alotra.milktea.entity.User;
import alotra.milktea.service.IUserService;
import alotra.milktea.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private IUserService userService = new UserServiceImpl();
    @GetMapping("/admin/user")
    public String findAll(Model model){
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @GetMapping("/admin/user/{username}")
    public String findOne(@PathVariable("username") String username, Model model){
        model.addAttribute("users", userService.findOne(username));
        return "/user/list";
    }
}
