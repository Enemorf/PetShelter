package com.jteam.GroupProject.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.Volunteer;
import com.jteam.GroupProject.service.VolunteerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("volunteers")
@Tag(name = "Волонтёр", description = "CRUD-методы для работы с волонтёрами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, запрос выполнился."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class VolunteerController {
    private final VolunteerService volunteerService;

    private final TelegramBot telegramBot;


    @PostMapping
    @Operation(summary = "Создать волонтёра")
    public Volunteer create(@RequestParam @Parameter(description = "Телеграм id волонтёра") Long telegramId,
                            @RequestParam @Parameter(description = "Имя") String firstName,
                            @RequestParam @Parameter(description = "Фамилия") String lastName) {
        return volunteerService.create(new Volunteer(telegramId, firstName, lastName));
    }

    @GetMapping()
    @Operation(summary = "Получение всех волонтёров")
    public List<Volunteer> getAll() {
        return volunteerService.getAll();
    }

    @GetMapping("id")
    @Operation(summary = "Получение волонтёра по id")
    public Volunteer getById(@RequestParam @Parameter(description = "Id волонтёра") Long volunteerId) {
        return volunteerService.getById(volunteerId);
    }

    @PutMapping
    @Operation(summary = "Изменить волонтёра")
    public Volunteer update(@RequestParam @Parameter(description = "Телеграм id волонтёра") Long telegramId,
                            @RequestParam(required = false) @Parameter(description = "Имя") String firstName,
                            @RequestParam(required = false) @Parameter(description = "Фамилия") String lastName) {
        return volunteerService.update(new Volunteer(telegramId, firstName, lastName));
    }

    @DeleteMapping("id")
    @Operation(summary = "Удаление волонтёра по id")
    public String deleteById(@RequestParam @Parameter(description = "Id волонтёра") Long volunteerId) {
        volunteerService.deleteById(volunteerId);
        return "Волонтёр успешно удалён";
    }

    @Tag(name = "Сообщения пользователю")
    @PostMapping("warning-message")
    @Operation(summary = "Отправить хозяину предупреждение о правильности отчётов")
    public String sendWarning(@RequestParam @Parameter(description = "Id хозяина") Long ownerId) {
        telegramBot.execute(new SendMessage(ownerId,
                """
                        Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо.
                        Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного
                        """));
        return "Сообщение успешно отправлено";
    }
}
