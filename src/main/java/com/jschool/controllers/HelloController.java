package com.jschool.controllers;

import com.jschool.service.EntityService;
import com.jschool.service.MailSender;
import com.jschool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    @ResponseBody
    public Map<String, String> hello(@RequestParam(required = false) String productId) {
        Map<String, String> someMap = new HashMap<>();
        someMap.put("1", "5");
        someMap.put("2", "10");
        ModelAndView model = new ModelAndView();
        model.addObject("map", someMap);
        return someMap;
    }

    @GetMapping(value = "")
    public String startPage(ModelMap map, HttpSession httpSession) {
        map.addAttribute("productsInCart", productService.getProductInCartQuantity(httpSession));
        return "startPage";
    }

    @PostMapping(value = "/test")
    @ResponseBody
    public int helloCart(@RequestParam(required = false) int productId) {
        System.out.println(productId);

        return productId;
    }

}
