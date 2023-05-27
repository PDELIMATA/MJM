package com.dydek.mjm.Service.ApiService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ApiServiceImpl implements ApiService{
    private final RestTemplate template = new RestTemplate();
    private final AtomicReference<String> accessToken = new AtomicReference<>();
    private final AtomicReference<String> apiSecret = new AtomicReference<>("Abcdef12345!");
    private final AtomicReference<String> apiClientId = new AtomicReference<>("delimata.ps@gmail.com:dydek");

    public ApiServiceImpl() {
        this.getAuthToken();
    }

    @Scheduled(initialDelay = 3_000_000, fixedDelay = 3_000_000)
    public String getAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", apiClientId.get());
        body.add("scope", "ais");
        body.add("client_secret", this.apiSecret.get());
        body.add("grant_type", "client_credentials");

        HttpEntity entity = new HttpEntity(body, headers);
        var response = template.exchange("https://id.barentswatch.no/connect/token",
                HttpMethod.POST,
                entity,
                Map.class);
        var responseBody = response.getBody();
        if (responseBody == null) {
            throw new IllegalStateException();
        }
        var token = responseBody.get("access_token");
        if (token == null) {
            throw new IllegalStateException();
        }
        this.accessToken.set((String) token);
        return ((String) token).toString();
    }

}

