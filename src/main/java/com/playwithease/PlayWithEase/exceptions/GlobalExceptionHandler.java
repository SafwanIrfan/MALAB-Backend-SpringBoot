package com.playwithease.PlayWithEase.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //409 Conflict
    public String handleDataIntegrityViolation (DataIntegrityViolationException ex){
        System.out.println("MESSAGE : " + ex.getMessage());
        if(ex.getMessage().contains("unique_username")) {
            return "Username already exists";
        } else if (ex.getMessage().contains("unique_phone")){
            return "Phone Number already exists";
        } else if (ex.getMessage().contains("unique_email")) {
            return "Email already exists";
        } else {
            return "Duplicate values alert!";
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConstraintViolation(ConstraintViolationException ex){
        return "Invalid Phone Number";
    }
}
