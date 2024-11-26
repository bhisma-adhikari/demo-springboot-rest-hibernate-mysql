package com.example.demo.exceptionhandling;

import lombok.Getter;

@Getter
public enum AppErrorCode {
    // generic errors
    BAD_REQUEST_GENERIC("0.400.0", "BAD REQUEST; {}"),
    BAD_REQUEST_METHOD_NOT_SUPPORTED("0.400.1", "BAD REQUEST; Method not supported"),
    INTERNAL_SERVER_ERROR_GENERIC("0.500.0", "INTERNAL SERVER ERROR"),

    // endpoint-specific errors 
    GET_USERS__NOT_FOUND("1.404.1", "User not found for given id"),
    GET_USER_NOTES__USERID_NONEXISTING("1.404.2", "VALIDATION ERROR: userId non-existing; user with id {} does not exist"),

    POST_USERS__ID_PRESENT("2.400.1", "VALIDATION ERROR: id not expected"),
    POST_USERS__FIRSTNAME_MISSING("2.400.2", "VALIDATION ERROR: firstName missing"),
    POST_USERS__FIRSTNAME_INVALID("2.400.3", "VALIDATION ERROR: firstName invalid"),
    POST_USERS__LASTNAME_MISSING("2.400.4", "VALIDATION ERROR: lastName missing"),
    POST_USERS__LASTNAME_INVALID("2.400.5", "VALIDATION ERROR: lastName invalid"),
    POST_USERS__LOGIN_CREDENTIAL_MISSING("2.400.6", "VALIDATION ERROR: loginCredential missing"),
    POST_USERS__LOGIN_CREDENTIAL_ID_PRESENT("2.400.7", "VALIDATION ERROR: loginCredential.id not expected"),
    POST_USERS__LOGIN_CREDENTIAL_USERNAME_MISSING("2.400.8", "VALIDATION ERROR: loginCredential.username missing"),
    POST_USERS__LOGIN_CREDENTIAL_USERNAME_INVALID("2.400.9", "VALIDATION ERROR: loginCredential.username invalid"),
    POST_USERS__LOGIN_CREDENTIAL_USERNAME_EXISTING("2.400.10", "VALIDATION ERROR: loginCredential.username existing; This username is not available"),
    POST_USERS__LOGIN_CREDENTIAL_PASSWORD_MISSING("2.400.11", "VALIDATION ERROR: loginCredential.password missing"),
    POST_USERS__LOGIN_CREDENTIAL_PASSWORD_INVALID("2.400.12", "VALIDATION ERROR: loginCredential.password invalid"),
    POST_USERS__LOGIN_CREDENTIAL_USERID_PRESENT("2.400.13", "VALIDATION ERROR: loginCredential.userId not expected"),
    POST_USERS__NOTE_IDS_PRESENT("2.400.14", "VALIDATION ERROR: noteIds not expected; This API creates a new user (without any notes)"),


    PUT_USERS__URL_ID_NONEXISTING("3.400.1", "VALIDATION ERROR: user with id {} does not exist"),
    PUT_USERS__ID_MISSING("3.400.2", "VALIDATION ERROR: id missing"),
    PUT_USERS__ID_INVALID("3.400.3", "VALIDATION ERROR: id invalid"),
    PUT_USERS__ID_NONEXISTING("3.400.4", "VALIDATION ERROR: user with id {} does not exist"),
    PUT_USERS__ID_MISMATCHING("3.400.5", "VALIDATION ERROR: user id from request URL must match the user id in request body"),
    PUT_USERS__FIRSTNAME_MISSING("3.400.6", "VALIDATION ERROR: firstName missing"),
    PUT_USERS__FIRSTNAME_INVALID("3.400.7", "VALIDATION ERROR: firstName invalid"),
    PUT_USERS__LASTNAME_MISSING("3.400.8", "VALIDATION ERROR: lastName missing"),
    PUT_USERS__LASTNAME_INVALID("3.400.9", "VALIDATION ERROR: lastName invalid"),
    PUT_USERS__LOGIN_CREDENTIAL_MISSING("3.400.10", "VALIDATION ERROR: loginCredential missing"),
    PUT_USERS__LOGIN_CREDENTIAL_ID_PRESENT("3.400.11", "VALIDATION ERROR: loginCredential.id not expected"),
    PUT_USERS__LOGIN_CREDENTIAL_USERNAME_MISSING("3.400.12", "VALIDATION ERROR: loginCredential.username missing"),
    PUT_USERS__LOGIN_CREDENTIAL_USERNAME_INVALID("3.400.13", "VALIDATION ERROR: loginCredential.username invalid"),
    PUT_USERS__LOGIN_CREDENTIAL_USERNAME_EXISTING("3.400.14", "VALIDATION ERROR: loginCredential.username existing; This username is not available"),
    PUT_USERS__LOGIN_CREDENTIAL_PASSWORD_MISSING("3.400.15", "VALIDATION ERROR: loginCredential.password missing"),
    PUT_USERS__LOGIN_CREDENTIAL_PASSWORD_INVALID("3.400.16", "VALIDATION ERROR: loginCredential.password invalid"),
    PUT_USERS__NOTEIDS_PRESENT("3.400.17", "VALIDATION ERROR: noteIds not expected; This API cannot modify the user's notes"),

    GET_NOTES__NOT_FOUND("4.404.1", "Note not found for given id"),

    POST_NOTES__ID_PRESENT("5.400.1", "VALIDATION ERROR: id not expected"),
    POST_NOTES__TEXT_MISSING("5.400.2", "VALIDATION ERROR: text missing"),
    POST_NOTES__TEXT_INVALID("5.400.3", "VALIDATION ERROR: text invalid"),
    POST_NOTES__GENRES_MISSING("5.400.4", "VALIDATION ERROR: genres missing"),
    POST_NOTES__GENRES_EMPTY("5.400.5", "VALIDATION ERROR: genres invalid; cannot be empty"),
    POST_NOTES__USERID_MISSING("5.400.6", "VALIDATION ERROR: userId missing"),
    POST_NOTES__USERID_INVALID("5.400.7", "VALIDATION ERROR: userId invalid"),
    POST_NOTES__USERID_NONEXISTING("5.400.8", "VALIDATION ERROR: userId non-existing; user with id {} does not exist"),

    PUT_NOTES__URL_ID_NONEXISTING("6.400.1", "VALIDATION ERROR: note with id {} does not exist"),
    PUT_NOTES__ID_MISSING("6.400.2", "VALIDATION ERROR: id missing"),
    PUT_NOTES__ID_INVALID("6.400.3", "VALIDATION ERROR: id invalid"),
    PUT_NOTES__ID_NONEXISTING("6.400.4", "VALIDATION ERROR: note with id {} does not exist"),
    PUT_NOTES__ID_MISMATCHING("6.400.5", "VALIDATION ERROR: note id from request URL must match the note id in request body"),
    PUT_NOTES__ID_NOT_FOUND("6.400.6", "VALIDATION ERROR: note not found for given id"),
    PUT_NOTES__TEXT_INVALID("6.400.7", "VALIDATION ERROR: missing or text invalid"),
    PUT_NOTES__GENRES_MISSING("6.400.8", "VALIDATION ERROR: genres missing"),
    PUT_NOTES__GENRES_EMPTY("6.400.9", "VALIDATION ERROR: genres invalid; cannot be empty"),
    PUT_NOTES__USERID_MISSING("5.400.10", "VALIDATION ERROR: userId missing"),
    PUT_NOTES__USERID_INVALID("5.400.11", "VALIDATION ERROR: userId invalid"),
    PUT_NOTES__USERID_NONEXISTING("6.400.12", "VALIDATION ERROR: userId non-existing; user with id {} does not exist"),

    ;

    private final String code;
    private final String description;


    AppErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
