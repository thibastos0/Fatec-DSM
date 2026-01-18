package edu.estudos.telegram_bot_spring_chatgpt;

//import java.io.ObjectInputFilter.Config;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import edu.estudos.telegram_bot_spring_chatgpt.telegram.ChatGPTTelegramBot;

@SpringBootApplication
@AutoConfigurationPackage
public class TelegramBotSpringChatgptApplication {

	public static void main(String[] args) {
		//SpringApplication.run(TelegramBotSpringChatgptApplication.class, args);
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(TelegramBotSpringChatgptApplication.class)
				.run(args);
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			ChatGPTTelegramBot chatGPTTelegramBot = context.getBean(ChatGPTTelegramBot.class);
			telegramBotsApi.registerBot(chatGPTTelegramBot);
			System.err.println("Telegram Bot started successfully.");

		} catch (TelegramApiException e) {
			System.err.println("TelegramBotsApi initialization failed: " + e.getMessage());
		}
	}

}
