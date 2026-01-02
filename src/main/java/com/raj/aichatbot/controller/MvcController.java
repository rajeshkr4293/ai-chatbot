package com.raj.aichatbot.controller;

import com.raj.aichatbot.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MvcController {

    private final ChatService chatService;

    public MvcController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String home() {
        return "chat";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String question, Model model) {

        String response = chatService.getResponse(question);
        model.addAttribute("response", response);

        return "chat";
    }
}
