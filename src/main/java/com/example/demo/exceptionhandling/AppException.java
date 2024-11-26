package com.example.demo.exceptionhandling;

import lombok.Getter;

import java.util.List;

@Getter
public class AppException extends Exception {

    private final List<AppError> errors;


    public AppException(List<AppError> errors) {
        this.errors = errors;
    }


    public AppException(AppError error) {
        this.errors = List.of(error);
    }


}
