package com.jteam.GroupProject.listener;


import com.jteam.GroupProject.botButtons.CreateButtonsStageOne;
import com.jteam.GroupProject.botButtons.CreateButtonsStageThree;
import com.jteam.GroupProject.botButtons.CreateButtonsStageTwo;
import com.jteam.GroupProject.botButtons.CreateButtonsStageZero;
import com.jteam.GroupProject.config.TelegramBotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


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
                executeMessage(CreateButtonsStageZero.createShelterButtons(chatId));
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            switch (callbackData) {
                case CALLBACK_DOG_SHELTER:
                    String dogShelterText = "Выбран " + DOG_SHELTER.toLowerCase() + ". Выбери необходимый пункт меню.";
                    executeMessage(CreateButtonsStageZero.createButtonsForUserRequest(chatId, messageId, dogShelterText));

                    isDogShelter = true;
                    break;

                case CALLBACK_CAT_SHELTER:
                    String catShelterText = "Выбран " + CAT_SHELTER.toLowerCase() + ". Выбери необходимый пункт меню.";
                    executeMessage(CreateButtonsStageZero.createButtonsForUserRequest(chatId, messageId, catShelterText));

                    isDogShelter = false;
                    break;

                case CALLBACK_INFO_SHELTER:
                    String infoShelterText = "Выбери пункт меню, который хочешь узнать";
                    executeMessage(CreateButtonsStageOne.createButtonsForInfoAboutShelter(chatId, messageId, infoShelterText));
                    break;

                case CALLBACK_TAKE_ANIMAL_FROM_SHELTER:
                    String takeAnimalText = "Консультация с потенциальным хозяином животного из приюта";
                    executeMessage(CreateButtonsStageTwo.createButtonsForTakeAnimal(chatId, messageId, takeAnimalText, isDogShelter));
                    break;

                case CALLBACK_REPORT:
                    String reportText = "Ведение питомца";
                    executeMessage(CreateButtonsStageThree.createButtonsForReport(chatId, messageId, reportText));
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

                case CALLBACK_RULES:
                    String rulerText = "В данном разделе можно узнать правила знакомства с животным до того, как забрать его с приюта";
                    EditMessageText rulesMessage = EditMessageText.builder()
                            .text(rulerText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(rulesMessage);
                    break;

                case CALLBACK_DOCUMENT_LIST:
                    String documentsText = "В данном разделе можно узнать список документов, необходимых для того, чтобы взять животное из приюта";
                    EditMessageText documentsMessage = EditMessageText.builder()
                            .text(documentsText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(documentsMessage);
                    break;

                case CALLBACK_TRANSPORT_PET:
                    String transportText = "В данном разделе можно узнать список рекомендаций по транспортировке животного";
                    EditMessageText transportMessage = EditMessageText.builder()
                            .text(transportText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(transportMessage);
                    break;

                case CALLBACK_HOME_IMPROVEMENT_FOR_SMALL_PET:
                    String homeImprovementForSmallPetText = "В данном разделе можно узнать список рекомендаций по обустройству дома для щенка/котенка";
                    EditMessageText homeImprovementForSmallPetMessage = EditMessageText.builder()
                            .text(homeImprovementForSmallPetText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(homeImprovementForSmallPetMessage);
                    break;

                case CALLBACK_HOME_IMPROVEMENT_FOR_ADULT_PET:
                    String homeImprovementForAdultPetText = "В данном разделе можно узнать список рекомендаций по обустройству дома для взрослого животного";
                    EditMessageText homeImprovementForAdultPetMessage = EditMessageText.builder()
                            .text(homeImprovementForAdultPetText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(homeImprovementForAdultPetMessage);
                    break;

                case CALLBACK_HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES:
                    String homeImprovementForPetWithLimitedOpportunitiesText = "В данном разделе можно узнать список рекомендаций по обустройству дома для животного с ограниченными возможностями";
                    EditMessageText homeImprovementForPetWithLimitedOpportunitiesMessage = EditMessageText.builder()
                            .text(homeImprovementForPetWithLimitedOpportunitiesText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(homeImprovementForPetWithLimitedOpportunitiesMessage);
                    break;

                case CALLBACK_DOG_HANDLER_ADVICE:
                    String dogHandlerAdviceText = "В данном разделе можно узнать советы кинолога по первичному общению с собакой";
                    EditMessageText dogHandlerAdviceMessage = EditMessageText.builder()
                            .text(dogHandlerAdviceText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(dogHandlerAdviceMessage);
                    break;

                case CALLBACK_DOG_HANDLER_LIST:
                    String dogHandlerListText = "В данном разделе можно узнать рекомендации по проверенным кинологам для дальнейшего обращения к ним";
                    EditMessageText dogHandlerListMessage = EditMessageText.builder()
                            .text(dogHandlerListText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(dogHandlerListMessage);
                    break;

                case CALLBACK_REASONS_FOR_REFUSAL:
                    String reasonsForRefusalText = "В данном разделе можно узнать список причин, почему могут отказать и не дать забрать собаку из приюта";
                    EditMessageText reasonsForRefusalMessage = EditMessageText.builder()
                            .text(reasonsForRefusalText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(reasonsForRefusalMessage);
                    break;

                case CALLBACK_REPORT_FORM:
                    String reportFormText = "В данном разделе можно узнать форму ежедневного отчета";
                    EditMessageText reportFormMessage = EditMessageText.builder()
                            .text(reportFormText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(reportFormMessage);
                    break;

                case CALLBACK_PASS_REPORT:
                    String passReportText = "В данном разделе можно отправить ежедневный отчет";
                    EditMessageText passReportMessage = EditMessageText.builder()
                            .text(passReportText)
                            .messageId(messageId)
                            .chatId(chatId)
                            .build();
                    executeMessage(passReportMessage);
                    break;

            }
        }
    }


    // Метод для обработки команды /start

    private void startCommandReceived(Long chatId, String name) {
        String helloText = "Привет, " + name + ". Рад тебя видеть!. Я помогу тебе взаимодействовать с приютами.";
        executeMessage(new SendMessage(String.valueOf(chatId), helloText));
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
