package com.emrahkusbudu.account.exception;

public class CustomerNotFoundException extends RuntimeException {
    private final Long customerId;

    public CustomerNotFoundException(Long customerId) {
        super("Customer not found with id: " + customerId);
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
