package com.emrahkusbudu.account.dto;

import com.emrahkusbudu.account.enums.AccountType;
import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {
    private Long id;
    private double balance;
    private AccountType accountType;
    private List<TransactionDTO> transactionDTO;
}
