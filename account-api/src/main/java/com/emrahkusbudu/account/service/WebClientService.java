package com.emrahkusbudu.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

public interface WebClientService {
    <T> Mono<T> makeWebClientRequest(String endpointUrl, HttpMethod httpMethod, Object requestBody, Class<T> responseType);
    <T> Mono<T> makeWebClientRequest(String endpointUrl, HttpMethod httpMethod, Object requestBody, TypeReference<T> responseType);
}