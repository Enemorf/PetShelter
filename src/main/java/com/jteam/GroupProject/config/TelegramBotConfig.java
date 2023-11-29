package com.jteam.GroupProject.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TelegramBotConfig {


    @Value("${telegram.bot.name}")
    private String botName;

}
