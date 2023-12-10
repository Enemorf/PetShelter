package com.jteam.GroupProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.DogOwner;
import com.jteam.GroupProject.service.DogOwnerService;

import java.util.List;

@RestController
@RequestMapping("dogOwners")
@Tag(name = "Владелец собаки", description = "CRUD-методы для работы с владельцами собак")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, запрос выполнился."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class DogOwnerController {
    private final DogOwnerService dogOwnerService;

    public DogOwnerController(DogOwnerService dogOwnerService) {
        this.dogOwnerService = dogOwnerService;
    }

    @PostMapping
    @Operation(
            summary = "Создать владельца собаки"
    )
    public DogOwner create(@RequestParam @Parameter(description = "Телеграм id владельца собаки") Long telegramId,
                           @RequestParam @Parameter(description = "Имя") String firstName,
                           @RequestParam @Parameter(description = "Фамилия") String lastName,
                           @RequestParam @Parameter(description = "Телефон") String phone,
                           @RequestParam @Parameter(description = "Id собаки") Long animalId) {
        return dogOwnerService.create(new DogOwner(telegramId, firstName, lastName, phone,
                null, null), TrialPeriod.AnimalType.DOG, animalId);
    }

    @PostMapping("/user")
    @Operation(
            summary = "Создать владельца собаки в бд из пользователя"
    )
    public DogOwner create(@RequestParam @Parameter(description = "Пользователь") Long id,
                           @RequestParam @Parameter(description = "Id собаки") Long animalId) {
        return dogOwnerService.create(id, TrialPeriod.AnimalType.DOG, animalId);
    }

    @GetMapping()
    @Operation(
            summary = "Получение списка всех владельцев собак"
    )
    public List<DogOwner> getAll() {
        return dogOwnerService.getAll();
    }

    @GetMapping("id")
    @Operation(
            summary = "Получение владельца собаки по id"
    )
    @Parameter(
            name = "id",
            description = "Id владельца собаки",
            example = "1"
    )
    public DogOwner getById(@RequestParam Long dogOwnerId) {
        return dogOwnerService.getById(dogOwnerId);
    }

    @PutMapping
    @Operation(
            summary = "Изменить владельца собаки"
    )
    public DogOwner update(@RequestParam @Parameter(description = "Телеграм id владельца собаки") Long telegramId,
                           @RequestParam(required = false) @Parameter(description = "Имя") String firstName,
                           @RequestParam(required = false) @Parameter(description = "Фамилия") String lastName,
                           @RequestParam(required = false) @Parameter(description = "Телефон") String phone) {
        return dogOwnerService.update(new DogOwner(telegramId, firstName, lastName, phone,
                null, null));
    }

    @DeleteMapping("id")
    @Operation(
            summary = "Удаление владельца собаки по id"
    )

    public String deleteById(@RequestParam @Parameter(description = "Id владельца собаки") Long dogOwnerId) {
        dogOwnerService.deleteById(dogOwnerId);
        return "Владелец собаки успешно удалён";
    }
}
