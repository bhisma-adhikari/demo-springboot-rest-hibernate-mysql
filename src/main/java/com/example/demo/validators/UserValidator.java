package com.example.demo.validators;

import com.example.demo.dtos.LoginCredentialDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.dtotransformers.GenreDtoTransformer;
import com.example.demo.entities.LoginCredentialEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptionhandling.AppError;
import com.example.demo.exceptionhandling.AppErrorCode;
import com.example.demo.exceptionhandling.AppException;
import com.example.demo.services.LoginCredentialService;
import com.example.demo.services.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserValidator extends BaseValidator {

    @Autowired
    UserService userService;

    @Autowired
    GenreDtoTransformer genreDtoTransformer;

    @Autowired
    LoginCredentialService loginCredentialService;


    public void validateUserIdForGet(Long userId) throws AppException {
        List<AppError> errors = new ArrayList<>();
        if (userService.findUser(userId) == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.GET_USERS__NOT_FOUND));
        }
        throwExceptionIfNeeded(errors);
    }


    public void validateUserIdForGetNotes(Long userId) throws AppException {
        List<AppError> errors = new ArrayList<>();
        if (userService.findUser(userId) == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.GET_USER_NOTES__USER_ID_NONEXISTING, userId));
        }
        throwExceptionIfNeeded(errors);
    }


    public void validateUserForPost(UserDto userDto) throws AppException {
        List<AppError> errors = new ArrayList<>();

        // validate id 
        Long id = userDto.getId();
        if (id != null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__ID_PRESENT));
        }

        // validate firstName
        String firstName = userDto.getFirstName();
        if (firstName == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__FIRSTNAME_MISSING));
        } else if (StringUtils.isBlank(firstName)) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__FIRSTNAME_INVALID));
        }

        // validate lastName
        String lastName = userDto.getLastName();
        if (lastName == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LASTNAME_MISSING));
        } else if (StringUtils.isBlank(lastName)) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LASTNAME_INVALID));
        }

        // validate loginCredential 
        LoginCredentialDto loginCredentialDto = userDto.getLoginCredentialDto();
        if (loginCredentialDto == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_MISSING));
        } else {
            // validate loginCredential.id 
            if (loginCredentialDto.getId() != null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_ID_PRESENT));
            }
            // validate loginCredential.username
            String username = loginCredentialDto.getUsername();
            if (username == null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_USERNAME_MISSING));
            } else if (StringUtils.isBlank(username)) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_USERNAME_INVALID));
            } else {
                UserEntity userEntity = userService.findUserByUsername(username);
                if (userEntity != null) {
                    errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_USERNAME_EXISTING));
                }
            }
            // validate loginCredential.password
            String password = loginCredentialDto.getPassword();
            if (password == null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_PASSWORD_MISSING));
            } else if (StringUtils.isBlank(password)) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_PASSWORD_INVALID));
            }
            // validate loginCredential.userId
            Long userId = loginCredentialDto.getUserId();
            if (userId != null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__LOGIN_CREDENTIAL_USER_ID_PRESENT));
            }
        }

        // validate noteIds
        Set<Long> noteIds = userDto.getNoteIds();
        if (noteIds != null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.POST_USERS__NOTE_IDS_PRESENT));
        }

        throwExceptionIfNeeded(errors);
    }


    public void validateUserForPut(UserDto userDto, Long userIdFromUrl) throws AppException {
        List<AppError> errors = new ArrayList<>();

        // validate userIdFromUrl
        UserEntity userEntityByIdFromUrl = userService.findUser(userIdFromUrl);
        if (userEntityByIdFromUrl == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__URL_ID_NONEXISTING, userIdFromUrl));
        }

        // validate id
        Long userIdFromBody = userDto.getId();
        if (userIdFromBody == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__ID_MISSING));
        } else if (!userIdFromBody.equals(userIdFromUrl)) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__ID_MISMATCHING));
        }

        // validate firstName
        String firstName = userDto.getFirstName();
        if (firstName == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__FIRSTNAME_MISSING));
        } else if (StringUtils.isBlank(firstName)) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__FIRSTNAME_INVALID));
        }

        // validate lastName
        String lastName = userDto.getLastName();
        if (lastName == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LASTNAME_MISSING));
        } else if (StringUtils.isBlank(lastName)) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LASTNAME_INVALID));
        }

        // validate loginCredential 
        LoginCredentialDto loginCredentialDto = userDto.getLoginCredentialDto();
        if (loginCredentialDto == null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_MISSING));
        } else {
            // validate loginCredential.id 
            Long loginCredentialId = loginCredentialDto.getId();
            if (loginCredentialId == null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_ID_MISSING));
            } else {
                LoginCredentialEntity loginCredentialEntity = loginCredentialService.findLoginCredentialEntity(loginCredentialId);
                if (loginCredentialEntity == null) {
                    errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_ID_NONEXISTING, loginCredentialId));
                }
            }
            // validate loginCredential.username
            String username = loginCredentialDto.getUsername();
            if (username == null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_USERNAME_MISSING));
            } else if (StringUtils.isBlank(username)) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_USERNAME_INVALID));
            } else {
                UserEntity userEntityByUsername = userService.findUserByUsername(username);
                if (userEntityByUsername != null && !userEntityByUsername.equals(userEntityByIdFromUrl)) {
                    errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_USERNAME_EXISTING));
                }
            }
            // validate loginCredential.password
            String password = loginCredentialDto.getPassword();
            if (password == null) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_PASSWORD_MISSING));
            } else if (StringUtils.isBlank(password)) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_PASSWORD_INVALID));
            }
            // validate loginCredential.userId -- can be null; however, if present, must be correct
            Long userId = loginCredentialDto.getUserId();
            if (userId != null && !userId.equals(userIdFromBody)) {
                errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__LOGIN_CREDENTIAL_USER_ID_MISMATCHING));
            }
        }

        // validate noteIds
        Set<Long> noteIds = userDto.getNoteIds();
        if (noteIds != null) {
            errors.add(AppError.fromAppErrorCode(AppErrorCode.PUT_USERS__NOTE_IDS_PRESENT));
        }

        throwExceptionIfNeeded(errors);
    }
}
