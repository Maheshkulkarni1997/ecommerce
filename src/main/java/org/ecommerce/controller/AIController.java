package org.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.ecommerce.domainmodel.AIResponse;
import org.ecommerce.domainmodel.UserMessage;
import org.ecommerce.exception.ApplicationException;
import org.ecommerce.service.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping
    public ResponseEntity<AIResponse> chat(@RequestBody UserMessage message) {
        log.debug("Received chat request");
        try {
            String reply = aiService.chat(message.getMessage());
            return ResponseEntity.ok(new AIResponse(reply, true));
        } catch (ApplicationException e) {
            log.error("Chat request failed: {}", e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(new AIResponse(e.getMessage(), false));
        }
    }
}