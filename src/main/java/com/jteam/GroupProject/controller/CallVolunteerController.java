package com.jteam.GroupProject.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class CallVolunteerController
{
    @Value("{telegram.volunteer.name}")
    private String volunteerTelegramName;

    /**
     * Возвращает сообщение и ник для связи с волонтером
     * @return текстовое сообщение с ником волонтера в telegram
     */
    public String getVolunteerTelegramName()
    {
        return "Если возникли вопросы, напишите волонтеру " + volunteerTelegramName;
    }
}
