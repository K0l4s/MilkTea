package alotra.milktea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import alotra.milktea.entity.User;
import alotra.milktea.service.Email;
import alotra.milktea.service.IUserService;
import alotra.milktea.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	IUserService userService = new UserServiceImpl();
	@Autowired
	private Email email;

	@GetMapping("/home")
	protected String home() {
		return "index";
	}
	@GetMapping("/admin")
	protected  String admin() {
		return "admin/home";
	}
	@GetMapping("/login")
	protected String showLoginForm(HttpSession session, @CookieValue(value = "username", defaultValue = "") String username, Model model) {
		// Check Session
        if (session.getAttribute("username") != null) {
            return "redirect:/home";
        }

        // Check Cookie
        if (!username.isEmpty()) {
            session.setAttribute("username", username);
            return "redirect:/home";
        }

        User user = new User();
		model.addAttribute("user", user);
        return "home/login"; // Assuming you have a login template named "login.html"
	}

	@GetMapping("/register")
	protected String showRegisterForm(HttpSession session, @CookieValue(value = "username", defaultValue = "") String username, Model model) {
		// Check Session
		if (session.getAttribute("username") != null) {
			return "redirect:/home";
		}

		// Check Cookie
		if (!username.isEmpty()) {
			session.setAttribute("username", username);
			return "redirect:/home";
		}
		User user = new User();
		model.addAttribute("user", user);
		return "home/register";
	}

	@GetMapping("vetifyRegister")
	protected String showVetifyRegister(@RequestParam("username") String username, Model model) {
		User user = userService.findOne(username);
		if (user != null) {
			if (user.getCode() == null || user.getCode().equals("Vetify")) {
				return "redirect:/home";
			} else {
				// User newUser = user;
				// newUser.setCode(null);
				user.setCode(null);
				model.addAttribute("user", user);
				return "home/vetifyRegister";
			}
		} else {
			return "home/home";
		}

	}

	@GetMapping("/registerInformation")
	protected String insertInfor() {
		return null;
	}

	@PostMapping("/vetifyRegister")
	protected String vetifyRegis(@ModelAttribute("user") User user, HttpSession session,
								 HttpServletResponse response){
		if (userService.vetifyUserCode(user)) {
			String username = user.getUsername();
			session.setAttribute("username", username);

			// Thiết lập Cookie
			Cookie usernameCookie = new Cookie("username", username);
			usernameCookie.setMaxAge(3600);
			response.addCookie(usernameCookie);
			return "redirect:/home";
		}
		return "redirect:/vetifyRegister?username=" + user.getUsername();
	}
	@GetMapping("/forgot")
	protected  String forgotPassword() {
		return "home/forgot";
	}
	
	@PostMapping("/resendReqCode")
	protected String resendReqCode() {
		return null;
	}

	@PostMapping("register")
	protected String reg(@ModelAttribute("user") User user) {
		user.setCode(email.getRandom());
		if (userService.register(user)) {
			email.sendEmailCode(user);
			return "redirect:/vetifyRegister?username=" + user.getUsername();
		} else {
			return "redirect:/register?error";
		}
	}
	@PostMapping("/login")
	protected String postLogin(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            HttpServletResponse response,
            Model model) {
		// Thực hiện kiểm tra thông tin đăng nhập (thay thế bằng logic kiểm tra thực tế)
        if (userService.login(username, password)) {
            // Đăng nhập thành công, thiết lập Session và Cookie
            session.setAttribute("username", username);

            // Thiết lập Cookie
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setMaxAge(3600);
            response.addCookie(usernameCookie);
            
//            session.setAttribute("isAdmin", userService.isAdmin(email, passwd));
            return "redirect:/home";
        } else {
            // Đăng nhập không thành công, quay lại trang đăng nhập
            model.addAttribute("message", "Thông tin đăng nhập không hợp lệ.");
            return "redirect:/login";
        }
	}
}
