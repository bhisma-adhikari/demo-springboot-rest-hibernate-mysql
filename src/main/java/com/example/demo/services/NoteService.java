package com.example.demo.services;

import com.example.demo.dtos.NoteDto;
import com.example.demo.dtotransformers.NoteDtoTransformer;
import com.example.demo.entities.NoteEntity;
import com.example.demo.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    NoteDtoTransformer noteDtoTransformer;

    @Autowired
    GenreService genreService;

    @Autowired
    UserService userService;


    /**
     * Returns a list of all notes.
     * Returns an empty list if no note is found.
     */
    public List<NoteEntity> findAllNotes() {
        List<NoteEntity> notes = new ArrayList<>();
        noteRepository.findAll().forEach(notes::add);
        return notes;
    }


    /**
     * Returns note if found, else returns null.
     */
    public NoteEntity findNote(Long noteId) {
        if (noteId == null) return null;
        return noteRepository.findById(noteId).orElse(null);
    }


    /**
     * Returns the new note added to the database.
     */
    public NoteEntity addNote(NoteDto noteDto) {
        NoteEntity noteEntity = noteDtoTransformer.transformToEntity(noteDto);
        return noteRepository.save(noteEntity);
    }


    /**
     * Returns the updated note entity in the database.
     */
    public NoteEntity updateNote(NoteDto noteDto) {
        NoteEntity noteEntity = noteDtoTransformer.transformToEntity(noteDto);
        return noteRepository.save(noteEntity);
    }


    /**
     * Deletes the note with given id from the database.
     * If no such note exists, this method does nothing.
     */
    public void deleteNote(Long noteId) {
        noteRepository.deleteById(noteId);
    }


}
