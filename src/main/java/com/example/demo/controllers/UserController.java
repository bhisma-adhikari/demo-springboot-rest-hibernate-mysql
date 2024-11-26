package com.example.demo.controllers;

import com.example.demo.dtos.NoteDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.dtotransformers.NoteDtoTransformer;
import com.example.demo.dtotransformers.UserDtoTransformer;
import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptionhandling.AppErrorResponse;
import com.example.demo.exceptionhandling.AppException;
import com.example.demo.services.NoteService;
import com.example.demo.services.UserService;
import com.example.demo.validators.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    UserDtoTransformer userDtoTransformer;

    @Autowired
    NoteService noteService;

    @Autowired
    NoteDtoTransformer noteDtoTransformer;


    @Operation(
            summary = "Fetches all users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
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
    @GetMapping("/v1/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> userEntities = userService.findAllUsers();
        List<UserDto> userDtos = userDtoTransformer.transformToDtos(userEntities);
        return ResponseEntity.ok(userDtos);
    }


    @Operation(
            summary = "Fetches user for given user id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorResponse.class))),
            }
    )
    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws AppException {
        userValidator.validateUserIdForGet(id);
        UserEntity userEntity = userService.findUser(id);
        UserDto userDto = userDtoTransformer.transformToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }


    @Operation(
            summary = "Fetches all notes of given user id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = NoteDto.class)))),
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
    @GetMapping("/v1/users/{userId}/notes")
    public ResponseEntity<List<NoteDto>> getAllNotesOfUser(@PathVariable Long userId) throws AppException {
        userValidator.validateUserIdForGetNotes(userId);
        List<NoteEntity> noteEntities = noteService.findAllNotes().stream()
                .filter(noteEntity -> noteEntity.getUserEntity().getId().equals(userId))
                .collect(Collectors.toList());
        List<NoteDto> noteDtos = noteDtoTransformer.transformToDtos(noteEntities);
        return ResponseEntity.ok(noteDtos);
    }


    @Operation(
            summary = "Creates a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
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
    @PostMapping("/v1/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) throws AppException {
        userValidator.validateUserForPost(userDto);
        UserEntity savedUserEntity = userService.addUser(userDto);
        UserDto savedUserDto = userDtoTransformer.transformToDto(savedUserEntity);
        return ResponseEntity
                .status(201)
                .body(savedUserDto);
    }


    @Operation(
            summary = "Updates the user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
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
    @PutMapping("/v1/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws AppException {
        userValidator.validateUserForPut(userDto, id);
        UserEntity savedUserEntity = userService.updateUser(userDto);
        UserDto savedUserDto = userDtoTransformer.transformToDto(savedUserEntity);
        return ResponseEntity.ok(savedUserDto);
    }


    @Operation(
            summary = "Deletes the user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
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
    @DeleteMapping("/v1/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted from database");
    }


}
