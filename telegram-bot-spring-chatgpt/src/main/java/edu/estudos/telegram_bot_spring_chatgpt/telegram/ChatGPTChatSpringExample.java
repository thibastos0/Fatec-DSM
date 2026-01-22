package edu.estudos.telegram_bot_spring_chatgpt.telegram;

import java.io.IOException;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatGPTChatSpringExample {

    @Value("${openai.api.key}")
    private String apiKey;

    public String ask(String prompt) throws IOException {
        try {
            
            OpenAiApi openAiApi = OpenAiApi.builder()
                    .apiKey(apiKey)
                    .build();
            
            
            OpenAiChatModel chatModel = OpenAiChatModel.builder()
                    .openAiApi(openAiApi)
                    .build();
            // Versão gpt-5 ainda não disponível na Spring AI, então usamos gpt-4.1-nano-2025-04-14
            ChatResponse response = chatModel.call(
                    new Prompt(prompt,
                        OpenAiChatOptions.builder()
                            .model("gpt-4.1-nano-2025-04-14")
                            .temperature(1.0)
                            .maxCompletionTokens(200)
                            .build()
            ));

            String text = response.getResults()
               .get(0)
               .getOutput()
               .getText();
            
            return text != null ? text.trim() : "";

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            return "❌ Erro ao chamar API: " + e.getMessage();
        }
    }

}
