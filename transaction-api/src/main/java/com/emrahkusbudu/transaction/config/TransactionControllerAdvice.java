package com.emrahkusbudu.transaction.config;

import com.emrahkusbudu.transaction.dto.ErrorResponse;
import com.emrahkusbudu.transaction.exception.TransactionCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class TransactionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionCreationException.class)
    public ResponseEntity<ErrorResponse> handleAccountCreationException(TransactionCreationException ex) {
        logError("Transaction creation exception", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(String logLabel, Exception ex) {
        log.error(logLabel + ": {}", ex.getMessage());
    }
}
