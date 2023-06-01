package com.dydek.mjm.Service.ApiService;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ApiServiceImpl implements ApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private volatile String accessToken;

    public ApiServiceImpl() {
        generateAuthToken();
    }

    @Scheduled(initialDelay = 3_000_000, fixedDelay = 3_000_000)
    public void generateAuthToken() {
        try {
            this.accessToken = requestAuthToken();
        } catch (Exception ex) {

        }
    }

    private String requestAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String apiClientId = "delimata.ps@gmail.com:dydek";
        body.add("client_id", apiClientId);
        body.add("scope", "ais");
        String apiSecret = "Abcdef12345!";
        body.add("client_secret", apiSecret);
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://id.barentswatch.no/connect/token",
                HttpMethod.POST,
                entity,
                Map.class
        );
        Map responseBody = response.getBody();
        if (responseBody == null) {
            throw new IllegalStateException("Empty response body");
        }
        Object token = responseBody.get("access_token");
        if (token == null) {
            throw new IllegalStateException("Access token not found");
        }
        return (String) token;
    }

    public String getToken() {
        return accessToken;
    }
}
