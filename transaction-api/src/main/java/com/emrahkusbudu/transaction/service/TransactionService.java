package com.emrahkusbudu.transaction.service;

import com.emrahkusbudu.transaction.dto.TransactionDTO;
import com.emrahkusbudu.transaction.dto.TransactionRequestDTO;
import com.emrahkusbudu.transaction.dto.UpdateBalanceRequestDTO;
import com.emrahkusbudu.transaction.entity.Transaction;
import com.emrahkusbudu.transaction.exception.TransactionNotFoundException;
import com.emrahkusbudu.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WebClientService webClientService;
    private final ModelMapper modelMapper;

    @Value("${ACCOUNT_API_URL}")
    private String accountServiceUrl;

    public TransactionService(TransactionRepository transactionRepository, WebClientService webClientService, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.webClientService = webClientService;
        this.modelMapper = modelMapper;
    }
    public  TransactionDTO  getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(transactionId));
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionRequestDTO transactionRequest) {
        Transaction transaction = Transaction
                .builder()
                .accountId(transactionRequest.getAccountId())
                .amount(transactionRequest.getAmount())
                .build();
        transactionRepository.save(transaction);

        if (!transactionRequest.isFirst())
            updateAccountBalance(transactionRequest);

        return modelMapper.map(transaction, TransactionDTO.class) ;
    }

    void updateAccountBalance(TransactionRequestDTO transactionRequest) {
        String endpointUrl = accountServiceUrl + "/accounts/" + transactionRequest.getAccountId();
        webClientService.makeWebClientRequest(endpointUrl, HttpMethod.PATCH, new UpdateBalanceRequestDTO(transactionRequest.getAmount()), Void.class).block();
    }

    public List<TransactionDTO> getTransactions(List<Long> accountIds) {
        return transactionRepository.findAllByAccountIdIn(accountIds).get().stream()
                .map(transaction ->  modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toList());
    }
}