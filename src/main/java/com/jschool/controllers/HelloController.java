package com.jschool.controllers;

import com.jschool.domain.Client;
import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private EntityService entityService;

    @Autowired
    public HelloController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "/test")
    public String hello() {
        Client client = entityService.getEntityByEmail(Client.class, "yaroslavvl@yandex.ru");
        System.out.println(client);
        return "users";
    }

    @GetMapping(value = "")
    public String startPage() {
        return "startPage";
    }


}
