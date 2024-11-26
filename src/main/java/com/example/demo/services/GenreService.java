package com.example.demo.services;

import com.example.demo.entities.GenreEntity;
import com.example.demo.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;


    /**
     * Returns a list of all genres.
     * Returns an empty list if no genre is found.
     */
    public List<GenreEntity> findAllGenres() {
        List<GenreEntity> genres = new ArrayList<>();
        genreRepository.findAll().forEach(genres::add);
        return genres;
    }


    /**
     * Returns genre if found, else returns null.
     */
    public GenreEntity findGenreByType(String type) {
        if (type == null) return null;
        return genreRepository.findByType(type);
    }


    /**
     * If the genreEntity already exists in the database,
     * then same the database entry is updated and returned.
     * If the genreEntity does not exist in the database yet,
     * then a new database entry is inserted into the database
     * and returned.
     */
    public GenreEntity addOrUpdate(GenreEntity genreEntity) {
        return genreRepository.save(genreEntity);
    }


}
