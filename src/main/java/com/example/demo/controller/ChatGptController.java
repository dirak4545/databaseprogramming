package com.example.demo.controller;

import com.example.demo.dto.ChatGptRequest;
import com.example.demo.dto.ChatGptResponse;
import com.example.demo.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatGptController {

    @Autowired
    private ChatGptService chatGptService;

    @PostMapping
    public ChatGptResponse getBookRecommendation(@RequestBody ChatGptRequest request) {
        ChatGptResponse response = new ChatGptResponse();
        try {
            String chatGptResponse = chatGptService.getChatGptResponse(request.getMessage());
            response.setResponse(chatGptResponse);
        } catch (Exception e) {
            response.setResponse("Error: " + e.getMessage());
        }
        return response;
    }
}