package com.emrahkusbudu.account.service;

import com.emrahkusbudu.account.dto.AccountDTO;
import com.emrahkusbudu.account.dto.AccountRequestDTO;
import com.emrahkusbudu.account.dto.TransactionRequestDTO;
import com.emrahkusbudu.account.dto.UpdateBalanceRequestDTO;
import com.emrahkusbudu.account.entity.Account;
import com.emrahkusbudu.account.entity.Customer;
import com.emrahkusbudu.account.enums.AccountType;
import com.emrahkusbudu.account.exception.AccountNotFoundException;
import com.emrahkusbudu.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private TransactionWebClientService transactionWebClientService;

    @Mock
    private ModelMapper modelMapper;

    private AccountService accountService;


    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository, customerService, transactionWebClientService, modelMapper);
    }

    @Test
    void createAccount_withInitialCredit_shouldCreateAccountAndTransaction() {
        // given
        long customerId = 1L;
        double initialCredit = 100.0;
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        accountRequestDTO.setCustomerId(customerId);
        accountRequestDTO.setInitialCredit(initialCredit);
        Customer customer = Customer.builder()
                .id(customerId)
                .firstName("Ali")
                .lastName("Veli")
                .email("abc@taf.com")
                .build();
        Account account = Account.builder()
                .accountType(AccountType.CURRENT)
                .customer(customer)
                .balance(initialCredit)
                .build();
        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(accountRepository.save(any())).thenReturn(account);
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(account.getId(), initialCredit, true);
        // when
        AccountDTO result = accountService.createAccount(accountRequestDTO);

        // then
        verify(customerService).getCustomer(customerId);
        verify(accountRepository).save(account);
        verify(transactionWebClientService).createTransaction(transactionRequestDTO);
        verify(modelMapper).map(account, AccountDTO.class);
    }

    @Test
    void getAccount_withNonExistingAccountId_shouldThrowAccountNotFoundException() {
        // given
        long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // when
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(accountId));
        verify(accountRepository).findById(accountId);
    }

    @Test
    void updateAccountBalance_shouldUpdateBalanceAndReturnAccountDTO() {
        // given
        long accountId = 1L;
        double currentBalance = 100.0;
        double newBalance = 50.0;
        UpdateBalanceRequestDTO request = new UpdateBalanceRequestDTO();
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(currentBalance);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        AccountDTO expectedAccountDTO = new AccountDTO();
        expectedAccountDTO.setId(accountId);
        expectedAccountDTO.setBalance(currentBalance + newBalance);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(expectedAccountDTO);

        // when
        AccountDTO result = accountService.updateAccountBalance(accountId, request);

        // then
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        verify(modelMapper).map(account, AccountDTO.class);
        assertEquals(expectedAccountDTO, result);
    }
}