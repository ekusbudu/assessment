package com.emrahkusbudu.account.dto;

import com.emrahkusbudu.account.enums.AccountType;
import lombok.Data;

@Data
public class AccountRequestDTO {
    private Long customerId;
    private double initialCredit;
    private AccountType accountType;

    @Override
    public String toString() {
        return "{" +
                "customerId:" + customerId +
                ", initialCredit:" + initialCredit +
                ", accountType:" + accountType +
                "}";
    }
}
