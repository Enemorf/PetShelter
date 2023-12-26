package com.jteam.GroupProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.model.shelters.DogShelter;
import com.jteam.GroupProject.service.ShelterService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dogs/shelters")
@Tag(name = "Собачий приют", description = "CRUD-методы для работы с приютом")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, собачки довольны."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class DogSheltersController {

    private final ShelterService<DogShelter, Dog> dogShelterService;

    @PostMapping("/")
    @Operation(
            summary = "Регистрация нового собачьего приюта."
    )
    public DogShelter create(@RequestParam @Parameter(description = "Название приюта") String name,
                             @RequestParam @Parameter(description = "Адрес и схема проезда") String location,
                             @RequestParam @Parameter(description = "Расписание работы приюта") String timetable,
                             @RequestParam @Parameter(description = "О приюте") String aboutMe,
                             @RequestParam @Parameter(description = "Способ связи с охраной") String security,
                             @RequestParam @Parameter(description = "Рекомендации о технике безопасности на территории приюта") String safetyAdvice
    ) {
        return dogShelterService.addShelter(new DogShelter(name, location, timetable, aboutMe, security, safetyAdvice));
    }

    @PutMapping("/")
    @Operation(
            summary = "Обновление информации о приюте"
    )
    public DogShelter update(@RequestParam @Parameter(description = "id приюта") long id,
                             @RequestParam(required = false)
                             @Parameter(description = "Название приюта") String name,
                             @RequestParam(required = false)
                             @Parameter(description = "Адрес и схема проезда") String location,
                             @RequestParam(required = false)
                             @Parameter(description = "Расписание работы приюта") String timetable,
                             @RequestParam(required = false)
                             @Parameter(description = "О приюте") String aboutMe,
                             @RequestParam(required = false)
                             @Parameter(description = "Способ связи с охраной") String security,
                             @RequestParam(required = false)
                             @Parameter(description = "Рекомендации о технике безопасности на территории приюта") String safetyAdvice) {
        return dogShelterService.updateShelter((new DogShelter(id, name, location, timetable, aboutMe, security, safetyAdvice)));
    }

    @GetMapping("/")
    @Operation(
            summary = "Список приютов"
    )
    public List<DogShelter> getAll() {
        return dogShelterService.getShelter();
    }

    @GetMapping("/list{id}")
    @Operation(
            summary = "Список животных приюта"
    )
    public List<Dog> getAnimal(@PathVariable @Parameter(description = "id приюта") long id) {
        return dogShelterService.getAnimal(id);
    }

    @GetMapping("/id{id}")
    @Operation(
            summary = "Поиск приюта по id"
    )
    public DogShelter getShelterId(@PathVariable @Parameter(description = "id приюта") long id) {
        return (dogShelterService.getSheltersId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление приюта"
    )

    public void delete(@PathVariable @Parameter(description = "id приюта") long id) {
        dogShelterService.delShelter(id);
    }

}
