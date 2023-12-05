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

import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.*;
import static com.jteam.GroupProject.BotConstants.TextConstants.*;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    private boolean isDogShelter;

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
                case CALLBACK_DOG_SHELTER:
                    String dogShelterText = "Выбран " + DOG_SHELTER.toLowerCase() + ". Выбери необходимый пункт меню.";
                    executeMessage(createButtonsForUserRequest(chatId, messageId, dogShelterText));

                    isDogShelter = true;
                    break;

                case CALLBACK_CAT_SHELTER:
                    String catShelterText = "Выбран " + CAT_SHELTER.toLowerCase() + ". Выбери необходимый пункт меню.";
                    executeMessage(createButtonsForUserRequest(chatId, messageId, catShelterText));

                    isDogShelter = false;
                    break;

                case CALLBACK_INFO_SHELTER:
                    String infoShelterText = "Выбери пункт меню, который хочешь узнать";
                    executeMessage(createButtonsForInfoAboutShelter(chatId, messageId, infoShelterText));
                    break;

                case CALLBACK_TAKE_ANIMAL_FROM_SHELTER:
                    String takeAnimalText = "Консультация с потенциальным хозяином животного из приюта";
                    executeMessage(createButtonsForTakeAnimal(chatId, messageId, takeAnimalText, isDogShelter));
                    break;

                case CALLBACK_REPORT:
                    String reportText = "Ведение питомца";
                    executeMessage(createButtonsForReport(chatId, messageId, reportText));
                    break;

                case CALLBACK_INFO:
                    String infoText = "В данном разделе можно узнать информацию о приюте";
                    EditMessageText infoMessage = EditMessageText.builder()
                            .text(infoText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(infoMessage);
                    break;

                case CALLBACK_SCHEDULE:
                    String scheduleText = "В данном разделе можно узнать расписание работы приюта и адрес, схему проезда";
                    EditMessageText scheduleMessage = EditMessageText.builder()
                            .text(scheduleText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(scheduleMessage);
                    break;

                case CALLBACK_SECURITY_CONTACTS:
                    String securityContactsText = "В данном разделе можно узнать контактные данные охраны для оформления пропуска на машину";
                    EditMessageText securityConstantMessage = EditMessageText.builder()
                            .text(securityContactsText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(securityConstantMessage);
                    break;

                case CALLBACK_SAFETY_PRECAUTIONS:
                    String safetyPrecautionsText = "В данном разделе можно узнать общие рекомендации о технике безопасности на территории приюта";
                    EditMessageText safetyPrecautionsMessage = EditMessageText.builder()
                            .text(safetyPrecautionsText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(safetyPrecautionsMessage);
                    break;

                case CALLBACK_CONTACTS:
                    String contactsText = "В данном разделе можно оставить контактные данные для связи";
                    EditMessageText contactsMessage = EditMessageText.builder()
                            .text(contactsText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(contactsMessage);
                    break;

                case CALLBACK_CALL_VOLUNTEER:
                    String callVolunteerText = "В данном разделе можно связаться с волонтером";
                    EditMessageText callVolunteerMessage = EditMessageText.builder()
                            .text(callVolunteerText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(callVolunteerMessage);
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
        return SendMessage.builder()
                .text(text)
                .chatId(chatId)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(
                                InlineKeyboardButton.builder()
                        .text(DOG_SHELTER)
                        .callbackData(CALLBACK_DOG_SHELTER)
                        .build(),
                                InlineKeyboardButton.builder()
                        .text(CAT_SHELTER)
                        .callbackData(CALLBACK_CAT_SHELTER)
                        .build()))
                        .build())
                .build();
    }
    private EditMessageText createButtonsForUserRequest(Long chatId, Integer messageId, String text) {

        return EditMessageText.builder()
                .text(text)
                .messageId(messageId)
                .chatId(chatId)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(INFO_SHELTER)
                                .callbackData(CALLBACK_INFO_SHELTER)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(TAKE_ANIMAL_FROM_SHELTER)
                                .callbackData(CALLBACK_TAKE_ANIMAL_FROM_SHELTER)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(REPORT)
                                .callbackData(CALLBACK_REPORT)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(CALL_VOLUNTEER)
                                .callbackData(CALLBACK_CALL_VOLUNTEER)
                                .build()))
                        .build())
                .build();
    }

    private EditMessageText createButtonsForInfoAboutShelter(Long chatId, Integer messageId, String text) {
        return EditMessageText.builder()
                .text(text)
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(INFO)
                                .callbackData(CALLBACK_INFO)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(SCHEDULE)
                                .callbackData(CALLBACK_SCHEDULE)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(SECURITY_CONTACTS)
                                .callbackData(CALLBACK_SECURITY_CONTACTS)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(SAFETY_PRECAUTIONS)
                                .callbackData(CALLBACK_SAFETY_PRECAUTIONS)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(CONTACTS)
                                .callbackData(CALLBACK_CONTACTS)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(CALL_VOLUNTEER)
                                .callbackData(CALLBACK_CALL_VOLUNTEER)
                                .build()))
                        .build())
                .build();
    }
    private EditMessageText createButtonsForTakeAnimal(Long chatId, int messageId, String takeAnimalText, boolean isDogShelter) {
        EditMessageText message = EditMessageText.builder()
                .text(takeAnimalText)
                .messageId(messageId)
                .chatId(chatId)
                .build();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton rulesButton = InlineKeyboardButton.builder()
                .text(RULES)
                .callbackData(CALLBACK_RULES)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow1 = List.of(rulesButton);

        InlineKeyboardButton documentsButton = InlineKeyboardButton.builder()
                .text(DOCUMENT_LIST)
                .callbackData(CALLBACK_DOCUMENT_LIST)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow2 = List.of(documentsButton);

        InlineKeyboardButton transportPetButton = InlineKeyboardButton.builder()
                .text(TRANSPORT_PET)
                .callbackData(CALLBACK_TRANSPORT_PET)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow3 = List.of(transportPetButton);

        InlineKeyboardButton homeForSmallPetButton = new InlineKeyboardButton();
        if (isDogShelter) {
            homeForSmallPetButton.setText(HOME_IMPROVEMENT_FOR_PUPPY);
        } else {
            homeForSmallPetButton.setText(HOME_IMPROVEMENT_FOR_KITTY);
        }
        homeForSmallPetButton.setCallbackData(CALLBACK_HOME_IMPROVEMENT_FOR_SMALL_PET);
        List<InlineKeyboardButton> keyboardButtonRow4 = List.of(homeForSmallPetButton);

        InlineKeyboardButton homeForAdultPet = InlineKeyboardButton.builder()
                .text(HOME_IMPROVEMENT_FOR_ADULT_PET)
                .callbackData(CALLBACK_HOME_IMPROVEMENT_FOR_ADULT_PET)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow5 = List.of(homeForAdultPet);

        InlineKeyboardButton homeForPetWithLimited = InlineKeyboardButton.builder()
                .text(HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES)
                .callbackData(CALLBACK_HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow6 = List.of(homeForPetWithLimited);

        InlineKeyboardButton dogHandlerAdvice = InlineKeyboardButton.builder()
                .text(DOG_HANDLER_ADVICE)
                .callbackData(CALLBACK_DOG_HANDLER_ADVICE)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow7 = List.of(dogHandlerAdvice);

        InlineKeyboardButton dogHandlerList = InlineKeyboardButton.builder()
                .text(DOG_HANDLER_LIST)
                .callbackData(CALLBACK_DOG_HANDLER_LIST)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow8 = List.of(dogHandlerList);

        InlineKeyboardButton reasonsForRefusal = InlineKeyboardButton.builder()
                .text(REASONS_FOR_REFUSAL)
                .callbackData(CALLBACK_REASONS_FOR_REFUSAL)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow9 = List.of(reasonsForRefusal);

        InlineKeyboardButton contacts = InlineKeyboardButton.builder()
                .text(CONTACTS)
                .callbackData(CALLBACK_CONTACTS)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow10 = List.of(contacts);

        InlineKeyboardButton callVolunteer = InlineKeyboardButton.builder()
                .text(CALL_VOLUNTEER)
                .callbackData(CALLBACK_CALL_VOLUNTEER)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow11 = List.of(callVolunteer);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow1);
        rowList.add(keyboardButtonRow2);
        rowList.add(keyboardButtonRow3);
        rowList.add(keyboardButtonRow4);
        rowList.add(keyboardButtonRow5);
        rowList.add(keyboardButtonRow6);
        if (isDogShelter) {
            rowList.add(keyboardButtonRow7);
            rowList.add(keyboardButtonRow8);
        }
        rowList.add(keyboardButtonRow9);
        rowList.add(keyboardButtonRow10);
        rowList.add(keyboardButtonRow11);

        markup.setKeyboard(rowList);

        message.setReplyMarkup(markup);
        return message;
    }

    private EditMessageText createButtonsForReport(Long chatId, int messageId, String text) {
        return EditMessageText.builder()
                .text(text)
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(REPORT_FORM)
                                .callbackData(CALLBACK_REPORT_FORM)
                                .build()))
                        .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(PASS_REPORT)
                                .callbackData(CALLBACK_PASS_REPORT)
                                .build()))
                        .build())
                .build();
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
