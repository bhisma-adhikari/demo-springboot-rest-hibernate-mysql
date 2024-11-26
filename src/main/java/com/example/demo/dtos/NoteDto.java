package com.example.demo.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class NoteDto {

    private Long id;
    private String text;
    private Set<String> genres;
    private Long userId;
}
