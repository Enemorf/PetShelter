package com.jteam.GroupProject.listener;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.model.Volunteer;
import com.jteam.GroupProject.replymarkup.ReplyMarkup;
import com.jteam.GroupProject.service.ReportService;
import com.jteam.GroupProject.service.TrialPeriodService;
import com.jteam.GroupProject.service.UserService;
import com.jteam.GroupProject.service.VolunteerService;
import com.jteam.GroupProject.service.impl.CatShelterServiceImpl;
import com.jteam.GroupProject.service.impl.DogShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSender {
    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final CatShelterServiceImpl catShelterService;
    private final DogShelterServiceImpl dogShelterService;
    private final UserService userService;
    private final TrialPeriodService trialPeriodService;
    private final ReportService reportService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public void sendMessage(Long chatId, String message) {
        SendResponse sendResponse = telegramBot.execute(new SendMessage(chatId, message));
        if (!sendResponse.isOk()) {
            logger.error(sendResponse.description());
        }
    }
    public void sendMessageToVolunteers(Message message) {
        Long chatId = message.chat().id();
        Integer integer = message.messageId();
        for (Volunteer volunteer : volunteerService.getAll()) {
            telegramBot.execute(new ForwardMessage(volunteer.getTelegramId(), chatId, integer));
        }
    }

     void sendMessageToVolunteers(Long chatId) {
        for (Volunteer volunteer : volunteerService.getAll()) {
            telegramBot.execute(new SendMessage(volunteer.getTelegramId(), "Владелец животного с id " + chatId + " не отправлял отчёты более двух дней!"));
        }
    }

    private void sendCommonMessage(Long chatId, String photoPath, String caption) {
        try {
            byte[] photo = Files.readAllBytes(Paths.get(Objects.requireNonNull(UpdatesListener.class.getResource(photoPath)).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(caption);
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
    }
    public void sendMenuStage(String shelterType, Long chatId) {
        logger.info("Вызвано меню выбора приюта - ID:{}", chatId);
        ReplyMarkup replyMarkup = new ReplyMarkup(telegramBot, catShelterService, dogShelterService);
        User user = userService.getById(chatId);
        user.setShelterType(shelterType);
        userService.update(user);
        replyMarkup.sendMenuStage(chatId);
    }
    private void sendReportExample(Long chatId) {
        sendCommonMessage(chatId, "/static/img/Cat.jpg", """
                Рацион: ваш текст;
                Самочувствие: ваш текст;
                Поведение: ваш текст;
                """);
    }
    public void sendReportPhotoToVolunteer(Long reportId, Long volunteerId) {
        GetFile request = new GetFile(reportService.getById(reportId).getPhotoId());

        try {
            GetFileResponse getFileResponse = telegramBot.execute(request);
            if (getFileResponse.isOk()) {
                byte[] image = telegramBot.getFileContent(getFileResponse.file());
                TrialPeriod trialPeriod = trialPeriodService.getById(reportService.getById(reportId).getTrialPeriodId());

                if (trialPeriod != null) {
                    SendPhoto sendPhoto = new SendPhoto(volunteerId, image);
                    sendPhoto.caption("Id владельца: " + trialPeriod.getOwnerId() + "\n" +
                            "Id испытательного срока: " + trialPeriod.getId() + "\n" +
                            "Id отчёта:" + reportId);

                    try {
                        telegramBot.execute(sendPhoto);
                    } catch (Exception e) {
                        logger.error("Error sending photo to volunteer", e);
                    }
                } else {
                    logger.error("TrialPeriod is null for reportId: {}", reportId);
                }
            } else {
                logger.error("Error getting file response: {}", getFileResponse.description());
            }
        } catch (Exception e) {
            logger.error("Error getting file for reportId: {}", reportId, e);
        }
    }
}
