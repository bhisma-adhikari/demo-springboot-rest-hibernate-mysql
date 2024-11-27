package com.example.demo.controllers;

import com.example.demo.dtotransformers.GenreDtoTransformer;
import com.example.demo.entities.GenreEntity;
import com.example.demo.exceptionhandling.AppErrorResponse;
import com.example.demo.services.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {

    @Autowired
    GenreService genreService;

    @Autowired
    GenreDtoTransformer genreDtoTransformer;


    @Operation(
            summary = "Fetches all genres",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorResponse.class))),
            }
    )
    @GetMapping("/v1/genres")
    public ResponseEntity<List<String>> getAllGenres() {
        List<GenreEntity> genreEntities = genreService.findAllGenres();
        List<String> genreDtos = genreDtoTransformer.transformToStrings(genreEntities);
        return ResponseEntity.ok(genreDtos);
    }


}
