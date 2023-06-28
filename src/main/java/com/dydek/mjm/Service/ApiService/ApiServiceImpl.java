package com.dydek.mjm.Service.ApiService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ApiServiceImpl implements ApiService {
    private final WebClient webClient;
    private volatile String accessToken;

    public ApiServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("https://id.barentswatch.no/connect/token")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        generateAuthToken();
    }

    @Scheduled(initialDelay = 3_000_000, fixedDelay = 3_000_000)
    public void generateAuthToken() {
        try {
            this.accessToken = requestAuthToken();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String requestAuthToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String apiClientId = "delimata.ps@gmail.com:dydek";
        body.add("client_id", apiClientId);
        body.add("scope", "ais");
        String apiSecret = "Abcdef12345!";
        body.add("client_secret", apiSecret);
        body.add("grant_type", "client_credentials");

        return webClient.post()
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(Map.class)
                .map(responseBody -> {
                    Object token = responseBody.get("access_token");
                    if (token == null) {
                        throw new IllegalStateException("Access token not found");
                    }
                    return (String) token;
                })
                .block();
    }

    public String getToken() {
        return accessToken;
    }
}
