package edu.estudos.telegram_bot_spring_chatgpt;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

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

    private final ChatGPTTelegramBot chatGPTTelegramBot;

    TelegramBotSpringChatgptApplication(ChatGPTTelegramBot chatGPTTelegramBot) {
        this.chatGPTTelegramBot = chatGPTTelegramBot;
    }

	public static void main(String[] args) {

		
		// Obtém o diretório atual da execução
    	String projectDir = System.getProperty("user.dir");
    	System.out.println("Working dir: " + projectDir);

        Dotenv dotenv = Dotenv.configure()
                //.directory(projectDir + "/telegram-bot-spring-chatgpt")   // usa o diretório da execução
				.directory(projectDir) // aparentemetente a maneira como o VS Code abre o workspace influencia o diretório de execução
				.filename(".env")
                .ignoreIfMissing()       // evita erro quando o .env não existir (Render)
                .load();

        Map<String, Object> props = new HashMap<>();

        dotenv.entries().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            props.put(key, value);
            //System.out.println("✅ " + key + " carregada via .env");
        });

		
		//SpringApplication.run(TelegramBotSpringChatgptApplication.class, args);
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(TelegramBotSpringChatgptApplication.class)
				.properties(props)
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
