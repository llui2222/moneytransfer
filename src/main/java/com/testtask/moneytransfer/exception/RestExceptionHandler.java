package com.testtask.moneytransfer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected MoneytransferError handleEntityNotFound(EntityNotFoundException ex) {
        return new MoneytransferError(ex.getMessage());
    }

    @ExceptionHandler(IllegalBalanceException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    protected MoneytransferError handleIllegalBalanceException(IllegalBalanceException ex) {
        return new MoneytransferError(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    protected MoneytransferError handleException(ConstraintViolationException ex) {
        return new MoneytransferError("100501: account id is not valid");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    protected MoneytransferError handleException(MethodArgumentNotValidException ex) {
        return new MoneytransferError("100501: value is not valid");
    }
}
