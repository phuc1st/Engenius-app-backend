package com.phuc.learn_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    TOEIC_TEST_NOT_EXIST(1020, "Toeic test not existed", HttpStatus.BAD_REQUEST),
    TOEIC_TEST_NAME_EXIST(1021, "Name of toeic test existed", HttpStatus.BAD_REQUEST),
    VOCABULARY_TOPIC_EXIST(1022, "Vocabulary topic existed", HttpStatus.BAD_REQUEST),
    VOCABULARY_TOPIC_NOT_EXIST(1023, "Vocabulary topic not existed", HttpStatus.BAD_REQUEST),
    FLASH_CARD_NOT_EXIST(1024, "Flash card not existed", HttpStatus.BAD_REQUEST),
    USER_VOCAB_TOPIC_PROGRESS_NOT_FOUND(1025, "Topic progress not found", HttpStatus.BAD_REQUEST),
    USER_VOCAB_TOPIC_PROGRESS_EXIST(1026, "User has already started this topic", HttpStatus.BAD_REQUEST),
    TASK_ALREADY_COMPLETED(1027, "Task already completed", HttpStatus.BAD_REQUEST),
    TASK_NOT_FOUND(1028, "Task not found", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(1029, "Invalid request", HttpStatus.BAD_REQUEST),
    GROUP_EXISTED(1030, "Group existed", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
