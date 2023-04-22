package com.emrahkusbudu.transaction.controller;

import com.emrahkusbudu.transaction.dto.TransactionDTO;
import com.emrahkusbudu.transaction.dto.TransactionRequestDTO;
import com.emrahkusbudu.transaction.entity.Transaction;
import com.emrahkusbudu.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequest) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable("transactionId") Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }

    @GetMapping("/accounts/{accountIds}")
    public ResponseEntity<List<TransactionDTO>> getAccountsTransactions(@PathVariable("accountIds") List<Long> accountIds) {
        return ResponseEntity.ok(transactionService.getTransactions(accountIds));
    }
}