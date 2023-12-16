package com.jteam.GroupProject.replymarkup;

import com.jteam.GroupProject.BotConstants.Information;
import com.jteam.GroupProject.BotConstants.TextConstants;
import com.jteam.GroupProject.model.shelters.CatShelter;
import com.jteam.GroupProject.model.shelters.DogShelter;
import com.jteam.GroupProject.service.impl.CatShelterServiceImpl;
import com.jteam.GroupProject.service.impl.DogShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ReplyMarkup {
    private final TelegramBot telegramBot;
    private final CatShelterServiceImpl catShelterService;
    private final DogShelterServiceImpl dogShelterService;


    public void sendStartMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.CAT_SHELTER),
                new KeyboardButton(TextConstants.DOG_SHELTER));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.CALL_VOLUNTEER),
                new KeyboardButton(TextConstants.REPORT_FORM));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, Information.WELCOME);
    }


    public void sendMenuStage(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.INFO_SHELTER),
                new KeyboardButton(TextConstants.FAQ),
                new KeyboardButton(TextConstants.REPORT_FORM));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.CALL_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.MAIN_MENU));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Выберите:");
    }

    public void sendListMenuCat(long chatId) {
        List<CatShelter> shelters = catShelterService.getShelter();
        List<KeyboardButton> buttons = new ArrayList<>();
        shelters.forEach(shelter -> buttons.add(new KeyboardButton(shelter.getName())));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buttons.toArray(new KeyboardButton[0]));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Список кошачьих приютов");
    }

    public void sendListMenuDog(long chatId) {
        List<DogShelter> shelters = dogShelterService.getShelter();
        List<KeyboardButton> buttons = new ArrayList<>();
        shelters.forEach(shelter -> buttons.add(new KeyboardButton(shelter.getName())));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buttons.toArray(new KeyboardButton[0]));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Список собачьих приютов");
    }

    public void sendMenuCat(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.SCHEDULE),
                new KeyboardButton(TextConstants.LIST_OF_CATS),
                new KeyboardButton(TextConstants.INFO));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.TB_GUIDELINES),
                new KeyboardButton(TextConstants.CONTACT_DETAILS),
                new KeyboardButton(TextConstants.SECURITY_CONTACTS));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.CALL_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.MAIN_MENU));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Информация о кошачьем приюте");
    }

    public void sendMenuDog(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.SCHEDULE),
                new KeyboardButton(TextConstants.LIST_OF_DOGS),
                new KeyboardButton(TextConstants.INFO));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.TB_GUIDELINES),
                new KeyboardButton(TextConstants.CONTACT_DETAILS),
                new KeyboardButton(TextConstants.SECURITY_CONTACTS));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.CALL_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(TextConstants.MAIN_MENU));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Информация о собачьем приюте");
    }

    public void menuCat(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.DOCUMENT_LIST),
                new KeyboardButton(TextConstants.REASONS_FOR_REFUSAL)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.RECOMMENDATIONS_FOR_CATS),
                new KeyboardButton(TextConstants.CALL_VOLUNTEER),
                new KeyboardButton(TextConstants.CONTACT_DETAILS)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.MAIN_MENU)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Все о кошках");
    }

    public void menuDog(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.DOCUMENT_LIST),
                new KeyboardButton(TextConstants.REASONS_FOR_REFUSAL)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.RECOMMENDATIONS_FOR_DOGS),
                new KeyboardButton(TextConstants.CALL_VOLUNTEER),
                new KeyboardButton(TextConstants.CONTACT_DETAILS)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.MAIN_MENU)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Все о собаках");
    }

    public void rulesForDogs(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.RULES_A_DOGS),
                new KeyboardButton(TextConstants.DOG_CARRIAGE),
                new KeyboardButton(TextConstants.HOME_IMPROVEMENT_FOR_PUPPY)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.HOME_IMPROVEMENT_FOR_ADULT_PET),
                new KeyboardButton(TextConstants.HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.DOG_HANDLER_ADVICE),
                new KeyboardButton(TextConstants.DOG_HANDLER_LIST));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.BACK_TO_ALL_ABOUT_DOGS)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, TextConstants.RECOMMENDATIONS_FOR_DOGS);
    }

    public void rulesForCats(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(TextConstants.RULES_A_CATS),
                new KeyboardButton(TextConstants.CAT_CARRIAGE)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.HOME_IMPROVEMENT_FOR_ADULT_PET),
                new KeyboardButton(TextConstants.HOME_IMPROVEMENT_FOR_KITTY),
                new KeyboardButton(TextConstants.HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(TextConstants.BACK_TO_ALL_ABOUT_CATS)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, TextConstants.RECOMMENDATIONS_FOR_CATS);
    }

    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        telegramBot.execute(request);
    }
}
