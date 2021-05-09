package com.jschool.controllers;

import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EmployeeController {

    EntityService entityService;

    @Autowired
    public EmployeeController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "admin/statistic")
    public String getStatistic(ModelMap map, HttpServletRequest request) {
        int days = Integer.valueOf(request.getParameter("days"));
        map.addAttribute("bestClientTree", entityService.getBestClient(days));
        map.addAttribute("bestProductTree", entityService.getBestProduct(days));
        map.addAttribute("total", entityService.getSum(days));
        map.addAttribute("daysAgo",days);
        return "statistic";
    }

}
