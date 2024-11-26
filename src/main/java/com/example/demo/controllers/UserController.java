package com.example.demo.controllers;

import com.example.demo.dtos.NoteDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.dtotransformers.NoteDtoTransformer;
import com.example.demo.dtotransformers.UserDtoTransformer;
import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptionhandling.AppException;
import com.example.demo.services.NoteService;
import com.example.demo.services.UserService;
import com.example.demo.validators.UserValidator;
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


    @GetMapping("/v1/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> userEntities = userService.findAllUsers();
        List<UserDto> userDtos = userDtoTransformer.transformToDtos(userEntities);
        return ResponseEntity.ok(userDtos);
    }


    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws AppException {
        userValidator.validateUserIdForGet(id);
        UserEntity userEntity = userService.findUser(id);
        UserDto userDto = userDtoTransformer.transformToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }


    @GetMapping("/v1/users/{userId}/notes")
    public ResponseEntity<List<NoteDto>> getAllNotesOfUser(@PathVariable Long userId) throws AppException {
        userValidator.validateUserIdForGetNotes(userId);
        List<NoteEntity> noteEntities = noteService.findAllNotes().stream()
                .filter(noteEntity -> noteEntity.getUserEntity().getId().equals(userId))
                .collect(Collectors.toList());
        List<NoteDto> noteDtos = noteDtoTransformer.transformToDtos(noteEntities);
        return ResponseEntity.ok(noteDtos);
    }


    @PostMapping("/v1/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) throws AppException {
        userValidator.validateUserForPost(userDto);
        UserEntity savedUserEntity = userService.addUser(userDto);
        UserDto savedUserDto = userDtoTransformer.transformToDto(savedUserEntity);
        return ResponseEntity
                .status(201)
                .body(savedUserDto);
    }


    @PutMapping("/v1/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws AppException {
        userValidator.validateUserForPut(userDto, id);
        UserEntity savedUserEntity = userService.updateUser(userDto);
        UserDto savedUserDto = userDtoTransformer.transformToDto(savedUserEntity);
        return ResponseEntity.ok(savedUserDto);
    }


    @DeleteMapping("/v1/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted from database");
    }


}
