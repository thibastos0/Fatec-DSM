package edu.estudos.telegram_bot_spring_chatgpt.telegram;

import java.io.IOException;

import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.ai.moderation.ModerationResponse;
import org.springframework.ai.openai.OpenAiModerationModel;
import org.springframework.ai.openai.OpenAiModerationOptions;
import org.springframework.ai.openai.api.OpenAiModerationApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class ChatGPTSpringExample {

    @Value("${openai.api.key}")
    private String apiKey;

    public String ask(String prompt) throws IOException {
        try {
            RestClient restClient = RestClient.builder()
                    .baseUrl("https://api.openai.com")
                    .defaultHeader("Authorization", "Bearer " + apiKey)
                    .build();
            OpenAiModerationApi openAiModerationApi = new OpenAiModerationApi(restClient);
            OpenAiModerationModel openAiModerationModel = new OpenAiModerationModel(openAiModerationApi);

            OpenAiModerationOptions moderationOptions = OpenAiModerationOptions.builder()
                    .model("omni-moderation-latest")
                    .build();

            ModerationPrompt moderationPrompt = new ModerationPrompt(prompt, moderationOptions);
            ModerationResponse response = openAiModerationModel.call(moderationPrompt);

            return response.toString();
        } catch (Exception e) {
            System.err.println("Erro na moderação: " + e.getMessage());
            e.printStackTrace();
            return "❌ Erro ao chamar API: " + e.getMessage();
        }
    }
}
