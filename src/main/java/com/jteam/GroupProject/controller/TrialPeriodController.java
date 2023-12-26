package com.jteam.GroupProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.service.TrialPeriodService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("trial-periods")
@Tag(name = "Испытательный срок", description = "CRUD-методы для работы с испытательными сроками")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, запрос выполнился."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class TrialPeriodController {

    private final TrialPeriodService trialPeriodService;

    public TrialPeriodController(TrialPeriodService trialPeriodService) {
        this.trialPeriodService = trialPeriodService;
    }

    @PostMapping
    @Operation(
            summary = "Создать испытательный срок"
    )
    public TrialPeriod create(@RequestParam @Parameter(description = "Дата начала испытательного срока") LocalDate startDate,
                              @RequestParam @Parameter(description = "Состояние") TrialPeriod.Result result,
                              @RequestParam @Parameter(description = "Id хозяина животного") Long ownerId,
                              @RequestParam @Parameter(description = "Тип взятого животного") TrialPeriod.AnimalType animalType,
                              @RequestParam @Parameter(description = "Id животного") Long animalId) {
        return trialPeriodService.create(new TrialPeriod(startDate, startDate.plusDays(30),
                startDate.minusDays(1), new ArrayList<>(), result, ownerId, animalType, animalId));
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех испытательных сроков"
    )
    public List<TrialPeriod> getAll() {
        return trialPeriodService.getAll();
    }

    @GetMapping("owner")
    @Operation(summary = "Получение всех испытательных сроков по id хозяина")
    public List<TrialPeriod> getAllByOwnerId(@RequestParam @Parameter(description = "Id хозяина животного") Long ownerId) {
        return trialPeriodService.getAllByOwnerId(ownerId);
    }

    @GetMapping("id")
    @Operation(
            summary = "Получение испытательного срока по id"
    )
    @Parameter(
            name = "id",
            description = "Id ипытательного срока",
            example = "1"
    )
    public TrialPeriod getById(@RequestParam Long id) {
        return trialPeriodService.getById(id);
    }

    @PutMapping
    @Operation(
            summary = "Изменить испытательный срок"
    )
    public TrialPeriod update(@RequestParam @Parameter(description = "Id испытательного срока") Long id,
                              @RequestParam(required = false) @Parameter(description = "Дата начала испытательного срока") LocalDate startDate,
                              @RequestParam(required = false) @Parameter(description = "Дата окончания испытательного срока") LocalDate endDate,
                              @RequestParam(required = false) @Parameter(description = "Дата последнего отчёта") LocalDate lastReportDate,
                              @RequestParam(required = false) @Parameter(description = "Состояние") TrialPeriod.Result result,
                              @RequestParam(required = false) @Parameter(description = "Id хозяина животного") Long ownerId,
                              @RequestParam(required = false) @Parameter(description = "Тип взятого животного") TrialPeriod.AnimalType animalType,
                              @RequestParam(required = false) @Parameter(description = "Id животного") Long animalId) {
        return trialPeriodService.update(new TrialPeriod(id, startDate, endDate,
                lastReportDate, new ArrayList<>(), result, ownerId, animalType, animalId));
    }

    @DeleteMapping("id")
    @Operation(summary = "Удаление испытательного срока по id")
    public String deleteById(@RequestParam @Parameter(description = "Id испытательного срока") Long id) {
        trialPeriodService.deleteById(id);
        return "Испытательный срок успешно удалён";
    }
}
