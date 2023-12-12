package com.jteam.GroupProject.botButtons;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.jteam.GroupProject.BotConstants.CallbackDataConstants.*;
import static com.jteam.GroupProject.BotConstants.TextConstants.*;
import static com.jteam.GroupProject.BotConstants.TextConstants.CALL_VOLUNTEER;

public class CreateButtonsStageTwo {
    public static EditMessageText createButtonsForTakeAnimal(Long chatId, int messageId, String takeAnimalText, boolean isDogShelter) {
        EditMessageText message = EditMessageText.builder()
                .text(takeAnimalText)
                .messageId(messageId)
                .chatId(chatId)
                .build();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton rulesButton = InlineKeyboardButton.builder()
                .text(RULES)
                .callbackData(CALLBACK_RULES)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow1 = List.of(rulesButton);

        InlineKeyboardButton documentsButton = InlineKeyboardButton.builder()
                .text(DOCUMENT_LIST)
                .callbackData(CALLBACK_DOCUMENT_LIST)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow2 = List.of(documentsButton);

        InlineKeyboardButton transportPetButton = InlineKeyboardButton.builder()
                .text(TRANSPORT_PET)
                .callbackData(CALLBACK_TRANSPORT_PET)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow3 = List.of(transportPetButton);

        InlineKeyboardButton homeForSmallPetButton = new InlineKeyboardButton();
        if (isDogShelter) {
            homeForSmallPetButton.setText(HOME_IMPROVEMENT_FOR_PUPPY);
        } else {
            homeForSmallPetButton.setText(HOME_IMPROVEMENT_FOR_KITTY);
        }
        homeForSmallPetButton.setCallbackData(CALLBACK_HOME_IMPROVEMENT_FOR_SMALL_PET);
        List<InlineKeyboardButton> keyboardButtonRow4 = List.of(homeForSmallPetButton);

        InlineKeyboardButton homeForAdultPet = InlineKeyboardButton.builder()
                .text(HOME_IMPROVEMENT_FOR_ADULT_PET)
                .callbackData(CALLBACK_HOME_IMPROVEMENT_FOR_ADULT_PET)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow5 = List.of(homeForAdultPet);

        InlineKeyboardButton homeForPetWithLimited = InlineKeyboardButton.builder()
                .text(HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES)
                .callbackData(CALLBACK_HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow6 = List.of(homeForPetWithLimited);

        InlineKeyboardButton dogHandlerAdvice = InlineKeyboardButton.builder()
                .text(DOG_HANDLER_ADVICE)
                .callbackData(CALLBACK_DOG_HANDLER_ADVICE)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow7 = List.of(dogHandlerAdvice);

        InlineKeyboardButton dogHandlerList = InlineKeyboardButton.builder()
                .text(DOG_HANDLER_LIST)
                .callbackData(CALLBACK_DOG_HANDLER_LIST)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow8 = List.of(dogHandlerList);

        InlineKeyboardButton reasonsForRefusal = InlineKeyboardButton.builder()
                .text(REASONS_FOR_REFUSAL)
                .callbackData(CALLBACK_REASONS_FOR_REFUSAL)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow9 = List.of(reasonsForRefusal);

        InlineKeyboardButton contacts = InlineKeyboardButton.builder()
                .text(CONTACTS)
                .callbackData(CALLBACK_CONTACTS)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow10 = List.of(contacts);

        InlineKeyboardButton callVolunteer = InlineKeyboardButton.builder()
                .text(CALL_VOLUNTEER)
                .callbackData(CALLBACK_CALL_VOLUNTEER)
                .build();
        List<InlineKeyboardButton> keyboardButtonRow11 = List.of(callVolunteer);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow1);
        rowList.add(keyboardButtonRow2);
        rowList.add(keyboardButtonRow3);
        rowList.add(keyboardButtonRow4);
        rowList.add(keyboardButtonRow5);
        rowList.add(keyboardButtonRow6);
        if (isDogShelter) {
            rowList.add(keyboardButtonRow7);
            rowList.add(keyboardButtonRow8);
        }
        rowList.add(keyboardButtonRow9);
        rowList.add(keyboardButtonRow10);
        rowList.add(keyboardButtonRow11);

        markup.setKeyboard(rowList);

        message.setReplyMarkup(markup);
        return message;
    }
}
