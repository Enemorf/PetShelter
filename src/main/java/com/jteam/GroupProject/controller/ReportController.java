package com.jteam.GroupProject.controller;

import com.pengrad.telegrambot.TelegramBot;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.Report;
import com.jteam.GroupProject.service.ReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("reports")
@Tag(name = "Отчёт", description = "CRUD-методы для работы с отчётами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, запрос выполнился."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class ReportController {

    private final ReportService reportService;
    private final TelegramBot telegramBot;

    public ReportController(ReportService reportService, TelegramBot telegramBot) {
        this.reportService = reportService;
        this.telegramBot= telegramBot;
    }

    @PostMapping
    @Operation(
            summary = "Создать отчёт"
    )
    public Report create(@RequestParam @Parameter(description = "Id фотографии") String photoId,
                         @RequestParam @Parameter(description = "Рацион животного") String foodRation,
                         @RequestParam @Parameter(description = "Общее самочувствие и привыкание к новому месту") String generalHealth,
                         @RequestParam @Parameter(description = "Изменение в поведении") String behaviorChanges,
                         @RequestParam @Parameter(description = "Id испытательного срока") Long trialPeriodId) {
        return reportService.create(new Report(photoId, foodRation, generalHealth, behaviorChanges, LocalDate.now(), trialPeriodId));
    }


    @GetMapping()
    @Operation(
            summary = "Получение всех отчётов"
    )
    public List<Report> getAll() {
        return reportService.getAll();
    }

    @GetMapping("trial-id")
    @Operation(
            summary = "Получение всех отчётов по id испытательного срока"
    )
    public List<Report> getAllByTrialPeriodId(@RequestParam @Parameter(description = "id испытательного срока") Long id) {
        return reportService.gelAllByTrialPeriodId(id);
    }

    @GetMapping("date-and-trial")
    @Operation(
            summary = "Получение отчёта по дате и id испытательного срока"
    )
    @Parameters(value = {
            @Parameter(
                    name = "date",
                    description = "Дата создания отчёта",
                    example = "2023-12-31"
            ),
            @Parameter(
                    name = "id",
                    description = "Id испытательного срока",
                    example = "1"
            )
    }
    )
    public Report getByDateAndTrialId(@RequestParam @Parameter(description = "Дата получения отчёта") LocalDate date,
                                      @RequestParam @Parameter(description = "id испытательного срока") Long id) {
        return reportService.getByDateAndTrialId(date, id);
    }

    @GetMapping("id")
    @Operation(summary = "Получение отчёта по id")
    public Report getById(@RequestParam @Parameter(description = "Id испытательного срока") Long reportId) {
        return reportService.getById(reportId);
    }

    @PutMapping
    @Operation(
            summary = "Изменить отчёт"
    )
    public Report update(@RequestParam @Parameter(description = "Id отчёта") Long id,
                         @RequestParam(required = false) @Parameter(description = "Id фотографии") String photoId,
                         @RequestParam(required = false) @Parameter(description = "Рацион животного") String foodRation,
                         @RequestParam(required = false) @Parameter(description = "Общее самочувствие и привыкание к новому месту") String generalHealth,
                         @RequestParam(required = false) @Parameter(description = "Изменение в поведении") String behaviorChanges,
                         @RequestParam(required = false) @Parameter(description = "Дата получения") LocalDate receiveDate,
                         @RequestParam(required = false) @Parameter(description = "Id испытательного срока") Long trialPeriodId) {
        return reportService.update(new Report(id, photoId, foodRation, generalHealth, behaviorChanges, receiveDate, trialPeriodId));
    }

    @DeleteMapping("id")
    @Operation(
            summary = "Удаление отчёта по id"
    )
    @Parameter(
            name = "id",
            description = "Id отчёта",
            example = "1"
    )
    public String deleteById(@RequestParam Long id) {
        reportService.deleteById(id);
        return "Отчёт успешно удалён";
    }
}
