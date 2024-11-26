package com.example.demo.exceptionhandling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class AppError {

    private String code;
    private String description;


    private AppError(String code, String description) {
        this.code = code;
        this.description = description;
    }


//    public static AppError fromAppErrorCode(AppErrorCode appErrorCode) {
//        if (appErrorCode == null) {
//            throw new IllegalArgumentException("Invalid argument appErrorCode: null");
//        }
//        return new AppError(appErrorCode.getCode(), appErrorCode.getDescription());
//    }


    public static AppError fromAppErrorCode(AppErrorCode appErrorCode, Object... arguments) {
        if (appErrorCode == null) {
            throw new IllegalArgumentException("Invalid argument appErrorCode: null");
        }
        String description = appErrorCode.getDescription();
        int nPlaceholders = StringUtils.countOccurrencesOf(description, "{}");
        if (nPlaceholders != arguments.length) {
            String errMsg = String.format("Invalid arguments: %s; must match the number of placeholders: %s in AppErrorCode.description", arguments.length, nPlaceholders);
            throw new IllegalArgumentException(errMsg);
        }
        description = description.replace("{}", "%s");
        description = String.format(description, arguments);
        return new AppError(appErrorCode.getCode(), description);
    }


    @JsonIgnore
    public int getHttpStatusCode() {
        return Integer.parseInt(this.getCode().split("\\.")[1]);
    }


}
