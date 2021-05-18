package com.jschool.controllers;

import com.jschool.exceptions.ChangePasswordException;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.exceptions.ProductIsInOrderException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    Logger logger = Logger.getLogger(this.getClass());

    @ExceptionHandler(value = ChangePasswordException.class)
    public String getPasswordException() {
        return "exceptions/changePasswordExceptionPage";
    }

    @ExceptionHandler(value = EmptyFieldException.class)
    public String handleEmptyFieldException() {
        return "exceptions/emptyFieldExceptionPage";
    }

    @ExceptionHandler(value = ProductIsInOrderException.class)
    public String getProductIsInOrderException() {
        return "exceptions/productIsInOrderPage";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequestException(Exception e) {
        logger.warn("User used incorrect type during typing in input");
        return "exceptions/badRequestExceptionPage";
    }

    @ExceptionHandler(value = NonValidNumberException.class)
    public String getNonValidNumberException() {
        return "exceptions/notValidNumberPage";
    }
}
