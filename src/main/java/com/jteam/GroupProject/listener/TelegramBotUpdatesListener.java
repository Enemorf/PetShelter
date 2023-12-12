package com.jteam.GroupProject.listener;

import com.jteam.GroupProject.messaging.MessageSender;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final MessageSender messageSender;


    public TelegramBotUpdatesListener(TelegramBot telegramBot, MessageSender messageSender) {
        this.telegramBot = telegramBot;
        this.messageSender = messageSender;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            // Process your updates here
            if (update.message() != null && update.message().text() != null) {
                processMessage(update.message());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processMessage(Message message) {
        if (message == null || message.text() == null) {
            return;
        }
        String text = message.text();
        Long chatId = message.chat().id();
        if ("/start".equals(text)) {
            messageSender.sendWelcomeMessage(chatId);
        }
    }
}


