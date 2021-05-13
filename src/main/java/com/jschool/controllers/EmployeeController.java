package com.jschool.controllers;

import com.jschool.service.EntityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EmployeeController {
    Logger logger = Logger.getLogger(this.getClass());
    EntityService entityService;

    @Autowired
    public EmployeeController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "admin/statistic")
    public String getStatistic(ModelMap map, HttpServletRequest request) {
        logger.info("Employee entered getStatistic method");
        int days = Integer.valueOf(request.getParameter("days"));
        map.addAttribute("bestClientTree", entityService.getBestClient(days));
        map.addAttribute("bestProductTree", entityService.getBestProduct(days));
        map.addAttribute("total", entityService.getSum(days));
        map.addAttribute("daysAgo",days);
        logger.info("Employee left the getStatistic method");
        return "statistic";
    }

}
