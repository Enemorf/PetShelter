package com.jteam.GroupProject.BotConstants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
@Getter
// Константы, используемые в боте
public class TextConstants {

    @Value("${text.dogShelter}")
    private String DOG_SHELTER;

    @Value("${text.catShelter}")
    private String CAT_SHELTER;

    @Value("${text.infoShelter}")
    private String INFO_SHELTER;

    @Value("${text.takeAnimalFromShelter}")
    private String TAKE_ANIMAL_FROM_SHELTER;

    @Value("${text.report}")
    private String REPORT;

    @Value("${text.callVolunteer}")
    private String CALL_VOLUNTEER;

    @Value("${text.info}")
    private String INFO;

    @Value("${text.schedule}")
    private String SCHEDULE;

    @Value("${text.securityContacts}")
    private String SECURITY_CONTACTS;

    @Value("${text.safetyPrecautions}")
    private String SAFETY_PRECAUTIONS;

    @Value("${text.contacts}")
    private String CONTACTS;

    @Value("${text.rulesForCats}")
    private String RULES_A_CATS;

    @Value("${text.rulesForDogs}")
    private String RULES_A_DOGS;

    @Value("${text.documentList}")
    private String DOCUMENT_LIST;

    @Value("${text.transportPet}")
    private String TRANSPORT_PET;

    @Value("${text.homeImprovementForPuppy}")
    private String HOME_IMPROVEMENT_FOR_PUPPY;

    @Value("${text.homeImprovementForKitty}")
    private String HOME_IMPROVEMENT_FOR_KITTY;

    @Value("${text.homeImprovementForAdultPet}")
    private String HOME_IMPROVEMENT_FOR_ADULT_PET;

    @Value("${text.homeImprovementForPetWithLimitedOpportunities}")
    private String HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES;

    @Value("${text.dogHandlerAdvice}")
    private String DOG_HANDLER_ADVICE;

    @Value("${text.dogHandlerList}")
    private String DOG_HANDLER_LIST;

    @Value("${text.reasonsForRefusal}")
    private String REASONS_FOR_REFUSAL;

    @Value("${text.reportForm}")
    private String REPORT_FORM;

    @Value("${text.passReport}")
    private String PASS_REPORT;

    @Value("${text.mainMenu}")
    private String MAIN_MENU;

    @Value("${text.faq}")
    private String FAQ;

    @Value("${text.tbGuidelines}")
    private String TB_GUIDELINES;

    @Value("${text.contactDetails}")
    private String CONTACT_DETAILS;

    @Value("${text.listOfCats}")
    private String LIST_OF_CATS;

    @Value("${text.listOfDogs}")
    private String LIST_OF_DOGS;

    @Value("${text.backToAllAboutCats}")
    private String BACK_TO_ALL_ABOUT_CATS;

    @Value("${text.backToAllAboutDogs}")
    private String BACK_TO_ALL_ABOUT_DOGS;

    @Value("${text.catCarriage}")
    private String CAT_CARRIAGE;

    @Value("${text.recommendationsForCats}")
    private String RECOMMENDATIONS_FOR_CATS;

    @Value("${text.recommendationsForDogs}")
    private String RECOMMENDATIONS_FOR_DOGS;

    @Value("${text.dogCarriage}")
    private String DOG_CARRIAGE;

}
