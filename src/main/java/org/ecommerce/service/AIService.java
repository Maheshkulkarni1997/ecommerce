package org.ecommerce.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.ecommerce.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AIService {

    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String MODEL = "nvidia/nemotron-3-super-120b-a12b:free";
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int MAX_HISTORY = 20;

    private static final String SYSTEM_PROMPT = """
            You are a friendly and helpful daily communication assistant.
            Your responsibilities include:
            - Answering general knowledge questions clearly and concisely
            - Helping with day-to-day tasks like drafting messages, emails, and reminders
            - Having natural, engaging conversations on any topic
            - Providing suggestions, advice, and recommendations when asked
            - Always be warm, approachable, and easy to talk to
            """;

    // Single global conversation history
    private final List<Map<String, String>> history = new ArrayList<>(
            List.of(Map.of("role", "system", "content", SYSTEM_PROMPT))
    );

    @Value("${openrouter.api.key}")
    private String apiKey;

    public String chat(String userMessage) {
        log.debug("chat() | message={}", userMessage);

        history.add(Map.of("role", "user", "content", userMessage));
        while (history.size() > MAX_HISTORY + 1) history.remove(1);

        String jsonPayload = buildPayload(history);
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(OPENROUTER_API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("HTTP-Referer", "http://localhost:8082")
                .addHeader("X-Title", "Daily Assistant")
                .post(body)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No response body";
                log.error("OpenRouter API error - status: {}, body: {}", response.code(), errorBody);
                throw new ApplicationException("API error: " + response.code());
            }

            String responseBody = response.body().string();
            String assistantReply = parseResponse(responseBody);

            history.add(Map.of("role", "assistant", "content", assistantReply));
            return assistantReply;

        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to call OpenRouter API", e);
            throw new ApplicationException("Request failed: " + e.getMessage());
        }
    }

    private String buildPayload(List<Map<String, String>> history) {
        try {
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("model", MODEL);
            payload.put("messages", history);
            return MAPPER.writeValueAsString(payload);
        } catch (Exception e) {
            throw new ApplicationException("Failed to build request payload");
        }
    }

    private String parseResponse(String responseBody) {
        try {
            JsonNode root = MAPPER.readTree(responseBody);
            JsonNode content = root.path("choices").path(0).path("message").path("content");

            if (content.isMissingNode()) {
                log.error("Unexpected response structure: {}", responseBody);
                throw new ApplicationException("Unable to parse response from AI");
            }

            return content.asText();

        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to parse response: {}", responseBody, e);
            throw new ApplicationException("Failed to parse AI response");
        }
    }
}