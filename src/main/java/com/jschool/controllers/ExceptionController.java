package com.jschool.controllers;

import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.ProductIsInOrder;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    Logger logger = Logger.getLogger(this.getClass());
//    @ExceptionHandler(value = Exception.class)
//    public String getGenericException() {
//        return "genericExceptionPage";
//    }

    @ExceptionHandler(value = EmptyFieldException.class)
    public String handleEmptyFieldException() {
        return "emptyFieldExceptionPage";
    }

    @ExceptionHandler(value = ProductIsInOrder.class)
    public String getProductIsInOrderException() {
        return "productIsInOrderPage";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequestException(Exception e) {
        logger.warn("User used incorrect type during typing in input");
        return "badRequestExceptionPage";
    }


}
