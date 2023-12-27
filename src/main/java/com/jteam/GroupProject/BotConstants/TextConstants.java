package com.jteam.GroupProject.BotConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
// Константы, используемые в боте
public class TextConstants {

    @Value("${text.dogShelter}")
    public static String DOG_SHELTER;

    @Value("${text.catShelter}")
    public static String CAT_SHELTER;

    @Value("${text.infoShelter}")
    public static String INFO_SHELTER;

    @Value("${text.takeAnimalFromShelter}")
    public static String TAKE_ANIMAL_FROM_SHELTER;

    @Value("${text.report}")
    public static String REPORT;

    @Value("${text.callVolunteer}")
    public static String CALL_VOLUNTEER;

    @Value("${text.info}")
    public static String INFO;

    @Value("${text.schedule}")
    public static String SCHEDULE;

    @Value("${text.securityContacts}")
    public static String SECURITY_CONTACTS;

    @Value("${text.safetyPrecautions}")
    public static String SAFETY_PRECAUTIONS;

    @Value("${text.contacts}")
    public static String CONTACTS;

    @Value("${text.rulesForCats}")
    public static String RULES_A_CATS;

    @Value("${text.rulesForDogs}")
    public static String RULES_A_DOGS;

    @Value("${text.documentList}")
    public static String DOCUMENT_LIST;

    @Value("${text.transportPet}")
    public static String TRANSPORT_PET;

    @Value("${text.homeImprovementForPuppy}")
    public static String HOME_IMPROVEMENT_FOR_PUPPY;

    @Value("${text.homeImprovementForKitty}")
    public static String HOME_IMPROVEMENT_FOR_KITTY;

    @Value("${text.homeImprovementForAdultPet}")
    public static String HOME_IMPROVEMENT_FOR_ADULT_PET;

    @Value("${text.homeImprovementForPetWithLimitedOpportunities}")
    public static String HOME_IMPROVEMENT_FOR_PET_WITH_LIMITED_OPPORTUNITIES;

    @Value("${text.dogHandlerAdvice}")
    public static String DOG_HANDLER_ADVICE;

    @Value("${text.dogHandlerList}")
    public static String DOG_HANDLER_LIST;

    @Value("${text.reasonsForRefusal}")
    public static String REASONS_FOR_REFUSAL;

    @Value("${text.reportForm}")
    public static String REPORT_FORM;

    @Value("${text.passReport}")
    public static String PASS_REPORT;

    @Value("${text.mainMenu}")
    public static String MAIN_MENU;

    @Value("${text.faq}")
    public static String FAQ;

    @Value("${text.tbGuidelines}")
    public static String TB_GUIDELINES;

    @Value("${text.contactDetails}")
    public static String CONTACT_DETAILS;

    @Value("${text.listOfCats}")
    public static String LIST_OF_CATS;

    @Value("${text.listOfDogs}")
    public static String LIST_OF_DOGS;

    @Value("${text.backToAllAboutCats}")
    public static String BACK_TO_ALL_ABOUT_CATS;

    @Value("${text.backToAllAboutDogs}")
    public static String BACK_TO_ALL_ABOUT_DOGS;

    @Value("${text.catCarriage}")
    public static String CAT_CARRIAGE;

    @Value("${text.recommendationsForCats}")
    public static String RECOMMENDATIONS_FOR_CATS;

    @Value("${text.recommendationsForDogs}")
    public static String RECOMMENDATIONS_FOR_DOGS;

    @Value("${text.dogCarriage}")
    public static String DOG_CARRIAGE;

}
