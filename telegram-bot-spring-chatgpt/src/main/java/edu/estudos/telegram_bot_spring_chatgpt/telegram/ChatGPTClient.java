package edu.estudos.telegram_bot_spring_chatgpt.telegram;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatGPTClient {

    @Value("${openai.api.key}")
    private String apiKey;

    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    public String ask(String prompt) throws IOException {
        
        MediaType mediaType = MediaType.parse("application/json");
        //modelname: consultar em https://platform.openai.com/docs/pricing ou https://platform.openai.com/docs/models exemplos: "gpt-3.5-turbo", "gpt-4", "gpt-4-turbo", "gpt-5-nano-2025-08-07", "omni-moderation-2024-09-26" (escolher conforme necessidade e custo)
        String requestBody = "{\"model\": \"gpt-5-nano-2025-08-07\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
        
        System.out.println("Request body: " + requestBody);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .post(okhttp3.RequestBody.create(requestBody, mediaType))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            assert response.body() != null;
            String responseBody = response.body().string();
            System.out.println("Response status: " + response.code());
            System.out.println("Response body: " + responseBody);
            
            // Verifica se houve erro na requisição
            if (!response.isSuccessful()) {
                JSONObject errorResponse = new JSONObject(responseBody);
                String errorMessage = errorResponse.getJSONObject("error").getString("message");
                return "❌ Erro na API: " + errorMessage + " (código: " + response.code() + ")";
            }
            
            JSONObject jsonResponse = new JSONObject(responseBody);
           
            return jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content").trim();
        }
    }

}
