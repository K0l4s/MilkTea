package alotra.milktea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alotra.milktea.service.Email;

@Controller
public class EmailController {
	@Autowired
	private Email email;
	
	@GetMapping("/hello")
    public String hello() {
        return "index";
    }
}
