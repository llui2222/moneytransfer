package com.testtask.moneytransfer.exception;

public class IllegalBalanceException extends RuntimeException {
    public IllegalBalanceException(String message) {
        super(message);
    }
}
