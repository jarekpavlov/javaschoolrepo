package com.jschool.controllers;

import com.jschool.service.EntityService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @Autowired
    private EntityService entityService;
   private static final Logger logger = Logger.getLogger(HelloController.class);

    @GetMapping(value = "/test")
    public String hello() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
        return "users";
    }

    @GetMapping(value = "")
    public String startPage() {
        return "startPage";
    }


}
