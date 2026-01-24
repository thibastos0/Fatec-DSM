package edu.estudos.telegram_bot_spring_chatgpt.telegram;

//import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@SuppressWarnings("deprecation")
public class ChatGPTTelegramBot extends TelegramLongPollingBot {

    private static final int MAX_REQUESTS_PER_MINUTE = 3;

    @Value("${telegram.bot.token}")
    private String botToken;
    
    private final ChatGPTClient chatGPTClient;
    private final ChatGPTSpringExample chatGPTSpringExample;
    private final ChatGPTChatSpringExample chatGPTChatSpringExample;
    private final GeminiExample geminiExample;

    private final ConcurrentHashMap<Long, UserWindow> userWindows = new ConcurrentHashMap<>();

    @Autowired
    public ChatGPTTelegramBot(ChatGPTClient chatGPTClient, ChatGPTSpringExample chatGPTSpringExample, ChatGPTChatSpringExample chatGPTChatSpringExample, GeminiExample geminiExample) {
        this.chatGPTClient = chatGPTClient;
        this.chatGPTSpringExample = chatGPTSpringExample;
        this.chatGPTChatSpringExample = chatGPTChatSpringExample;
        this.geminiExample = geminiExample;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if(!(messageText.startsWith("/ask "))) {
            sendText(chatId, "Use o comando /ask seguido da sua pergunta.");
            return;
        }

        if(isRateLimited(chatId)) {
            sendText(chatId, "Limite de requisições atingido. Tente novamente mais tarde.");
            return;
        }

        String prompt = messageText.substring(4).trim();
        if(prompt.isEmpty()) {
            sendText(chatId, "Por favor, forneça uma pergunta após o comando /ask.");
            return;
        }

        sendTypingAction(chatId);

        try {
            //String answer = chatGPTClient.ask(prompt);
            //String answer = chatGPTSpringExample.ask(prompt);
            //String answer = chatGPTChatSpringExample.ask(prompt);
            String answer = geminiExample.ask(prompt);
            sendText(chatId, answer);
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            sendText(chatId, "❌ Erro: " + e.getMessage());
        }
        
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        //nome do bot criado no BotFather
        return "conversa_chatgpt_bot";
    }

    private void sendTypingAction(Long chatId) {
        SendChatAction chatAction = new SendChatAction();
        chatAction.setChatId(String.valueOf(chatId));
        chatAction.setAction(ActionType.TYPING);
        try {
            execute(chatAction);
        } catch (TelegramApiException e) {
            System.err.println("Erro ao enviar ação de digitação: " + e.getMessage());
        }
    }

    private void sendText(Long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    private boolean isRateLimited(Long chatId) {

        Instant now = Instant.now();

        userWindows.compute(chatId, (Long id, UserWindow window) -> {
            if (window == null || Duration.between(window.startTime, now).toMinutes() >= 1) {
                return new UserWindow(now, 1);
            }
            return new UserWindow(window.startTime, window.requestCount + 1);
        });
        return userWindows.get(chatId).requestCount > MAX_REQUESTS_PER_MINUTE;
    }

    private record UserWindow(Instant startTime, int requestCount) {}
    

}
