package com.emrahkusbudu.transaction.service;

import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

public interface WebClientService {
    <T> Mono<T> makeWebClientRequest(String endpointUrl, HttpMethod httpMethod, Object requestBody, Class<T> responseType);
}