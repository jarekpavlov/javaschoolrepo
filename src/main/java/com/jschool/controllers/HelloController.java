package com.jschool.controllers;

import com.jschool.service.ProductService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class HelloController {


    @Autowired
    AmqpTemplate template;

    private ProductService productService;

    public HelloController(ProductService productService) {
        this.productService = productService;
    }

    @Value("${upload.path}")
    String some;

    @GetMapping(value = "/test")
    @ResponseBody
    public String hello() {
        System.out.println(some);
        return "users";
    }

    @GetMapping(value = "")
    public String startPage(ModelMap map, HttpSession httpSession) {
        productService.getCartModelMap(map, httpSession);
        return "startPage";
    }


}
