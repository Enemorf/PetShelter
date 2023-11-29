package com.jteam.GroupProject.listener;


import com.jteam.GroupProject.config.TelegramBotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {


    private final TelegramBotConfig botConfig;

    public TelegramBot(TelegramBotConfig botConfig,
                       @Value("${telegram.bot.token}") String botToken) {
        super(botToken);
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    // Метод для обработки полученных сообщений
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Processing update: {}", update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                chooseShelterButtons(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callbackData) {
                case "DOG_SHELTER":
                    sendMessage(chatId, "Выбран приют для собак");
                    break;

                case "CAT_SHELTER":
                    sendMessage(chatId, "Выбран приют для кошек");
                    break;
            }
        }
    }

    // Метод для обработки команды /start
    private void startCommandReceived(Long chatId, String name) {
        String helloText = "Привет, " + name + ". Рад тебя видеть!. Я помогу тебе взаимодействовать с приютами.";
        sendMessage(chatId, helloText);
    }

    // Создание кнопок выбора приюта
    private void chooseShelterButtons(Long chatId) {
        String text = "Выбери приют";
        SendMessage message = new SendMessage(String.valueOf(chatId), text);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var dogButton = new InlineKeyboardButton();
        dogButton.setText("Приют для собак");
        dogButton.setCallbackData("DOG_SHELTER");

        var catButton = new InlineKeyboardButton();
        catButton.setText("Приют для кошек");
        catButton.setCallbackData("CAT_SHELTER");


        rowInLine.add(dogButton);
        rowInLine.add(catButton);

        rowsInLine.add(rowInLine);

        keyboardMarkup.setKeyboard(rowsInLine);
        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);

    }


    // Методы для отправки сообщений
    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        executeMessage(sendMessage);
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
