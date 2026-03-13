package org.ecommerce.controller;

import okhttp3.*;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final OkHttpClient CLIENT = new OkHttpClient();

    @Value("${openrouter.api.key}")
    private String apiKey;

    @PostMapping
    public String chat(@org.springframework.web.bind.annotation.RequestBody UserMessage message) {
        log.debug("API key loaded: {}", apiKey != null ? "YES" : "NO");

        String jsonPayload = """
        	    {
        	      "model": "nvidia/nemotron-3-super-120b-a12b:free",
        	      "messages": [
        	        { "role": "user", "content": "%s" }
        	      ]
        	    }
        	    """.formatted(message.getMessage().replace("\\", "\\\\").replace("\"", "\\\""));

        RequestBody body = RequestBody.create(
                jsonPayload,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(OPENROUTER_API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("HTTP-Referer", "https://your-app-url.com")
                .addHeader("X-Title", "My Ecommerce App")
                .post(body)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "⚠️ Error " + response.code() + ": " + response.body().string();
            }

            String responseBody = response.body().string();
            log.debug("Raw response: {}", responseBody);

            int choicesIdx = responseBody.indexOf("\"content\":\"");
            if (choicesIdx != -1) {
                int start = choicesIdx + 11;
                int end = responseBody.indexOf("\"", start);
                if (end > start) {
                    return responseBody.substring(start, end)
                                       .replace("\\n", "\n")
                                       .replace("\\\"", "\"");
                }
            }
            return "⚠️ Unable to parse response: " + responseBody;

        } catch (Exception e) {
            log.error("Request failed", e);
            return "⚠️ Request failed: " + e.getMessage();
        }
    }
}

class UserMessage {
    private String message;
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}