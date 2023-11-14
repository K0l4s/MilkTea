package alotra.milktea.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import alotra.milktea.entity.User;
import alotra.milktea.service.Email;
import alotra.milktea.service.IUserService;
import alotra.milktea.service.UserServiceImpl;
import jakarta.transaction.Transactional;

@Controller
public class HomeController {
	@Autowired
	IUserService userService = new UserServiceImpl();
	
	@Autowired
	private Email email;

	@GetMapping("/home")
	protected String home() {
		return "home/home";
	}

	@GetMapping("/login")
	protected String showLoginForm() {
		return "home/login";
	}

	@GetMapping("/register")
	protected String showRegisterForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "home/register";
	}
	@GetMapping("vetifyRegister")
	protected String showVetifyRegister() {
		return "home/vetifyRegister";
	}
	@PostMapping("register")
	protected String reg(@ModelAttribute User user) {
	    if (userService.register(user)) {
	        // Đăng ký thành công, chuyển hướng đến trang "/home"
	        return "redirect:/vetifyRegister";
	    } else {
	        // Đăng ký không thành công, chuyển hướng đến trang đăng ký với tham số error
	        return "redirect:/register?error";
	    }
	}


}
