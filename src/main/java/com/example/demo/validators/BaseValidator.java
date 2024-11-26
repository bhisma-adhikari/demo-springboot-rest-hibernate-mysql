package com.example.demo.validators;

import com.example.demo.exceptionhandling.AppError;
import com.example.demo.exceptionhandling.AppException;

import java.util.List;

public abstract class BaseValidator {

    public void throwExceptionIfNeeded(List<AppError> errors) throws AppException {
        if (!errors.isEmpty()) {
            throw new AppException(errors);
        }
    }

}
