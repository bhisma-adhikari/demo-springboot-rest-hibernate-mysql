package com.example.demo.exceptionhandling;

import lombok.Data;

import java.util.List;

@Data
public class AppErrorResponse {

    private final List<AppError> errors;


    public AppErrorResponse(List<AppError> errors) {
        this.errors = errors;
    }


    public AppErrorResponse(AppError errors) {
        this.errors = List.of(errors);
    }


}
