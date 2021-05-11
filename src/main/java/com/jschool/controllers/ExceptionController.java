package com.jschool.controllers;

import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.ProductIsInOrder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(value = Exception.class)
//    public String getGenericException() {
//        return "genericExceptionPage";
//    }

    @ExceptionHandler(value = EmptyFieldException.class)
    public String handleEmptyFieldException(){
        return "emptyFieldExceptionPage";
    }

    @ExceptionHandler(value = ProductIsInOrder.class)
    public String getProductIsInOrderException(){
        return "productIsInOrderPage";
    }

}
