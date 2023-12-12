package com.jteam.GroupProject.botButtons;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.CALLBACK_PASS_REPORT;
import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.CALLBACK_REPORT_FORM;
import static com.jteam.GroupProject.BotConstants.TextConstants.PASS_REPORT;
import static com.jteam.GroupProject.BotConstants.TextConstants.REPORT_FORM;

public class CreateButtonsStageThree {
    public static EditMessageText createButtonsForReport(Long chatId, int messageId, String text) {
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
}
