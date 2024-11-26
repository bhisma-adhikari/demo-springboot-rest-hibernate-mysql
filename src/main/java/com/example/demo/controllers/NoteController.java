package com.example.demo.controllers;

import com.example.demo.dtos.NoteDto;
import com.example.demo.dtotransformers.NoteDtoTransformer;
import com.example.demo.entities.NoteEntity;
import com.example.demo.exceptionhandling.AppException;
import com.example.demo.services.NoteService;
import com.example.demo.validators.NoteValidator;
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


    @GetMapping("/v1/notes")
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        List<NoteEntity> noteEntities = noteService.findAllNotes();
        List<NoteDto> noteDtos = noteDtoTransformer.transformToDtos(noteEntities);
        return ResponseEntity.ok(noteDtos);
    }


    @GetMapping("/v1/notes/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable Long id) throws AppException {
        noteValidator.validateNoteIdForGet(id);
        NoteEntity noteEntity = noteService.findNote(id);
        NoteDto noteDto = noteDtoTransformer.transformToDto(noteEntity);
        return ResponseEntity.ok(noteDto);
    }


    @PostMapping("/v1/notes")
    public ResponseEntity<NoteDto> addNote(@RequestBody NoteDto noteDto) throws AppException {
        noteValidator.validateNoteForPost(noteDto);
        NoteEntity savedNoteEntity = noteService.addNote(noteDto);
        NoteDto savedNoteDto = noteDtoTransformer.transformToDto(savedNoteEntity);
        return ResponseEntity
                .status(201)
                .body(savedNoteDto);
    }


    @PutMapping("/v1/notes/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id, @RequestBody NoteDto noteDto) throws AppException {
        noteValidator.validateNoteForPut(noteDto, id);
        NoteEntity savedNoteEntity = noteService.updateNote(noteDto);
        NoteDto savedNoteDto = noteDtoTransformer.transformToDto(savedNoteEntity);
        return ResponseEntity.ok(savedNoteDto);
    }


    @DeleteMapping("/v1/notes/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok("Note deleted from database");
    }


}
