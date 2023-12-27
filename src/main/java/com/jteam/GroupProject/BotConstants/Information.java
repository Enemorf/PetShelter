package com.jteam.GroupProject.BotConstants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Information {
    @Value("${information.howToAdoptAnAnimal}")
    public static String HOW_TO_ADOPT_AN_ANIMAL;

    @Value("${information.welcome}")
    public static String WELCOME;

    @Value("${information.animalDatingRules}")
    public static String ANIMAL_DATING_RULES;

    @Value("${information.listOfDocuments}")
    public static String LIST_OF_DOCUMENTS;

    @Value("${information.recommendationsHomeImprovementKittenPuppy}")
    public static String RECOMMENDATIONS_HOME_IMPROVEMENT_KITTEN_PUPPY;

    @Value("${information.recommendationsHomeImprovementAdultAnimal}")
    public static String RECOMMENDATIONS_HOME_IMPROVEMENT_ADULT_ANIMAL;

    @Value("${information.recommendationsHomeImprovementDisabledAnimal}")
    public static String RECOMMENDATIONS_HOME_IMPROVEMENT_DISABLED_ANIMAL;

    @Value("${information.dogHandlersAdvice}")
    public static String DOG_HANDLERS_ADVICE;

    @Value("${information.dogHandlersContacts}")
    public static String DOG_HANDLERS_CONTACTS;

    @Value("${information.listOfReasonForDeny}")
    public static String LIST_OF_REASON_FOR_DENY;

    @Value("${information.trialNotSuccessful}")
    public static String TRIAL_NOT_SUCCESSFUL;


    @Value("${information.trialExtended}")
    public static String TRIAL_EXTENDED;

    @Value("${information.successful}")
    public static String SUCCESSFUL;

    @Value("${information.transportationOfTheAnimal}")
    public static String TRANSPORTATION_OF_THE_ANIMAL;

    @Value("${information.infoReport}")
    public static String INFO_REPORT;
}

