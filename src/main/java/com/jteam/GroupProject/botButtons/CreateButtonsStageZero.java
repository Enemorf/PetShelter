package com.jteam.GroupProject.botButtons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.*;
import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.CALLBACK_CALL_VOLUNTEER;
import static com.jteam.GroupProject.BotConstants.TextConstants.*;
import static com.jteam.GroupProject.BotConstants.TextConstants.CALL_VOLUNTEER;

public class CreateButtonsStageZero {
    public static SendMessage createShelterButtons(Long chatId) {
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

    public static EditMessageText createButtonsForUserRequest(Long chatId, Integer messageId, String text) {

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
}
