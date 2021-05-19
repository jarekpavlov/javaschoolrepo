package com.jschool.controllers;

import com.jschool.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HelloController {

    private ProductService productService;

    public HelloController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/test")
    public String hello() {

        return "users";
    }

    @GetMapping(value = "")
    public String startPage(ModelMap map, HttpSession httpSession) {
        productService.getCartModelMap(map, httpSession);
        return "startPage";
    }


}
