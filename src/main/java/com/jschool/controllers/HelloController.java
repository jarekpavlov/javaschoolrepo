package com.jschool.controllers;

import com.jschool.service.EntityService;
import com.jschool.service.MailSender;
import com.jschool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HelloController {

    @Autowired
    EntityService entityService;
    @Autowired
    MailSender mailSender;


    private ProductService productService;

    public HelloController(ProductService productService) {
        this.productService = productService;
    }

    @Value("${upload.path}")
    String some;

    @GetMapping(value = "/test")
    public String hello() {

        mailSender.send("jarekpavlov@gmail.com", "Activation code", "Some message");
        System.out.println("some");
        return "startPage";
    }

    @GetMapping(value = "")
    public String startPage(ModelMap map, HttpSession httpSession) {
        map.addAttribute("productsInCart", productService.getProductInCartQuantity(httpSession));
        return "startPage";
    }

}
