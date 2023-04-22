package com.emrahkusbudu.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.emrahkusbudu.account.dto.AccountDTO;
import com.emrahkusbudu.account.dto.CustomerDTO;
import com.emrahkusbudu.account.dto.TransactionDTO;
import com.emrahkusbudu.account.entity.Account;
import com.emrahkusbudu.account.entity.Customer;
import com.emrahkusbudu.account.enums.AccountType;
import com.emrahkusbudu.account.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    TransactionWebClientService transactionWebClientService;

    @Mock
    ModelMapper modelMapper;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository, transactionWebClientService, modelMapper);
    }

    @Test
    void testGetCustomerInformation() {
        Long customerId = 1L;
        List<Long> accountIds = List.of(1L, 2L);
        List<TransactionDTO> transactions = List.of(new TransactionDTO(1L, 1L, 10.0,null),
                new TransactionDTO(2L, 2L, 20.0, null),
                new TransactionDTO(3L, 1L, 30.0, null),
                new TransactionDTO(4L, 2L, 40.0, null));
        List<Account> accounts = new ArrayList<>();
        accounts.add( Account.builder().id(1L).accountType(AccountType.CURRENT).balance(100.0).build());
        accounts.add( Account.builder().id(2L).accountType(AccountType.SAVINGS).balance(200.0).build());
        Customer customer = new Customer();
        List<AccountDTO> accountDTOs = new ArrayList<>();
        accountDTOs.add( AccountDTO.builder().id(1L).accountType(AccountType.CURRENT).balance(100.0).build());
        accountDTOs.add( AccountDTO.builder().id(2L).accountType(AccountType.SAVINGS).balance(200.0).build());
        customer.setId(customerId);
        customer.setAccounts(accounts);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAccounts(accountDTOs);
        customerDTO.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);
        when(transactionWebClientService.getTransactions(accountIds)).thenReturn(transactions);

        CustomerDTO result = customerService.getCustomerInformation(customerId);

        assertEquals(customerDTO, result);
        verify(customerRepository).findById(customerId);
        verify(modelMapper).map(customer, CustomerDTO.class);
        verify(transactionWebClientService).getTransactions(accountIds);
    }

}