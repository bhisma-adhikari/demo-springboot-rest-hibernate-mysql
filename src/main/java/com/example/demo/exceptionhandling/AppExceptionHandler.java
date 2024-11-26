package com.example.demo.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppErrorResponse> handleException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(new AppErrorResponse(AppError.fromAppErrorCode(AppErrorCode.INTERNAL_SERVER_ERROR_GENERIC)));
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<AppErrorResponse> handleException(NoResourceFoundException exception) {
        return ResponseEntity
                .status(404)
                .body(new AppErrorResponse(AppError.fromAppErrorCode(AppErrorCode.BAD_REQUEST_GENERIC, exception.getMessage())));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<AppErrorResponse> handleException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(400)
                .body(new AppErrorResponse(AppError.fromAppErrorCode(AppErrorCode.BAD_REQUEST_GENERIC, exception.getMessage())));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<AppErrorResponse> handleException(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity
                .status(400)
                .body(new AppErrorResponse(AppError.fromAppErrorCode(AppErrorCode.BAD_REQUEST_METHOD_NOT_SUPPORTED)));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AppErrorResponse> handleException(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(400)
                .body(new AppErrorResponse(AppError.fromAppErrorCode(AppErrorCode.BAD_REQUEST_GENERIC, exception.getMessage())));
    }


    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppErrorResponse> handleAppException(AppException appException) {
        return ResponseEntity
                .status(resolveHttpStatusCode(appException))
                .body(new AppErrorResponse(appException.getErrors()));
    }


    /**
     * ASSUMPTIONS:
     * - appException.errors has at least one entry
     */
    private int resolveHttpStatusCode(AppException appException) {
        Set<Integer> httpStatusCodes = appException.getErrors().stream()
                .map(AppError::getHttpStatusCode)
                .collect(Collectors.toSet());
        return resolveHttpStatusCode(httpStatusCodes);

    }


    private int resolveHttpStatusCode(Set<Integer> httpStatusCodes) {
        if (httpStatusCodes.size() == 1) {
            return httpStatusCodes.iterator().next();
        }
        List<Integer> priorityList = List.of(401, 403, 400, 500);
        for (Integer statusCode : priorityList) {
            if (httpStatusCodes.contains(statusCode)) {
                return statusCode;
            }
        }
        return 500;
    }


}
