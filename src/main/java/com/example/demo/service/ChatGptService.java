package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ChatGptService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.client.timeout.connect:30}")
    private Long connectTimeout;

    @Value("${openai.client.timeout.read:30}")
    private Long readTimeout;

    @Value("${openai.client.timeout.write:30}")
    private Long writeTimeout;

    private OkHttpClient client;

    @PostConstruct
    public void init() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
    }

    public String getChatGptResponse(String prompt) throws IOException {
        String jsonBody = String.format("{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                model, prompt.replace("\"", "\\\""));

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(RequestBody.create(MediaType.parse("application/json"), jsonBody))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response.body().string());

            return jsonResponse.get("choices").get(0).get("message").get("content").asText();
        }
    }
}