package com.emrahkusbudu.transaction.service;

import com.emrahkusbudu.transaction.dto.AccountDTO;
import com.emrahkusbudu.transaction.dto.TransactionDTO;
import com.emrahkusbudu.transaction.dto.TransactionRequestDTO;
import com.emrahkusbudu.transaction.entity.Transaction;
import com.emrahkusbudu.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WebClientService webClientService;
    private final ModelMapper modelMapper;

    @Value("${account.service.url}")
    private String accountServiceUrl;

    public TransactionService(TransactionRepository transactionRepository, WebClientService webClientService, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.webClientService = webClientService;
        this.modelMapper = modelMapper;
    }
    public Optional<Transaction> getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Transactional
    public Transaction createTransaction(TransactionRequestDTO transactionRequest) {
        Transaction transaction = Transaction
                .builder()
                .accountId(transactionRequest.getAccountId())
                .amount(transactionRequest.getAmount())
                .build();
        transactionRepository.save(transaction);

        if (!transactionRequest.isFirst())
            updateAccountBalance();

        return transaction;
    }

    void updateAccountBalance() {
        String endpointUrl = accountServiceUrl + "/accounts/{accountId}";
        webClientService.makeWebClientRequest(endpointUrl, HttpMethod.PATCH, new AccountDTO(), Void.class).block();
    }

    public List<TransactionDTO> getTransactions(List<Long> accountIds) {
        return transactionRepository.findAllByAccountIdIn(accountIds).get().stream()
                .map(transaction ->  modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toList());
    }
}