package com.example.demo.repositories;

import com.example.demo.entities.GenreEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<GenreEntity, Long> {
    GenreEntity findByType(String type);
}
