package com.jschool.controllers;

import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;

@Controller
public class HelloController {

    @Autowired
    private EntityService entityService;


    @GetMapping(value = "/test")
    public String hello() throws FileNotFoundException {
        int a = 5;
        if(a==5) {
            throw new FileNotFoundException();
        }
        return "users";
    }

    @GetMapping(value = "")
    public String startPage() {
        return "startPage";
    }


}
