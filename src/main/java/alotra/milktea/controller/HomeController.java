package alotra.milktea.controller;

import alotra.milktea.entity.User;
import alotra.milktea.model.ResetPasswordModel;
import alotra.milktea.model.SendCodeModel;
import alotra.milktea.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
	@Autowired
	IUserService userService = new UserServiceImpl();
	@Autowired
	private Email email;
	@Autowired
	IProductService productService = new ProductServiceImpl();
	@GetMapping("/home")
	protected String home() {
//		model.addAttribute("products",productService.findAllByStatusNot((short) 0));
		return "index";
	}
	@GetMapping("/admin")
	protected  String admin() {
		return "admin/others/dashboard";
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
	@GetMapping("/forgotPassword")
	protected  String forgotPassword() {
		return "home/forgot";
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
			if (user.isEnable()) {
				return "redirect:/home";
			} else {
				// User newUser = user;
				// newUser.setCode(null);
				//user.setCode(null);
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
	@GetMapping("/forgotPassword/sendRequest")
	protected String getRequestPassForm(Model model){
		SendCodeModel sendCodeModel = new SendCodeModel();
		model.addAttribute("sendReqCode", sendCodeModel);
		return "home/sendReqPass";
	}
	@GetMapping("/forgotPassword/vetify")
	protected  String getResetPassword(@Param("email") String email, Model model){
		ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
		resetPasswordModel.setEmail(email);
		model.addAttribute("resetPssw",resetPasswordModel);
		return "home/forgotPassword";
	}
	@PostMapping("/forgotPassword/vetify")
	protected String postResetPassword(@ModelAttribute("resetPssw") ResetPasswordModel resetPasswordModel){
		if(userService.resetPassw(resetPasswordModel)){
			return "redirect:/login";
		}
		if(resetPasswordModel.getEmail()==null)
			return "redirect:/forgotPassword/vetify";
		return "redirect:/forgotPassword/vetify?email"+resetPasswordModel+"/error";
	}
	@PostMapping("/forgotPassword/sendRequest")
	protected  String postRequestPassForm(@ModelAttribute("sendReqCode") SendCodeModel sendCode){
		User user = userService.findByEmail(sendCode.getEmail());
//		userService.
//		email.sendResetPassCode(user);

		if(userService.sendRequestPassCode(sendCode)){
			email.sendResetPassCode(user);
			return "redirect:/forgotPassword/vetify";}
		return "redirect:/forgotPassword/sendRequest";
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
	@GetMapping("/logout")
	private String getLogout(HttpSession session, HttpServletResponse response){
		// Xóa thông tin đăng nhập từ Session
		session.removeAttribute("username");

		// Xóa Cookie
		Cookie usernameCookie = new Cookie("username", null);
		usernameCookie.setMaxAge(0);
		response.addCookie(usernameCookie);

		// Chuyển hướng về trang đăng nhập
		return "redirect:/login";
	}
}
