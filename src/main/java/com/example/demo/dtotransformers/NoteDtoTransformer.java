package com.example.demo.dtotransformers;

import com.example.demo.dtos.NoteDto;
import com.example.demo.entities.GenreEntity;
import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.NoteService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NoteDtoTransformer {

    @Lazy
    @Autowired
    NoteService noteService;

    @Autowired
    GenreDtoTransformer genreDtoTransformer;

    @Lazy
    @Autowired
    UserService userService;


    public NoteDto transformToDto(NoteEntity noteEntity) {
        Set<String> genres = noteEntity.getGenreEntities().stream()
                .map(GenreEntity::getType)
                .collect(Collectors.toSet());
        return NoteDto.builder()
                .id(noteEntity.getId())
                .text(noteEntity.getText())
                .genres(genres)
                .userId(noteEntity.getUserEntity().getId())
                .build();
    }


    /**
     * Returns an entity version of the DTO.
     * <p>
     * If such entity already exists in the database,then the same entity is returned
     * with its fields updated, but not yet persisted into the database, per the DTO.
     * <p>
     * If such entity does not exist in the database,
     * then a new non-persistent entity is created and returned.
     */
    public NoteEntity transformToEntity(NoteDto noteDto) {
        Set<GenreEntity> genreEntities = genreDtoTransformer.transformToEntities(noteDto.getGenres());
        UserEntity userEntity = userService.findUser(noteDto.getUserId());

        NoteEntity noteEntityDb = noteService.findNote(noteDto.getId());
        if (noteEntityDb != null) {
            noteEntityDb.setText(noteDto.getText());
            noteEntityDb.setGenreEntities(genreEntities);
            noteEntityDb.setUserEntity(userEntity);
            return noteEntityDb;
        }

        return NoteEntity.builder()
                .id(noteDto.getId())
                .text(noteDto.getText())
                .genreEntities(genreEntities)
                .userEntity(userEntity)
                .build();
    }


    public Set<NoteEntity> transformToEntities(Set<NoteDto> noteDtos) {
        if (CollectionUtils.isEmpty(noteDtos)) return new HashSet<>();
        return noteDtos.stream().map(this::transformToEntity).collect(Collectors.toSet());
    }


    public Set<NoteDto> transformToDtos(Set<NoteEntity> noteEntities) {
        if (CollectionUtils.isEmpty(noteEntities)) return new HashSet<>();
        return noteEntities.stream().map(this::transformToDto).collect(Collectors.toSet());
    }


    public List<NoteDto> transformToDtos(List<NoteEntity> noteEntities) {
        if (CollectionUtils.isEmpty(noteEntities)) return new ArrayList<>();
        return noteEntities.stream().map(this::transformToDto).collect(Collectors.toList());
    }


}
