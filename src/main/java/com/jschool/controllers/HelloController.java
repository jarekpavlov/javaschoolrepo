package com.jschool.controllers;

import com.jschool.count.JoinCountByClient;
import com.jschool.count.JoinCountByProduct;
import com.jschool.count.JoinCountSum;
import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class HelloController {

    @Autowired
    private EntityService entityService;

    @GetMapping(value = "/test")
    public String hello() {
        Set<JoinCountByClient> set = entityService.getBestClient(20);
        System.out.println(set);
        JoinCountSum joinCountSum = entityService.getSum(20);
        System.out.println(joinCountSum);
        Set<JoinCountByProduct> setP = entityService.getBestProduct(20);
        System.out.println(setP);
        return "users";
    }

    @GetMapping(value = "")
    public String startPage() {
        return "startPage";
    }


}
