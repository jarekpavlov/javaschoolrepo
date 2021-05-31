package com.jschool.controllers;

import com.jschool.exceptions.ChangePasswordException;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.exceptions.ProductIsInOrderException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handle(Exception ex,
//                                         HttpServletRequest request, HttpServletResponse response) {
//        if (ex instanceof NullPointerException) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }

    @ExceptionHandler(value = NonValidNumberException.class)
    public String getNonValidNumberException() {
        return "exceptions/notValidNumberPage";
    }
}
