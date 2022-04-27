package com.testtask.moneytransfer.repository;

import com.testtask.moneytransfer.data.Account;
import org.springframework.data.repository.CrudRepository;

public interface MoneytransferRepository extends CrudRepository<Account, Integer> {
}
