package com.emrahkusbudu.transaction.service;

import com.emrahkusbudu.transaction.dto.TransactionDTO;
import com.emrahkusbudu.transaction.dto.TransactionRequestDTO;
import com.emrahkusbudu.transaction.dto.UpdateBalanceRequestDTO;
import com.emrahkusbudu.transaction.entity.Transaction;
import com.emrahkusbudu.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WebClientService webClientService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository, webClientService, modelMapper);
    }

    @Test
    public void testGetTransaction() {
        Long transactionId = 1L;
        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .accountId(1L)
                .amount(100.00)
                .build();
        TransactionDTO transactionDto = TransactionDTO.builder()
                .id(transactionId)
                .accountId(1L)
                .amount(100.00)
                .build();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(modelMapper.map(transaction, TransactionDTO.class)).thenReturn(transactionDto);
        TransactionDTO result = transactionService.getTransaction(transactionId);

        assertNotNull(result);
        assertEquals(transactionId, result.getId());
    }

    @Test
    public void testCreateTransaction() {
        ReflectionTestUtils.setField(transactionService, "accountServiceUrl", "http://localhost:9090/account-api");
        Long accountId = 1L;
        Double amount = 100.00;
        TransactionRequestDTO transactionRequest = TransactionRequestDTO.builder()
                .accountId(accountId)
                .amount(amount)
                .isFirst(false)
                .build();
        Transaction transaction = Transaction.builder()
                .id(1L)
                .accountId(accountId)
                .amount(amount)
                .build();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(webClientService.makeWebClientRequest(anyString(), any(HttpMethod.class), any(UpdateBalanceRequestDTO.class), any()))
                .thenReturn(Mono.empty());
        transactionService.createTransaction(transactionRequest);

        assertEquals(accountId, transaction.getAccountId());
        assertEquals(amount, transaction.getAmount());
    }
}