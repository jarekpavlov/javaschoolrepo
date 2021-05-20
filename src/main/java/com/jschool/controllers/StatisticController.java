package com.jschool.controllers;

import com.jschool.count.JoinCountByProduct;
import com.jschool.domain.Product;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.service.EntityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class StatisticController {

    private EntityService entityService;

    public StatisticController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "get-statistic")
    @ResponseBody
    public Set<JoinCountByProduct> getProductStatistic(@RequestParam int days) throws NonValidNumberException {
        if (days < 1)
            throw new NonValidNumberException("You've entered the wrong format number");
        Set<JoinCountByProduct> bestProductSet = entityService.getBestProduct(days);
        return bestProductSet;
    }


}
