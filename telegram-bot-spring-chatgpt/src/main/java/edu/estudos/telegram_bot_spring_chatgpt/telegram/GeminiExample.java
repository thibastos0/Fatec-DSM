package edu.estudos.telegram_bot_spring_chatgpt.telegram;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;


@Component
public class GeminiExample {

    private final ChatModel chatModel;

    @Autowired
    public GeminiExample(@Qualifier("geminiModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String ask(String prompt) {
        try {

            ChatResponse response = chatModel.call(new Prompt(prompt));

            if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
                return "❌ Resposta vazia do Gemini";
            }
            
            String result = response.getResult()
                    .getOutput()
                    .getText();

            return result != null ? result.trim() : "Sem resposta";

        } catch (Exception e) {
            System.err.println("Erro ao chamar Gemini: " + e.getMessage());
            e.printStackTrace();
            return "❌ Erro ao chamar Gemini: " + e.getMessage();
        }
    }
}