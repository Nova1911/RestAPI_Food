package com.service.food.exceptionHandler;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, int errorCode, HttpStatus status){
        ErrorResponse errorResponse = new ErrorResponse(errorCode, e.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ServiceValidationException.class)
    public ResponseEntity<ErrorResponse> handleServiceValidationException(ServiceValidationException e){
        int errorCode = e.getErrorCode();
        return createErrorResponse(e, errorCode, HttpStatus.resolve(errorCode));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e){
        int errorCode = 404;
        logger.error("NoSuchElementException: No value is present in DB");
        return createErrorResponse(e, errorCode, HttpStatus.resolve(errorCode));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        int errorCode = 400;
        logger.error("HttpMessageNotReadableException: Invalid data format");
        return createErrorResponse(e, errorCode, HttpStatus.resolve(errorCode));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        int errorCode = 400;
        logger.error("MethodArgumentTypeMismatchException: Invalid parameter");
        return createErrorResponse(e, errorCode, HttpStatus.resolve(errorCode));
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ErrorResponse> handleCannotCreateTransactionException(CannotCreateTransactionException e){
        int errorCode = 500;
        logger.error("CannotCreateTransactionException: Can not create a database transaction");
        return createErrorResponse(e, errorCode, HttpStatus.resolve(errorCode));
    }

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<ErrorResponse> handleJDBCConnectionException(JDBCConnectionException e){
        int errorCode = 500;
        logger.error("JDBCConnectionException: No database connection");
        return createErrorResponse(e, errorCode, HttpStatus.resolve(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        int errorCode = 500;
        logger.error("Exception: " + e.getMessage());
        return createErrorResponse(e, errorCode, HttpStatus.resolve(errorCode));
    }
}
