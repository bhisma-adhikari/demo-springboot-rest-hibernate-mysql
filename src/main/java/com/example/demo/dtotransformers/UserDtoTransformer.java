package com.example.demo.dtotransformers;

import com.example.demo.dtos.UserDto;
import com.example.demo.entities.BaseEntity;
import com.example.demo.entities.LoginCredentialEntity;
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
public class UserDtoTransformer {

    @Lazy
    @Autowired
    UserService userService;

    @Autowired
    LoginCredentialDtoTransformer loginCredentialDtoTransformer;

    @Lazy
    @Autowired
    NoteDtoTransformer noteDtoTransformer;

    @Lazy
    @Autowired
    NoteService noteService;


    public UserDto transformToDto(UserEntity userEntity) {
        if (userEntity == null) return null;
        Set<Long> noteIds = userEntity.getNoteEntities() == null
                ? new HashSet<>()
                : userEntity.getNoteEntities().stream().map(BaseEntity::getId).collect(Collectors.toSet());
        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .loginCredentialDto(loginCredentialDtoTransformer.transformToDto(userEntity.getLoginCredentialEntity()))
                .noteIds(noteIds)
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
    public UserEntity transformToEntity(UserDto userDto) {
        LoginCredentialEntity loginCredentialEntity = loginCredentialDtoTransformer.transformToEntity(userDto.getLoginCredentialDto());

        UserEntity userEntityDb = userService.findUser(userDto.getId());
        if (userEntityDb != null) {
            userEntityDb.setFirstName(userDto.getFirstName());
            userEntityDb.setLastName(userDto.getLastName());
            userEntityDb.setLoginCredentialEntity(loginCredentialEntity);
            if (userDto.getNoteIds() != null) {
                // Since userEntityDb.noteEntities is a persistent entity, 
                // we should not assign a new object to it (doing so would 
                // make the original noteEntities set "orphan", which is not allowed).
                // Rather, we should simply update the same persistent set. 
                userEntityDb.getNoteEntities().clear();
                userEntityDb.getNoteEntities().addAll(
                        userDto.getNoteIds().stream()
                                .map(noteId -> noteService.findNote(noteId))
                                .collect(Collectors.toSet()));
            }
            return userEntityDb;
        }

        Set<NoteEntity> noteEntities = userDto.getNoteIds() == null
                ? null
                : userDto.getNoteIds().stream()
                .map(noteId -> noteService.findNote(noteId))
                .collect(Collectors.toSet());
        return UserEntity.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .loginCredentialEntity(loginCredentialEntity)
                .noteEntities(noteEntities)
                .build();
    }


    public Set<UserEntity> transformToEntities(Set<UserDto> userDtos) {
        if (CollectionUtils.isEmpty(userDtos)) return new HashSet<>();
        return userDtos.stream().map(this::transformToEntity).collect(Collectors.toSet());
    }


    public Set<UserDto> transformToDtos(Set<UserEntity> userEntities) {
        if (CollectionUtils.isEmpty(userEntities)) return new HashSet<>();
        return userEntities.stream().map(this::transformToDto).collect(Collectors.toSet());
    }


    public List<UserDto> transformToDtos(List<UserEntity> userEntities) {
        if (CollectionUtils.isEmpty(userEntities)) return new ArrayList<>();
        return userEntities.stream().map(this::transformToDto).collect(Collectors.toList());
    }


}
