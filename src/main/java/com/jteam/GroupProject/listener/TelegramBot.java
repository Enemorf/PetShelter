package com.jteam.GroupProject.listener;


import com.jteam.GroupProject.config.TelegramBotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.jteam.GroupProject.BotConstants.Constants.*;

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
                executeMessage(createShelterButtons(chatId));
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            switch (callbackData) {
                case DOG_SHELTER:
                    String dogShelterText = "Выбран " + DOG_SHELTER.toLowerCase();
                    executeMessage(createButtonsForUserRequest(chatId, messageId, dogShelterText));
                    break;

                case CAT_SHELTER:
                    String catShelterText = "Выбран " + CAT_SHELTER.toLowerCase();
                    executeMessage(createButtonsForUserRequest(chatId, messageId, catShelterText));
                    break;
            }
        }
    }

    // Метод для обработки команды /start
    private void startCommandReceived(Long chatId, String name) {
        String helloText = "Привет, " + name + ". Рад тебя видеть!. Я помогу тебе взаимодействовать с приютами.";
        executeMessage(new SendMessage(String.valueOf(chatId), helloText));
    }

    // Создание кнопок выбора приюта
    private SendMessage createShelterButtons(Long chatId) {
        String text = "Выбери приют";
        SendMessage message = new SendMessage(String.valueOf(chatId), text);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        InlineKeyboardButton dogButton = new InlineKeyboardButton();
        dogButton.setText("Приют для собак");
        dogButton.setCallbackData(DOG_SHELTER);

        InlineKeyboardButton catButton = new InlineKeyboardButton();
        catButton.setText("Приют для кошек");
        catButton.setCallbackData(CAT_SHELTER);


        rowInLine.add(dogButton);
        rowInLine.add(catButton);

        rowsInLine.add(rowInLine);

        keyboardMarkup.setKeyboard(rowsInLine);
        message.setReplyMarkup(keyboardMarkup);

        return message;

    }

    private EditMessageText createButtonsForUserRequest(Long chatId, Integer messageId, String text) {

        EditMessageText message = editMessageText(chatId, messageId, text);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> infoButtonRow = new ArrayList<>();
        List<InlineKeyboardButton> takeAnimalButtonRow = new ArrayList<>();
        List<InlineKeyboardButton> reportButtonRow = new ArrayList<>();
        List<InlineKeyboardButton> callVolunteerButtonRow = new ArrayList<>();

        InlineKeyboardButton infoButton = new InlineKeyboardButton();
        infoButton.setText(INFO_SHELTER);
        infoButton.setCallbackData(INFO_SHELTER);

        InlineKeyboardButton takeAnimalButton = new InlineKeyboardButton();
        takeAnimalButton.setText(TAKE_ANIMAL_FROM_SHELTER);
        takeAnimalButton.setCallbackData(TAKE_ANIMAL_FROM_SHELTER);

        InlineKeyboardButton reportButton = new InlineKeyboardButton();
        reportButton.setText(REPORT);
        reportButton.setCallbackData(REPORT);

        InlineKeyboardButton volunteerButton = new InlineKeyboardButton();
        volunteerButton.setText(CALL_VOLUNTEER);
        volunteerButton.setCallbackData(CALL_VOLUNTEER);

        infoButtonRow.add(infoButton);
        takeAnimalButtonRow.add(takeAnimalButton);
        reportButtonRow.add(reportButton);
        callVolunteerButtonRow.add(volunteerButton);

        rowsInLine.add(infoButtonRow);
        rowsInLine.add(takeAnimalButtonRow);
        rowsInLine.add(reportButtonRow);
        rowsInLine.add(callVolunteerButtonRow);

        keyboardMarkup.setKeyboard(rowsInLine);
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }


    // Создание сообщения, которое заменит сообщение с соответствующим messageId
    private EditMessageText editMessageText(Long chatId, int messageId, String text) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId(messageId);

        return message;
    }

    // Отправка сообщения ботом
    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void executeMessage(EditMessageText message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
