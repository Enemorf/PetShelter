package com.jteam.GroupProject.messaging;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSender {
    private final TelegramBot telegramBot;

    public MessageSender(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendWelcomeMessage(Long chatId) {
        String welcomeText = "Привет! Я бот, который поможет вам узнать о приютах для кошек и собак.\n" +
                "И что нужно знать и уметь, чтобы забрать животное из приюта.";

        SendMessage welcomeMessage = new SendMessage(chatId, welcomeText);


        try {
            SendResponse sendResponse = telegramBot.execute(welcomeMessage);

            if (!sendResponse.isOk()) {
                log.error("Failed to send welcome message. Error: {}", sendResponse.description());
            }
        } catch (Exception e) {
            log.error("Exception while sending welcome message", e);
        }

    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            telegramBot.execute(message);
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибок отправки сообщения
        }
    }

    public void sendAboutShelterMessage(Long chatId) {
        // Реализация информации о приюте
    }

    public void sendScheduleAndAddressMessage(Long chatId) {
        // Реализация расписания работы и адреса приюта
    }

    public void sendSecurityRecommendationsMessage(Long chatId) {
        // Реализация рекомендаций по технике безопасности
    }

    public void sendContactSecurityMessage(Long chatId) {
        // Реализация контактных данных охраны
    }

    public void sendContactInformationRequest(Long chatId) {
        // Реализация запроса контактной информации для связи
    }

    public void sendUnknownCommandMessage(Long chatId) {
        // Реализация сообщения об неизвестной команде
    }
}
