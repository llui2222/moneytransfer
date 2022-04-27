package com.testtask.moneytransfer.service;

import com.testtask.moneytransfer.data.Account;
import com.testtask.moneytransfer.exception.IllegalBalanceException;
import com.testtask.moneytransfer.repository.MoneytransferRepository;
import lombok.AllArgsConstructor;
import org.openapi.moneytransfer.model.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class MoneytransferServiceImpl implements MoneytransferService {

    private final MoneytransferRepository moneytransferRepository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void add(Integer accountId, Value value) {
        performAction(accountId, value.getValue().intValue());
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deduct(Integer accountId, Value value) {
        performAction(accountId, -value.getValue().intValue());
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transfer(Integer accountFromId, Integer accountToId, Value value) {
        performAction(accountFromId, -value.getValue().intValue());
        performAction(accountToId, value.getValue().intValue());
    }

    private void performAction(Integer accountId, int value) {
        Account account = moneytransferRepository.findById(accountId).orElseThrow(
                () -> new EntityNotFoundException("100500: account not found")
        );
        int newBalance = account.getBalance() + value;
        if (newBalance < 0) {
            throw new IllegalBalanceException("100502: negative balance");
        }
        account.setBalance(newBalance);
        moneytransferRepository.save(account);
    }
}
