package com.emrahkusbudu.account.service;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class WebClientServiceImpl implements WebClientService {
    private final WebClient.Builder webClientBuilder;

    public WebClientServiceImpl( WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public <T> Mono<T> makeWebClientRequest(String endpointUrl, HttpMethod httpMethod, Object requestBody, Class<T> responseType) {
        WebClient.RequestBodySpec requestSpec = webClientBuilder.build().method(httpMethod)
                .uri(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON);

        if (requestBody != null) {
            requestSpec.body(BodyInserters.fromValue(requestBody));
        }

        return requestSpec
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Client error: " + response.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Server error: " + response.statusCode())))
                .bodyToMono(responseType)
                .doOnError(Throwable::printStackTrace);
    }

    public <T> Mono<T> makeWebClientRequest(String endpointUrl, HttpMethod httpMethod, Object requestBody, TypeReference<T> responseType) {
        WebClient.RequestBodySpec requestSpec = webClientBuilder.build().method(httpMethod)
                .uri(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON);

        if (requestBody != null) {
            requestSpec.body(BodyInserters.fromValue(requestBody));
        }

        ParameterizedTypeReference<T> typeReference = new ParameterizedTypeReference<>() {
            @Override
            public Type getType() {
                return responseType.getType();
            }
        };

        return requestSpec
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Client error: " + response.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Server error: " + response.statusCode())))
                .bodyToMono(typeReference)
                .doOnError(Throwable::printStackTrace);
    }
}