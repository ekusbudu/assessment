package com.emrahkusbudu.account.service;

import com.emrahkusbudu.account.dto.TransactionDTO;
import com.emrahkusbudu.account.dto.TransactionRequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionWebClientService {
    private final WebClientService webClientService;

    @Value("${TRANSACTION_API_URL}")
    private String transactionServiceUrl;

    public TransactionWebClientService(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        String endpointUrl = transactionServiceUrl + "/transactions/create";
        return webClientService
                .makeWebClientRequest(endpointUrl, HttpMethod.POST, transactionRequestDTO, TransactionDTO.class)
                .block();
    }

    public List<TransactionDTO> getTransactions(List<Long> accountIds) {
        String accountIdsVariable = accountIds.stream().map(Object::toString).collect(Collectors.joining(","));
        TypeReference<List<TransactionDTO>> responseType = new TypeReference<List<TransactionDTO>>() {};
        String endpointUrl = transactionServiceUrl + "/transactions/accounts/"+accountIdsVariable;
        return webClientService.makeWebClientRequest(endpointUrl, HttpMethod.GET, null, responseType).block();
    }
}
