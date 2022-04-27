package com.testtask.moneytransfer.exception;

import lombok.Getter;

@Getter
public class MoneytransferError {

    private final String message;
    public MoneytransferError(String message) {
        this.message = message;
    }
}
