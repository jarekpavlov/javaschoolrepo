package com.jschool.controllers;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.service.EntityService;
import com.jschool.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private OrderService orderService;

    private EntityService entityService;

    @Autowired
    public HelloController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "/test")
    public String hello() {
        List<OrderDTO> list = orderService.getOrderDtoList();
        System.out.println(list);
        return "users";
    }

    @GetMapping(value = "")
    public String startPage() {
        return "startPage";
    }


}
