package com.emrahkusbudu.account.service;

import com.emrahkusbudu.account.dto.CustomerDTO;
import com.emrahkusbudu.account.dto.TransactionDTO;
import com.emrahkusbudu.account.entity.Account;
import com.emrahkusbudu.account.entity.Customer;
import com.emrahkusbudu.account.exception.CustomerNotFoundException;
import com.emrahkusbudu.account.repository.AccountRepository;
import com.emrahkusbudu.account.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TransactionWebClientService transactionWebClientService;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository,TransactionWebClientService transactionWebClientService, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.transactionWebClientService = transactionWebClientService;
        this.modelMapper = modelMapper;
    }

    public  Customer  getCustomer(Long accountId) {
        return customerRepository.findById(accountId).orElseThrow(() -> new CustomerNotFoundException(accountId));
    }

    public CustomerDTO getCustomerInformation(Long customerId) {
        Customer customer = getCustomer(customerId);
        List<Long> accountIds = customer.getAccounts().stream().map(Account::getId).collect(Collectors.toList());
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        if(!accountIds.isEmpty()){
            List<TransactionDTO> transactions = transactionWebClientService.getTransactions(accountIds);
            customerDTO.getAccounts()
                    .forEach(accountDTO ->
                            accountDTO.setTransactionDTO(
                                    transactions.stream().filter(t -> t.getAccountId() == accountDTO.getId()).toList()
                            )
                    );
        }
        return customerDTO;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
