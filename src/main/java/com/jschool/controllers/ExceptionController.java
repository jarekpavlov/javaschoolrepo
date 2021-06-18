package com.jschool.controllers;

import com.jschool.exceptions.*;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    Logger logger = Logger.getLogger(this.getClass());

    @ExceptionHandler(value = ChangePasswordException.class)
    public String getPasswordException() {
        return "exceptions/changePasswordExceptionPage";
    }

    @ExceptionHandler(value = UserExists.class)
    public String getUserExistsException() {
        return "exceptions/customPageException";
    }

    @ExceptionHandler(value = EmptyFieldException.class)
    public String handleEmptyFieldException() {
        return "exceptions/emptyFieldExceptionPage";
    }

    @ExceptionHandler(value = ProductIsInOrderException.class)
    public String getProductIsInOrderException() {
        return "exceptions/productIsInOrderPage";
    }

//    @ExceptionHandler(value = Exception.class)
//    public String getException() {
//        return "exceptions/genericExceptionPage";
//    }

    @ExceptionHandler(value = NonValidNumberException.class)
    public String getNonValidNumberException() {
        return "exceptions/notValidNumberPage";
    }
}
