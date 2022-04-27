package com.testtask.moneytransfer.controller;

import com.testtask.moneytransfer.service.MoneytransferService;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.openapi.moneytransfer.api.V1Api;
import org.openapi.moneytransfer.model.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class MoneytransferController implements V1Api {

    private final MoneytransferService moneytransferService;

    @Override
    public ResponseEntity<Void> add(Integer accountId, Value value) {
        moneytransferService.add(accountId,value);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deduct(Integer accountId, Value value) {
        moneytransferService.deduct(accountId,value);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> transfer(Integer accountFromId, Integer accountToId, Value value) {
        moneytransferService.transfer(accountFromId, accountToId, value);
        return ResponseEntity.noContent().build();
    }
}
