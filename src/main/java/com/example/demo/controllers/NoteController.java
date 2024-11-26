package com.example.demo.controllers;

import com.example.demo.dtos.NoteDto;
import com.example.demo.dtotransformers.NoteDtoTransformer;
import com.example.demo.entities.NoteEntity;
import com.example.demo.exceptionhandling.AppErrorResponse;
import com.example.demo.exceptionhandling.AppException;
import com.example.demo.services.NoteService;
import com.example.demo.validators.NoteValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    NoteValidator noteValidator;

    @Autowired
    NoteDtoTransformer noteDtoTransformer;


    @Operation(
            summary = "Fetches all notes",
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
    @GetMapping("/v1/notes")
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        List<NoteEntity> noteEntities = noteService.findAllNotes();
        List<NoteDto> noteDtos = noteDtoTransformer.transformToDtos(noteEntities);
        return ResponseEntity.ok(noteDtos);
    }


    @Operation(
            summary = "Fetches note for given note id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteDto.class))),
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
    @GetMapping("/v1/notes/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable Long id) throws AppException {
        noteValidator.validateNoteIdForGet(id);
        NoteEntity noteEntity = noteService.findNote(id);
        NoteDto noteDto = noteDtoTransformer.transformToDto(noteEntity);
        return ResponseEntity.ok(noteDto);
    }


    @Operation(
            summary = "Creates a new note",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteDto.class))),
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
    @PostMapping("/v1/notes")
    public ResponseEntity<NoteDto> addNote(@RequestBody NoteDto noteDto) throws AppException {
        noteValidator.validateNoteForPost(noteDto);
        NoteEntity savedNoteEntity = noteService.addNote(noteDto);
        NoteDto savedNoteDto = noteDtoTransformer.transformToDto(savedNoteEntity);
        return ResponseEntity
                .status(201)
                .body(savedNoteDto);
    }


    @Operation(
            summary = "Updates the note",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteDto.class))),
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
    @PutMapping("/v1/notes/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id, @RequestBody NoteDto noteDto) throws AppException {
        noteValidator.validateNoteForPut(noteDto, id);
        NoteEntity savedNoteEntity = noteService.updateNote(noteDto);
        NoteDto savedNoteDto = noteDtoTransformer.transformToDto(savedNoteEntity);
        return ResponseEntity.ok(savedNoteDto);
    }


    @Operation(
            summary = "Deletes the note",
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
    @DeleteMapping("/v1/notes/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok("Note deleted from database");
    }


}
