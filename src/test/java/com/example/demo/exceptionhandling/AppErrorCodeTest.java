package com.example.demo.exceptionhandling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class AppErrorCodeTest {

    @Test
    public void testCodeUniqueness() {
        Map<String, Integer> map = new HashMap<>(); // key: AppErrorCode.code; value: count
        for (AppErrorCode appErrorCode : AppErrorCode.values()) {
            int count = map.getOrDefault(appErrorCode.getCode(), 0);
            map.put(appErrorCode.getCode(), ++count);
        }
        Set<String> duplicateCodes = map.keySet().stream().filter(key -> map.get(key) > 1).collect(Collectors.toSet());
        if (!duplicateCodes.isEmpty()) {
            fail("Duplicate error codes detected: " + duplicateCodes);
        }
    }


    @Test
    public void testApiCodeCorrectness() {
        for (AppErrorCode appErrorCode : AppErrorCode.values()) {
            int httpStatusCode = Integer.parseInt(appErrorCode.getCode().split("\\.")[0]);
            if (httpStatusCode < 0) {
                fail("Invalid error code detected: " + appErrorCode.getCode());
            }
        }
    }


    @Test
    public void testHttpStatusCodeCorrectness() {
        for (AppErrorCode appErrorCode : AppErrorCode.values()) {
            int httpStatusCode = Integer.parseInt(appErrorCode.getCode().split("\\.")[1]);
            if (httpStatusCode < 400 || httpStatusCode > 599) {
                fail("Invalid error code detected: " + appErrorCode.getCode());
            }
        }
    }


    @Test
    public void testErrorCodeCorrectness() {
        for (AppErrorCode appErrorCode : AppErrorCode.values()) {
            int httpStatusCode = Integer.parseInt(appErrorCode.getCode().split("\\.")[2]);
            if (httpStatusCode < 0) {
                fail("Invalid error code detected: " + appErrorCode.getCode());
            }
        }
    }


}
