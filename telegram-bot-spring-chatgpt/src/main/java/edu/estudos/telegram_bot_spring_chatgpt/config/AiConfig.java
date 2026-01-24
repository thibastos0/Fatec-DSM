package edu.estudos.telegram_bot_spring_chatgpt.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
//import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class AiConfig {

    @Bean
    @Qualifier("geminiModel")
    public ChatModel geminiChatModel(GoogleGenAiChatModel model) {
        return model;
    }
/*
    @Bean
    @Qualifier("openAiModel")
    public ChatModel openAiChatModel(OpenAiChatModel model) {
        return model;
    }*/
}
