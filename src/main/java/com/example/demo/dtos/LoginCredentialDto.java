package com.example.demo.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginCredentialDto {

    private Long id;
    private String username;
    private String password;
    private Long userId;
}
