package com.emrahkusbudu.account.controller;

import com.emrahkusbudu.account.dto.AccountDTO;
import com.emrahkusbudu.account.dto.AccountRequestDTO;
import com.emrahkusbudu.account.dto.UpdateBalanceRequestDTO;
import com.emrahkusbudu.account.entity.Account;
import com.emrahkusbudu.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountRequestDTO requestDTO) {
        return ResponseEntity.ok(accountService.createAccount(requestDTO));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") Long accountId) {
        return  ResponseEntity.ok(accountService.getAccount(accountId));
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity<AccountDTO> updateAccountBalance(@PathVariable("accountId") Long accountId,
                                                        @RequestBody UpdateBalanceRequestDTO request) {
        return ResponseEntity.ok(accountService.addBalanceToAccount(accountId, request));
    }
}
