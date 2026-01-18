# Telegram Bot com Spring Boot e ChatGPT

Um bot do Telegram integrado com Spring Boot 4.0.1 e a API do ChatGPT, permitindo interações inteligentes com usuários do Telegram através de processamento de linguagem natural.

## 📚 Créditos

Este projeto é baseado no tutorial do canal do YouTube **[@FinashkinDmitry](https://www.youtube.com/c/FinashkinDmitry)**.

Agradecimentos especiais ao criador pela excelente documentação e recursos educacionais.

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21** ou superior
- **Maven 3.8+**
- Uma **conta no Telegram** e acesso ao [@BotFather](https://t.me/botfather) para criar um bot
- Chave de API do **ChatGPT** (OpenAI API Key)

## 🚀 Instalação e Configuração

### 1. Clone ou baixe o projeto

```bash
git clone <seu-repositorio>
cd telegram-bot-spring-chatgpt
```

### 2. Crie um bot no Telegram

1. Acesse [@BotFather](https://t.me/botfather) no Telegram
2. Envie o comando `/newbot`
3. Siga as instruções e obtenha seu **bot token**

### 3. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto ou configure as variáveis de ambiente:

```
TELEGRAM_BOT_TOKEN=seu_token_aqui
CHATGPT_API_KEY=sua_api_key_aqui
CHATGPT_MODEL=gpt-3.5-turbo
```

Alternatively, configure no arquivo `application.properties`:

```properties
telegram.bot.token=seu_token_aqui
chatgpt.api.key=sua_api_key_aqui
chatgpt.model=gpt-3.5-turbo
```

### 4. Instale as dependências e compile

```bash
mvn clean install
```

## 🏃 Como Executar

### Desenvolvimento

```bash
mvn spring-boot:run
```

### Build para Produção

```bash
mvn clean package
java -jar target/telegram-bot-spring-chatgpt-0.0.1-SNAPSHOT.jar
```

## 📖 Uso

Após iniciar a aplicação:

1. Abra o Telegram e procure pelo seu bot (usando o nome que definiu no BotFather)
2. Inicie uma conversa com `/start`
3. Envie mensagens de texto normalmente - o bot responderá usando ChatGPT
4. Use `/help` para ver os comandos disponíveis

## 🏗️ Estrutura do Projeto

```
telegram-bot-spring-chatgpt/
├── src/
│   ├── main/
│   │   ├── java/edu/estudos/
│   │   │   └── telegram_bot_spring_chatgpt/
│   │   │       └── ...
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 📦 Dependências Principais

- **Spring Boot 4.0.1**: Framework web e injeção de dependência
- **TelegramBots 6.9.7.1**: SDK oficial do Telegram para Java
- **OpenAI API**: Integração com ChatGPT

## 🔧 Configuração Avançada

Para mais detalhes sobre Spring Boot, consulte:
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/4.0.1/reference/html/)
- [TelegramBots Documentation](https://github.com/rubenlagus/TelegramBots)

## 📝 Notas Importantes

- **Variáveis de Ambiente**: Nunca commite sua API key ou token do bot no repositório. Use variáveis de ambiente.
- **Rate Limiting**: O Telegram tem limites de taxa. Implemente cache e throttling se necessário.
- **Custos da API**: Cada requisição ao ChatGPT gera custos. Monitore seu uso na plataforma OpenAI.

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se livre para:
- Reportar bugs
- Sugerir melhorias
- Fazer pull requests

## 📄 Licença

Este projeto é fornecido como material educacional.

## ❓ Suporte

Para dúvidas ou problemas:

1. Verifique o arquivo [HELP.md](./HELP.md)
2. Consulte a documentação do Spring Boot
3. Visite o [repositório do TelegramBots](https://github.com/rubenlagus/TelegramBots)

---

**Desenvolvido como projeto educacional baseado no tutorial de @FinashkinDmitry**
