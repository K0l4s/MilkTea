package alotra.milktea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ProductController {
    @GetMapping("/admin/products")
    protected String product() {
        return "admin/products/list";
    }
}
