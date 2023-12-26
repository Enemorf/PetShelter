package com.jteam.GroupProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.service.DogService;

import java.util.List;

@RestController
@RequestMapping("/dogs")
@Tag(name = "Собаки", description = "CRUD-методы для работы с собаками")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, запрос выполнился."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class DogController {

    private final DogService dogService;

    @GetMapping("/id")
    @Operation(summary = "Получение собаки по ID")
    public Dog getById(@RequestParam @Parameter(description = "ID собаки") Long id) {
        return dogService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Добавить собаку в приют")
    public Dog create(
            @RequestParam @Parameter(description = "Имя собаки") String name,
            @RequestParam @Parameter(description = "Возраст") int age,
            @RequestParam @Parameter(description = "Здоров?") boolean isHealthy,
            @RequestParam @Parameter(description = "Привит?") boolean vaccinated,
            @RequestParam @Parameter(description = "ID приюта") Long shelterId) {
        return dogService.create(new Dog(name, age, isHealthy, vaccinated, shelterId));
    }

    @GetMapping()
    @Operation(summary = "Получение всех собак")
    public List<Dog> getAll() {
        return dogService.getAll();
    }

    @GetMapping("/ownerID")
    @Operation(summary = "Получение списка собак по ID хозяина")
    public List<Dog> getOwnerById(@RequestParam @Parameter(description = "ID хозяина собаки") Long id) {
        return dogService.getAllByUserId(id);
    }


    @PutMapping
    @Operation(summary = "Изменить информацию о собаке")
    public Dog update(
            @RequestParam @Parameter(description = "ID собаки") Long id,
            @RequestParam(required = false) @Parameter(description = "Имя собаки") String name,
            @RequestParam(required = false) @Parameter(description = "Возраст собаки") Integer age,
            @RequestParam(required = false) @Parameter(description = "Здоров?") Boolean isHealthy,
            @RequestParam(required = false) @Parameter(description = "Привит?") Boolean vaccinated,
            @RequestParam(required = false) @Parameter(description = "ID собачьего приюта") Long shelterId,
            @RequestParam(required = false) @Parameter(description = "ID хозяина") Long ownerId) {
        return dogService.update(new Dog(id, name, age, isHealthy, vaccinated, shelterId, ownerId));
    }

    @DeleteMapping("/id")
    @Operation(summary = "Удаление собаки")
    public String deleteById(@RequestParam Long id) {
        dogService.remove(id);
        return "Собаку выбросили на улицу";
    }
}
