package com.jteam.GroupProject.BotConstants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Information {

    @Value("${information.howToAdoptAnAnimal}")
    private String HOW_TO_ADOPT_AN_ANIMAL;

    @Value("${information.welcome}")
    private String WELCOME;

    @Value("${information.animalDatingRules}")
    private String ANIMAL_DATING_RULES;

    @Value("${information.listOfDocuments}")
    private String LIST_OF_DOCUMENTS;

    @Value("${information.recommendationsHomeImprovementKittenPuppy}")
    private String RECOMMENDATIONS_HOME_IMPROVEMENT_KITTEN_PUPPY;

    @Value("${information.recommendationsHomeImprovementAdultAnimal}")
    private String RECOMMENDATIONS_HOME_IMPROVEMENT_ADULT_ANIMAL;

    @Value("${information.recommendationsHomeImprovementDisabledAnimal}")
    private String RECOMMENDATIONS_HOME_IMPROVEMENT_DISABLED_ANIMAL;

    @Value("${information.dogHandlersAdvice}")
    private String DOG_HANDLERS_ADVICE;

    @Value("${information.dogHandlersContacts}")
    private String DOG_HANDLERS_CONTACTS;

    @Value("${information.listOfReasonForDeny}")
    private String LIST_OF_REASON_FOR_DENY;

    @Value("${information.trialNotSuccessful}")
    private String TRIAL_NOT_SUCCESSFUL;

    @Value("${information.trialExtended}")
    private String TRIAL_EXTENDED;

    @Value("${information.successful}")
    private String SUCCESSFUL;

    @Value("${information.transportationOfTheAnimal}")
    private String TRANSPORTATION_OF_THE_ANIMAL;

    @Value("${information.infoReport}")
    private String INFO_REPORT;

}

