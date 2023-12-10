package com.jteam.GroupProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
@Tag(name = "Пользователь", description = "CRUD-методы для работы с пользователями")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, запрос выполнился."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestParam @Parameter(description = "Телеграм id пользователя") Long telegramId,
                       @RequestParam @Parameter(description = "Имя") String firstName,
                       @RequestParam @Parameter(description = "Фамилия") String lastName,
                       @RequestParam @Parameter(description = "Телефон") String phone) {
        return userService.create(new User(telegramId, firstName, lastName, phone));
    }

    @GetMapping()
    @Operation(summary = "Получение списка всех пользователей")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("id")
    @Operation(summary = "Получение пользователя по id")
    public User getById(@RequestParam @Parameter(description = "Id пользователя") Long userId) {
        return userService.getById(userId);
    }

    @PutMapping
    @Operation(summary = "Изменить пользователя")
    public User update(@RequestParam @Parameter(description = "Телеграм id пользователя") Long telegramId,
                       @RequestParam(required = false) @Parameter(description = "Имя") String firstName,
                       @RequestParam(required = false) @Parameter(description = "Фамилия") String lastName,
                       @RequestParam(required = false) @Parameter(description = "Телефон") String phone) {
        return userService.update(new User(telegramId, firstName, lastName, phone));
    }

    @DeleteMapping("id")
    @Operation(summary = "Удаление пользователя по id")
    public String deleteById(@RequestParam @Parameter(description = "Id пользователя") Long userId) {
        userService.deleteById(userId);
        return "Пользователь успешно удалён";
    }
}
