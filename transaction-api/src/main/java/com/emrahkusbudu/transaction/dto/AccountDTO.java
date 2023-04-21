package com.emrahkusbudu.transaction.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {
    private Long accountId;
    private List<TransactionDTO> transactions;
}
