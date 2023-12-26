package com.jteam.GroupProject.listener;

import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.service.ReportService;
import com.jteam.GroupProject.service.UserService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
@RequiredArgsConstructor
public class TelegramMessageProcessor {
    private final UserService userService;
    private final ReportService reportService;
    private final MessageSender messageSender;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public void getContact(Long chatId, String text) {
        Pattern pattern = Pattern.compile("^(\\d{11})$");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            User byId = userService.getById(chatId);
            byId.setPhone(matcher.group(1));
            userService.update(byId);
            messageSender.sendMessage(chatId, "Телефон принят");
        } else {
            messageSender.sendMessage(chatId, "Неверно ввели номер телефона. Введите номер в формате: 89997776655)");
        }
        logger.info("Получен телефон - ID:{} тел:{} ", chatId, text);
    }

    public void getReport(Message message) {
        PhotoSize photoSize = message.photo()[message.photo().length - 1];
        String caption = message.caption();
        Long chatId = message.chat().id();
        try {
            reportService.createFromTelegram(photoSize.fileId(), caption, chatId);
            messageSender.sendMessage(chatId, "Ваш отчёт принят.");
        } catch (Exception e) {
            messageSender.sendMessage(chatId, e.getMessage());
        }
    }

    public String getStringFromList(List<?> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(o -> sb.append(o)
                .append("\n")
                .append("============").append("\n"));
        return sb.toString();
    }
}
