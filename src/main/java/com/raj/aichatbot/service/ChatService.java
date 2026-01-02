package com.raj.aichatbot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {

    private static final String OLLAMA_URL =
            "http://host.docker.internal:11434/api/generate";

    private final RestTemplate restTemplate;

    public ChatService() {
        this.restTemplate = new RestTemplate();
    }

    public String getResponse(String question) {

        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-oss:120b-cloud");
            requestBody.put("prompt", question);
            requestBody.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    OLLAMA_URL,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getBody() != null && response.getBody().get("response") != null) {
                return response.getBody().get("response").toString();
            }

            return "AI did not return any response.";

        } catch (RestClientException ex) {
            return "AI is temporarily unavailable. Please try again.";
        } catch (Exception ex) {
            return "Something went wrong while processing your request.";
        }
    }
}
