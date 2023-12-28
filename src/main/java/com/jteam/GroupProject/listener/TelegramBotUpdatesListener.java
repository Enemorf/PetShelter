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

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        ReplyMarkup replyMarkup = new ReplyMarkup(telegramBot, catShelterService, dogShelterService);
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

                            switch (text) {
                                case "/start", "К выбору приютов" -> {
                                    logger.info("Запустили бота/выбрали приют - ID:{}", chatId);
                                    replyMarkup.sendStartMenu(chatId);
                                }
                                case TextConstants.CAT_SHELTER -> messageSender.sendMenuStage("CAT", chatId);
                                case TextConstants.DOG_SHELTER -> messageSender.sendMenuStage("DOG", chatId);
                                case TextConstants.INFO_SHELTER -> {
                                    logger.info("Узнать информацию о приюте - ID:{}", chatId);
                                    shelterType = userService.getShelterById(chatId);
                                    if ("CAT".equals(shelterType)) {
                                        replyMarkup.sendListMenuCat(chatId);
                                    } else if ("DOG".equals(shelterType)) {
                                        replyMarkup.sendListMenuDog(chatId);
                                    }
                                }
                                case TextConstants.MAIN_MENU -> {
                                    logger.info("Главное меню - ID:{}", chatId);
                                    user.setShelterType(null);
                                    user.setShelterName(null);
                                    userService.update(user);
                                    replyMarkup.sendStartMenu(chatId);
                                }
                                case TextConstants.SCHEDULE -> {
                                    logger.info("Расписание работы - ID:{}", chatId);
                                    if (user.getShelterType().equals("CAT")) {
                                        messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getTimetable());
                                    } else if (user.getShelterType().equals("DOG")) {
                                        messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getTimetable());
                                    }
                                }
                                case TextConstants.LIST_OF_CATS -> {
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
                                case TextConstants.LIST_OF_DOGS -> {
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
                                case TextConstants.INFO -> {
                                    logger.info("О приюте - ID:{}", chatId);
                                    if (user.getShelterType().equals("CAT")) {
                                        messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getAboutMe());
                                    } else if (user.getShelterType().equals("DOG")) {
                                        messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getAboutMe());
                                    }
                                }
                                case TextConstants.TB_GUIDELINES -> {
                                    logger.info("Рекомендации о ТБ - ID:{}", chatId);
                                    if (user.getShelterType().equals("CAT")) {
                                        messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getSafetyAdvice());
                                    } else if (user.getShelterType().equals("DOG")) {
                                        messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getSafetyAdvice());
                                    }
                                }
                                case TextConstants.CONTACT_DETAILS -> {
                                    logger.info("Как отправить свои данные для связи - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, "Введите номер телефона в формате: 89997776655");
                                }

                                case TextConstants.SECURITY_CONTACTS -> {
                                    logger.info("Контактные данные охраны - ID:{}", chatId);
                                    if (user.getShelterType().equals("CAT")) {
                                        messageSender.sendMessage(chatId, catShelterService.getShelterByName(user.getShelterName()).getSecurity());
                                    } else if (user.getShelterType().equals("DOG")) {
                                        messageSender.sendMessage(chatId, dogShelterService.getShelterByName(user.getShelterName()).getSecurity());
                                    }
                                }
                                case TextConstants.FAQ -> {
                                    logger.info("Часто задаваемые вопросы - ID:{}", chatId);
                                    if (user.getShelterType().equals("CAT")) {
                                        replyMarkup.menuCat(chatId);
                                    } else if (user.getShelterType().equals("DOG")) {
                                        replyMarkup.menuDog(chatId);
                                    }
                                }
                                case TextConstants.BACK_TO_ALL_ABOUT_CATS -> {
                                    logger.info("Все о кошках - ID:{}", chatId);
                                    replyMarkup.menuCat(chatId);
                                }
                                case TextConstants.BACK_TO_ALL_ABOUT_DOGS -> {
                                    logger.info("Все о собаках - ID:{}", chatId);
                                    replyMarkup.menuDog(chatId);
                                }
                                case TextConstants.RULES_A_CATS, TextConstants.RULES_A_DOGS -> {
                                    logger.info("Правила знакомства - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.ANIMAL_DATING_RULES);
                                }
                                case TextConstants.CAT_CARRIAGE, TextConstants.DOG_CARRIAGE -> {
                                    logger.info("Перевозка - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.TRANSPORTATION_OF_THE_ANIMAL);
                                }
                                case TextConstants.DOCUMENT_LIST -> {
                                    logger.info("Необходимые документы - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.LIST_OF_DOCUMENTS);
                                }
                                case TextConstants.REASONS_FOR_REFUSAL -> {
                                    logger.info("Список причин для отказа выдачи питомца - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.LIST_OF_REASON_FOR_DENY);
                                }
                                case TextConstants.RECOMMENDATIONS_FOR_DOGS -> {
                                    logger.info("Рекомендации для собак - ID:{}", chatId);
                                    replyMarkup.rulesForDogs(chatId);
                                }
                                case TextConstants.RECOMMENDATIONS_FOR_CATS -> {
                                    logger.info("Рекомендации для кошек - ID:{}", chatId);
                                    replyMarkup.rulesForCats(chatId);
                                }
                                case TextConstants.HOME_IMPROVEMENT_FOR_KITTY, TextConstants.HOME_IMPROVEMENT_FOR_PUPPY -> {
                                    logger.info("Обустройство щенка/котенка - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.RECOMMENDATIONS_HOME_IMPROVEMENT_KITTEN_PUPPY);
                                }
                                case TextConstants.HOME_IMPROVEMENT_FOR_ADULT_PET -> {
                                    logger.info("Обустройство взрослого животного - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.RECOMMENDATIONS_HOME_IMPROVEMENT_ADULT_ANIMAL);
                                }
                                case TextConstants.HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES -> {
                                    logger.info("Обустройство животного с ограниченными возможностями - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.RECOMMENDATIONS_HOME_IMPROVEMENT_DISABLED_ANIMAL);
                                }
                                case TextConstants.DOG_HANDLER_ADVICE -> {
                                    logger.info("Советы кинолога - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.DOG_HANDLERS_ADVICE);
                                }
                                case TextConstants.DOG_HANDLER_LIST -> {
                                    logger.info("Проверенные кинологи для обращения - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.DOG_HANDLERS_CONTACTS);
                                }
                                case TextConstants.REPORT_FORM -> {
                                    logger.info("Отправить форму отчёта - ID:{}", chatId);
                                    messageSender.sendMessage(chatId, Information.INFO_REPORT);
                                }
                                case TextConstants.CALL_VOLUNTEER -> {
                                    logger.info("Позвать волонтёра - ID:{}", chatId);
                                    messageSender.sendMessageToVolunteers(message);
                                    messageSender.sendMessage(chatId, "Первый освободившийся волонтёр ответит вам в ближайшее время");
                                }
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


