package com.jteam.GroupProject.controller;

import com.jteam.GroupProject.model.animal.Cat;
import com.jteam.GroupProject.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.shelters.CatShelter;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cats/shelters")
@Tag(name = "Кошачий приют", description = "CRUD-методы для работы с приютом")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, кошки довольны."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class CatSheltersController {

    private final ShelterService<CatShelter, Cat> catShelterService;

    @PostMapping("/")
    @Operation(
            summary = "Регистрация нового кошачьего приюта."
    )
    public CatShelter create(@RequestParam @Parameter(description = "Название приюта") String name,
                             @RequestParam @Parameter(description = "Адрес и схема проезда") String location,
                             @RequestParam @Parameter(description = "Расписание работы приюта") String timetable,
                             @RequestParam @Parameter(description = "О приюте") String aboutMe,
                             @RequestParam @Parameter(description = "Способ связи с охраной") String security,
                             @RequestParam @Parameter(description = "Рекомендации о технике безопасности на территории приюта") String safetyAdvice
    ) {
        return catShelterService.addShelter(new CatShelter(name, location, timetable, aboutMe, security, safetyAdvice));
    }

    @PutMapping("/")
    @Operation(
            summary = "Обновление информации о приюте"
    )
    public CatShelter update(@RequestParam @Parameter(description = "id приюта") long id,
                             @RequestParam(required = false) @Parameter(description = "Название приюта") String name,
                             @RequestParam(required = false) @Parameter(description = "Адрес и схема проезда") String location,
                             @RequestParam(required = false) @Parameter(description = "Расписание работы приюта") String timetable,
                             @RequestParam(required = false) @Parameter(description = "О приюте") String aboutMe,
                             @RequestParam(required = false) @Parameter(description = "Способ связи с охраной") String security,
                             @RequestParam(required = false) @Parameter(description = "Рекомендации о технике безопасности на территории приюта") String safetyAdvice) {

        return catShelterService.updateShelter(new CatShelter(id, name, location, timetable, aboutMe, security, safetyAdvice));
    }

    @GetMapping("/")
    @Operation(
            summary = "Список приютов"
    )
    public List<CatShelter> getAll() {
        return catShelterService.getShelter();
    }

    @GetMapping("/id{id}")
    @Operation(
            summary = "Поиск приюта по id"
    )
    public CatShelter getShelterId(@PathVariable @Parameter(description = "id приюта") long id) {
        return (catShelterService.getSheltersId(id));
    }

    @GetMapping("/list{id}")
    @Operation(
            summary = "Список животных приюта"
    )
    public List<Cat> getAnimal(@PathVariable @Parameter(description = "id приюта") long id) {
        return catShelterService.getAnimal(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление приюта"
    )

    public void delete(@PathVariable @Parameter(description = "id приюта") long id) {
        catShelterService.delShelter(id);
    }

}
