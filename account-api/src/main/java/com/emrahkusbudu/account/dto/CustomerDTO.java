package com.emrahkusbudu.account.dto;

import com.emrahkusbudu.account.enums.AccountType;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<AccountDTO> accounts;
}
