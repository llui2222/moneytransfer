package com.testtask.moneytransfer.service;

import org.openapi.moneytransfer.model.Value;

public interface MoneytransferService {
    void add(Integer accountId, Value value);
    void deduct(Integer accountId, Value value);
    void transfer(Integer accountFromId, Integer accountToId, Value value);
}
