package com.example.demo.exceptionhandling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class AppErrorCodeTest {

    @Test
    public void validateAppErrorCodes() {
        List<String> codes = new ArrayList<>();
        for (AppErrorCode appErrorCode : AppErrorCode.values()) {

            // validate uniqueness 
            if (codes.contains(appErrorCode.getCode())) {
                fail("Duplicate AppErrorCode.code: " + appErrorCode.getCode() + "; All error codes must be unique.");
            } else {
                codes.add(appErrorCode.getCode());
            }

            // validate format
            String[] tokens = appErrorCode.getCode().split("\\.");
            if (tokens.length != 3) {
                fail("Invalid AppErrorCode.code: " + appErrorCode.getCode() + "; Must be in format '*.*.*");
            }

            // valid API code
            try {
                int apiCode = Integer.parseInt(tokens[0]);
                if (apiCode < 0) {
                    fail("Invalid AppErrorCode.code: " + appErrorCode.getCode() + "; The first token must be an a non-negative integer.");
                }
            } catch (Exception e) {
                fail("Invalid AppErrorCode.code: " + appErrorCode.getCode() + "; The first token must be an a non-negative integer.");
            }

            // validate HTTP status code
            try {
                int httpStatusCode = Integer.parseInt(tokens[1]);
                if (httpStatusCode < 400 || httpStatusCode > 599) {
                    fail("Invalid AppErrorCode.code: " + appErrorCode.getCode() + "; The second token must be an an integer in range [400, 599].");
                }
            } catch (Exception e) {
                fail("Invalid AppErrorCode.code: " + appErrorCode.getCode() + "; The second token must be an an integer in range [400, 599].");
            }

            // validate Error code
            try {
                int errorCode = Integer.parseInt(tokens[2]);
                if (errorCode < 0) {
                    fail("Invalid AppErrorCode.code: " + appErrorCode.getCode() + "; The third token must be a non-negative integer.");
                }
            } catch (Exception e) {
                fail("Invalid AppErrorCode.code: " + appErrorCode.getCode() + "; The third token must be a non-negative integer.");
            }
        }
    }


}
