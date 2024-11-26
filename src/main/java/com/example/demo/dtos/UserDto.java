package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    @JsonProperty("loginCredential")
    private LoginCredentialDto loginCredentialDto;
    private Set<Long> noteIds;
}
