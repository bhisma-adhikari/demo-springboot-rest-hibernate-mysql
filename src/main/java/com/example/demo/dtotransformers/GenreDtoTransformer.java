package com.example.demo.dtotransformers;

import com.example.demo.entities.GenreEntity;
import com.example.demo.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreDtoTransformer {


    @Autowired
    GenreService genreService;


    /**
     * Returns an entity version of the DTO.
     * <p>
     * If such entity already exists in the database,then the same entity is returned
     * with its fields updated, but not yet persisted into the database, per the DTO.
     * <p>
     * If such entity does not exist in the database,
     * then a new non-persistent entity is created and returned.
     */
    public GenreEntity transformToEntity(String genreType) {
        GenreEntity genreEntityDb = genreService.findGenreByType(genreType);
        if (genreEntityDb != null) {
            return genreEntityDb;
        }
        return GenreEntity.builder()
                .type(genreType.toUpperCase())
                .noteEntities(null)
                .build();
    }


    public Set<GenreEntity> transformToEntities(Set<String> genreTypes) {
        if (CollectionUtils.isEmpty(genreTypes)) return new HashSet<>();
        return genreTypes.stream().map(this::transformToEntity).collect(Collectors.toSet());
    }


    public String transformToString(GenreEntity genreEntity) {
        if (genreEntity == null) return null;
        return genreEntity.getType();
    }


    public List<String> transformToStrings(List<GenreEntity> genreEntities) {
        if (genreEntities == null) return new ArrayList<>();
        return genreEntities.stream().map(GenreEntity::getType).collect(Collectors.toList());
    }

}
