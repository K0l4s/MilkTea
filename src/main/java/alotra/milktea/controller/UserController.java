package alotra.milktea.controller;

import alotra.milktea.entity.User;
import alotra.milktea.service.IUserService;
import alotra.milktea.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    IUserService userService = new UserServiceImpl();

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
        return "user/addUser";
    }
}
