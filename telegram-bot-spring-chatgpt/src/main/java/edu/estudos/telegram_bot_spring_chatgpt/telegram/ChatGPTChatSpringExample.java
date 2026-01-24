package edu.estudos.telegram_bot_spring_chatgpt.telegram;

import java.io.IOException;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.genai.Chat;

@Component
public class ChatGPTChatSpringExample {

    private final OpenAiChatModel openAiChatModel;

    public ChatGPTChatSpringExample(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    public String ask(String prompt) throws IOException {
        try {
            
            // Versão gpt-5 ainda não disponível na Spring AI, então usamos gpt-4.1-nano-2025-04-14

            ChatResponse response = openAiChatModel.call(new Prompt(prompt));

            String text = response.getResults()
               .get(0)
               .getOutput()
               .getText();
            
            return text != null ? text.trim() : "Sem resposta";

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            return "❌ Erro ao chamar API: " + e.getMessage();
        }
    }

}
