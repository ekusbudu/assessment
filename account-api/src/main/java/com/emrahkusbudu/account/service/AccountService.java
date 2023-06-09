package com.emrahkusbudu.account.service;

import com.emrahkusbudu.account.dto.*;
import com.emrahkusbudu.account.entity.Account;
import com.emrahkusbudu.account.entity.Customer;
import com.emrahkusbudu.account.enums.AccountType;
import com.emrahkusbudu.account.exception.AccountCreationException;
import com.emrahkusbudu.account.exception.AccountNotFoundException;
import com.emrahkusbudu.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final TransactionWebClientService transactionWebClientService;
    private final ModelMapper modelMapper;


    public AccountService(AccountRepository accountRepository, CustomerService customerService,TransactionWebClientService transactionWebClientService, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.transactionWebClientService = transactionWebClientService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public AccountDTO createAccount(AccountRequestDTO requestDTO) {
        try {
            double initialCredit = requestDTO.getInitialCredit();
            Customer customer = customerService.getCustomer(requestDTO.getCustomerId());

            Account account = Account.builder()
                    .customer(customer)
                    .balance(initialCredit)
                    .accountType(AccountType.CURRENT)
                    .build();

            accountRepository.save(account);
            AccountDTO accountDto = modelMapper.map(account, AccountDTO.class);
            if (initialCredit > 0) {
                TransactionDTO transaction = transactionWebClientService
                        .createTransaction(new TransactionRequestDTO(account.getId(), initialCredit, true));
                accountDto.setTransactionDTO(Arrays.asList( transaction));
            }

            return accountDto;
        } catch (Exception exception) {
            throw new AccountCreationException("Error creating account: " + requestDTO.toString() + " Exception: " + exception.getMessage());
        }
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public AccountDTO addBalanceToAccount(Long accountId, UpdateBalanceRequestDTO request) {
        Account presentAccount = this.getAccount(accountId);
        AtomicReference<Double> atomicBalance = new AtomicReference<>(presentAccount.getBalance());
        atomicBalance.updateAndGet(currentBalance -> currentBalance + request.getNewBalance());
        presentAccount.setBalance(atomicBalance.get());
        return modelMapper.map(saveAccount(presentAccount), AccountDTO.class);
    }
}
