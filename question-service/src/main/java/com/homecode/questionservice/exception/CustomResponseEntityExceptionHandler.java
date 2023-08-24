package com.homecode.questionservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NoDataFoundException.class })
    protected ResponseEntity<Object> handleNoDataFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "No data available.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleOtherExceptions(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "An error occurred.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { QuestionNotAddedException.class })
    protected ResponseEntity<Object> handleQuestionNotAdded(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Question could not be added.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = { QuestionUpdateException.class })
    protected ResponseEntity<Object> handleQuestionUpdateException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Question could not be updated.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = { QuestionDeleteException.class })
    protected ResponseEntity<Object> handleQuestionDeleteException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Question could not be deleted.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { QuizQuestionsRetrieveException.class })
    protected ResponseEntity<Object> handleQuizQuestionsRetrieveException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Error retrieving questions for quiz.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}

