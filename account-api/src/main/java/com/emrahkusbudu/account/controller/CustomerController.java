package com.emrahkusbudu.account.controller;

import com.emrahkusbudu.account.entity.Account;
import com.emrahkusbudu.account.entity.Customer;
import com.emrahkusbudu.account.service.AccountService;
import com.emrahkusbudu.account.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerInformation(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerInformation(customerId));
    }
}
