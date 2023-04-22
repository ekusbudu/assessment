package com.emrahkusbudu.account.dto;

import com.emrahkusbudu.account.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private double balance;
    private AccountType accountType;
    private List<TransactionDTO> transactionDTO;
}
