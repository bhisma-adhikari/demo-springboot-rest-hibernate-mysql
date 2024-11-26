package com.example.demo.validators;

import com.example.demo.dtos.NoteDto;
import com.example.demo.dtotransformers.GenreDtoTransformer;
import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptionhandling.AppError;
import com.example.demo.exceptionhandling.AppErrorCode;
import com.example.demo.exceptionhandling.AppException;
import com.example.demo.services.NoteService;
import com.example.demo.services.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class NoteValidator extends BaseValidator {

    @Autowired
    NoteService noteService;

    @Autowired
    GenreDtoTransformer genreDtoTransformer;

    @Autowired
    UserService userService;


    public void validateNoteIdForGet(Long noteId) throws AppException {
        List<AppError> errors = new ArrayList<>();
        if (noteService.findNote(noteId) == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.GET_NOTES__NOT_FOUND));
        }
        throwExceptionIfNeeded(errors);
    }


    public void validateNoteForPost(NoteDto noteDto) throws AppException {
        List<AppError> errors = new ArrayList<>();

        // validate id 
        if (noteDto.getId() != null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_NOTES__ID_PRESENT));
        }

        // validate text 
        String text = noteDto.getText();
        if (text == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_NOTES__TEXT_MISSING));
        } else if (StringUtils.isBlank(text)) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_NOTES__TEXT_INVALID));
        }

        // validate genres 
        Set<String> genres = noteDto.getGenres();
        if (genres == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_NOTES__GENRES_MISSING));
        } else if (genres.isEmpty()) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_NOTES__GENRES_EMPTY));
        }

        // validate userId 
        Long userId = noteDto.getUserId();
        if (userId == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_NOTES__USERID_MISSING));
        } else {
            UserEntity userEntity = userService.findUser(userId);
            if (userEntity == null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_NOTES__USERID_NONEXISTING, userId));
            }
        }

        throwExceptionIfNeeded(errors);
    }


    public void validateNoteForPut(NoteDto noteDto, Long noteIdFromUrl) throws AppException {
        List<AppError> errors = new ArrayList<>();

        // validate noteIdFromUrl
        NoteEntity noteEntityFromIdFromUrl = noteService.findNote(noteIdFromUrl);
        if (noteEntityFromIdFromUrl == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__URL_ID_NONEXISTING, noteIdFromUrl));
        }

        // validate id 
        Long noteIdFromBody = noteDto.getId();
        if (noteIdFromBody == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__ID_MISSING));
        } else if (!noteIdFromBody.equals(noteIdFromUrl)) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__ID_MISMATCHING));
        }

        // validate text
        if (StringUtils.isBlank(noteDto.getText())) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__TEXT_INVALID));
        }

        // validate genres 
        Set<String> genres = noteDto.getGenres();
        if (genres == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__GENRES_MISSING));
        } else if (genres.isEmpty()) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__GENRES_EMPTY));
        }

        // validate userId 
        Long userId = noteDto.getUserId();
        if (userId == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__USER_ID_MISSING));
        } else {
            UserEntity userEntity = userService.findUser(userId);
            if (userEntity == null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_NOTES__USER_ID_NONEXISTING, userId));
            }
        }

        throwExceptionIfNeeded(errors);
    }


}
