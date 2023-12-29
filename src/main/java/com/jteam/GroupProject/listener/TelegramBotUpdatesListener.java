package com.jteam.GroupProject.listener;

import com.jteam.GroupProject.BotConstants.Information;
import com.jteam.GroupProject.BotConstants.TextConstants;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.model.animal.Cat;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.replymarkup.ReplyMarkup;
import com.jteam.GroupProject.repository.UserRepository;
import com.jteam.GroupProject.service.UserService;
import com.jteam.GroupProject.service.impl.CatShelterServiceImpl;
import com.jteam.GroupProject.service.impl.DogShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final UserService userService;
    private final UserRepository userRepo;
    private final CatShelterServiceImpl catShelterService;
    private final DogShelterServiceImpl dogShelterService;
    private final MessageSender messageSender;
    private final TelegramMessageProcessor telegramMessageProcessor;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TextConstants textConstants;
    private final Information information;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        ReplyMarkup replyMarkup = new ReplyMarkup(telegramBot, catShelterService, dogShelterService,textConstants,information);
        try {
            updates.stream()
                    .filter(update -> update.message() != null)
                    .forEach(update -> {
                        try {
                            logger.info("Обработка обновления: {}", update);
                            Message message = update.message();
                            Long chatId = message.chat().id();
                            Chat chat = message.chat();
                            String text = message.text();
                            if (!userRepo.existsById(chatId)) {
                                userService.create(new User(chatId, chat.firstName(), chat.lastName(), ""));
                            }
                            User user = userService.getById(chatId);

                            if (message.photo() != null) {
                                telegramMessageProcessor.getReport(message);
                                return;
                            }

                            Pattern pattern = Pattern.compile("(\\d+)");
                            Matcher matcher = pattern.matcher(text);
                            if (matcher.find()) {
                                telegramMessageProcessor.getContact(chatId, text);
                                return;
                            }

                            String shelterType = user.getShelterType();
                            if (shelterType != null) {
                                if (shelterType.equals("DOG")) {
                                    dogShelterService.getShelter().forEach(dogShelter -> {
                                        if (dogShelter.getName().equals(text)) {
                                            user.setShelterName(dogShelter.getName());
                                            replyMarkup.sendMenuDog(chatId);
                                            userService.update(user);
                                        }
                                    });
                                } else if (shelterType.equals("CAT")) {
                                    catShelterService.getShelter().forEach(catShelter -> {
                                        if (catShelter.getName().equals(text)) {
                                            user.setShelterName(catShelter.getName());
                                            replyMarkup.sendMenuCat(chatId);
                                            userService.update(user);
                                        }
                                    });
                                }
                            }

                            String start  = "/start";
                            String toShelter = "К выбору приютов";

                            if(start.equals(text)||toShelter.equals(text)) {
                                logger.info("Запустили бота/выбрали приют - ID:{}", chatId);
                                replyMarkup.sendStartMenu(chatId);
                            }
                            if(textConstants.getCAT_SHELTER().equals(text)){
                                messageSender.sendMenuStage("CAT", chatId);
                            }
                            if(textConstants.getDOG_SHELTER().equals(text)){
                                messageSender.sendMenuStage("DOG", chatId);
                            }
                            if(textConstants.getINFO_SHELTER().equals(text)){
                                logger.info("Узнать информацию о приюте - ID:{}", chatId);
                                shelterType = userService.getShelterById(chatId);
                                if ("CAT".equals(shelterType)) {
                                    replyMarkup.sendListMenuCat(chatId);
                                } else if ("DOG".equals(shelterType)) {
                                    replyMarkup.sendListMenuDog(chatId);
                                }
                            }
                            if(textConstants.getMAIN_MENU().equals(text)){
                                logger.info("Главное меню - ID:{}", chatId);
                                user.setShelterType(null);
                                user.setShelterName(null);
                                userService.update(user);
                                replyMarkup.sendStartMenu(chatId);
                            }
                            if(textConstants.getSCHEDULE().equals(text)){
                                logger.info("Расписание работы - ID:{}", chatId);
                                if (user.getShelterType().equals("CAT")) {
                                    messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getTimetable());
                                } else if (user.getShelterType().equals("DOG")) {
                                    messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getTimetable());
                                }
                            }
                            if(textConstants.getLIST_OF_CATS().equals(text)){
                                logger.info("Список кошек - ID:{}", chatId);
                                List<Cat> catList = catShelterService.getShelterByName(user.getShelterName())
                                        .getList().stream()
                                        .filter(cat -> cat != null && cat.getOwnerId() == null)
                                        .toList();
                                if (catList.isEmpty()) {
                                    messageSender.sendMessage(chatId, "Кошки кончились");
                                    return;
                                }
                                messageSender.sendMessage(chatId, telegramMessageProcessor.getStringFromList(catList));
                            }
                            if(textConstants.getLIST_OF_DOGS().equals(text)){
                                logger.info("Список собак - ID:{}", chatId);
                                List<Dog> dogList = dogShelterService.getShelterByName(user.getShelterName())
                                        .getList().stream()
                                        .filter(dog -> dog != null && dog.getOwnerId() == null)
                                        .toList();
                                if (dogList.isEmpty()) {
                                    messageSender.sendMessage(chatId, "Собаки кончились");
                                    return;
                                }
                                messageSender.sendMessage(chatId, telegramMessageProcessor.getStringFromList(dogList));
                            }
                            if(textConstants.getINFO().equals(text)){
                                logger.info("О приюте - ID:{}", chatId);
                                if (user.getShelterType().equals("CAT")) {
                                    messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getAboutMe());
                                } else if (user.getShelterType().equals("DOG")) {
                                    messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getAboutMe());
                                }
                            }
                            if(textConstants.getTB_GUIDELINES().equals(text)){
                                logger.info("Рекомендации о ТБ - ID:{}", chatId);
                                if (user.getShelterType().equals("CAT")) {
                                    messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getSafetyAdvice());
                                } else if (user.getShelterType().equals("DOG")) {
                                    messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getSafetyAdvice());
                                }
                            }
                            if(textConstants.getCONTACT_DETAILS().equals(text)){
                                logger.info("Как отправить свои данные для связи - ID:{}", chatId);
                                messageSender.sendMessage(chatId, "Введите номер телефона в формате: 89997776655");
                            }
                            if(textConstants.getSECURITY_CONTACTS().equals(text)){
                                logger.info("Контактные данные охраны - ID:{}", chatId);
                                if (user.getShelterType().equals("CAT")) {
                                    messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getSecurity());
                                } else if (user.getShelterType().equals("DOG")) {
                                    messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getSecurity());
                                }
                            }
                            if(textConstants.getFAQ().equals(text)){
                                logger.info("Часто задаваемые вопросы - ID:{}", chatId);
                                if (user.getShelterType().equals("CAT")) {
                                    replyMarkup.menuCat(chatId);
                                } else if (user.getShelterType().equals("DOG")) {
                                    replyMarkup.menuDog(chatId);
                                }
                            }
                            if(textConstants.getBACK_TO_ALL_ABOUT_CATS().equals(text)){
                                logger.info("Все о кошках - ID:{}", chatId);
                                replyMarkup.menuCat(chatId);
                            }
                            if(textConstants.getBACK_TO_ALL_ABOUT_DOGS().equals(text)){
                                logger.info("Все о собаках - ID:{}", chatId);
                                replyMarkup.menuDog(chatId);
                            }
                            if(textConstants.getRULES_A_CATS().equals(text) || textConstants.getRULES_A_DOGS().equals(text)){
                                logger.info("Правила знакомства - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getANIMAL_DATING_RULES());
                            }
                            if(textConstants.getCAT_CARRIAGE().equals(text) || textConstants.getDOG_CARRIAGE().equals(text)){
                                logger.info("Перевозка - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getTRANSPORTATION_OF_THE_ANIMAL());
                            }
                            if(textConstants.getDOCUMENT_LIST().equals(text)){
                                logger.info("Необходимые документы - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getLIST_OF_DOCUMENTS());
                            }
                            if(textConstants.getREASONS_FOR_REFUSAL().equals(text)){
                                logger.info("Список причин для отказа выдачи питомца - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getLIST_OF_REASON_FOR_DENY());
                            }
                            if(textConstants.getRECOMMENDATIONS_FOR_DOGS().equals(text)){
                                logger.info("Рекомендации для собак - ID:{}", chatId);
                                replyMarkup.rulesForDogs(chatId);
                            }
                            if(textConstants.getRECOMMENDATIONS_FOR_CATS().equals(text)){
                                logger.info("Рекомендации для кошек - ID:{}", chatId);
                                replyMarkup.rulesForCats(chatId);
                            }
                            if(textConstants.getHOME_IMPROVEMENT_FOR_KITTY().equals(text) || textConstants.getHOME_IMPROVEMENT_FOR_PUPPY().equals(text)){
                                logger.info("Обустройство щенка/котенка - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getRECOMMENDATIONS_HOME_IMPROVEMENT_KITTEN_PUPPY());
                            }
                            if(textConstants.getHOME_IMPROVEMENT_FOR_ADULT_PET().equals(text)){
                                logger.info("Обустройство взрослого животного - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getRECOMMENDATIONS_HOME_IMPROVEMENT_ADULT_ANIMAL());
                            }
                            if(textConstants.getHOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES().equals(text)){
                                logger.info("Обустройство животного с ограниченными возможностями - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getRECOMMENDATIONS_HOME_IMPROVEMENT_DISABLED_ANIMAL());
                            }
                            if(textConstants.getDOG_HANDLER_ADVICE().equals(text)){
                                logger.info("Советы кинолога - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getDOG_HANDLERS_ADVICE());
                            }
                            if(textConstants.getDOG_HANDLER_LIST().equals(text)){
                                logger.info("Проверенные кинологи для обращения - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getDOG_HANDLERS_CONTACTS());
                            }
                            if(textConstants.getREPORT_FORM().equals(text)){
                                logger.info("Отправить форму отчёта - ID:{}", chatId);
                                messageSender.sendMessage(chatId, information.getINFO_REPORT());
                            }
                            if(textConstants.getCALL_VOLUNTEER().equals(text)){
                                logger.info("Позвать волонтёра - ID:{}", chatId);
                                messageSender.sendMessageToVolunteers(message);
                                messageSender.sendMessage(chatId, "Первый освободившийся волонтёр ответит вам в ближайшее время");
                            }
                        } catch (Exception e) {
                            logger.error("Error processing message", e);
                        }
                    });
        } catch (Exception e) {
            logger.error("Error processing updates", e);
        }
        return CONFIRMED_UPDATES_ALL;
    }
}


