package com.jschool.controllers;

import com.jschool.domain.Product;
import com.jschool.service.ProductService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Product hello(@RequestParam String buttonPage) {
        System.out.println(buttonPage);
        Product product = new Product();
        product.setCategory(buttonPage);
        product.setTitle("sneakers");
        product.setBrand("brand");
        product.setColor("white");
        return product;
    }

    @GetMapping(value = "")
    public String startPage(ModelMap map, HttpSession httpSession) {
        productService.getCartModelMap(map, httpSession);
        return "startPage";
    }

    @PostMapping(value = "/test")
    public String someAct(@RequestParam String color, @RequestParam String brand,@RequestParam String title){
        System.out.println(color);
        return "redirect:/products";
    }


}
