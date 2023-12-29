package com.jteam.GroupProject.listener;

import com.jteam.GroupProject.BotConstants.Information;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.service.TrialPeriodService;
import com.jteam.GroupProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final UserService userService;
    private final TrialPeriodService trialPeriodService;
    private final MessageSender messageSender;
    private final Information information;


    private void sendMessage(Long chatId, String message) {
        messageSender.sendMessage(chatId, message);
    }
    @Scheduled(cron = "@daily")

    // Логика шедулера для отправки предупреждений
    private void sendWarning() {
        for (User user : userService.getAll()) {
            for (TrialPeriod trialPeriod : trialPeriodService.getAllByOwnerId(user.getTelegramId())) {
                if ((trialPeriod.getReports().size() < 45 && !trialPeriod.getLastReportDate().isEqual(trialPeriod.getEndDate())) &&
                        trialPeriod.getLastReportDate().isBefore(LocalDate.now().minusDays(2))) {
                    sendMessage(user.getTelegramId(), "Вы не отправляли отчёты уже более двух дней. " +
                            "Пожалуйста, отправьте отчёт или выйдите на связь с волонтёрами.");
                    messageSender.sendMessageToVolunteers(user.getTelegramId());
                }
            }

        }
    }

    @Scheduled(cron = "@daily")

    // Логика шедулера для отправки статуса испытательного срока
    private void sendTrialPeriodStatus() {
        for (User user : userService.getAll()) {
            for (TrialPeriod trialPeriod : trialPeriodService.getAllByOwnerId(user.getTelegramId())) {
                if (trialPeriod.getResult().equals(TrialPeriod.Result.NOT_SUCCESSFUL)) {
                    sendMessage(user.getTelegramId(), information.getTRIAL_NOT_SUCCESSFUL());
                } else if (trialPeriod.getResult().equals(TrialPeriod.Result.EXTENDED)) {
                    sendMessage(user.getTelegramId(), information.getTRIAL_EXTENDED());
                } else if (trialPeriod.getResult().equals(TrialPeriod.Result.SUCCESSFUL)) {
                    sendMessage(user.getTelegramId(), information.getSUCCESSFUL());
                }
            }
        }
    }
}

