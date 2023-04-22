package com.emrahkusbudu.transaction.exception;

public class TransactionNotFoundException extends RuntimeException {

    private final Long transactionId;

    public TransactionNotFoundException(Long transactionId) {
        super("Transaction not found with id: " + transactionId);
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }
}
