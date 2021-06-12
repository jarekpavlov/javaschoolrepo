package com.jschool.exceptions;

public class UserExists extends Exception{

    public UserExists(String message){
        super(message);
    }
}
