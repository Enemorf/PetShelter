package com.jteam.GroupProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Главная")
public class MainController {
    @GetMapping("/")
    @Operation(
            summary = "Главная страница приложения"
    )
    public String mainPage() {
        return "Home";
    }
}
