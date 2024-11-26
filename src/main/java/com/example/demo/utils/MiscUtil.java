package com.example.demo.utils;

import java.util.function.Supplier;

public class MiscUtil {
    /**
     * Returns supplier.get()
     * Returned value will be null if supplier.get() returns null or throws any exception.
     */
    public static <T> T safelyGet(Supplier<T> supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Invalid argument supplier: null");
        }
        try {
            return supplier.get();
        } catch (Exception e) {
            return null;
        }
    }

}
