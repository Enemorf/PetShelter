package com.jteam.GroupProject.botButtons;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.*;
import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.CALLBACK_CALL_VOLUNTEER;
import static com.jteam.GroupProject.BotConstants.TextConstants.*;
import static com.jteam.GroupProject.BotConstants.TextConstants.CALL_VOLUNTEER;

public class CreateButtonsStageOne {
    public static EditMessageText createButtonsForInfoAboutShelter(Long chatId, Integer messageId, String text) {
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
}
