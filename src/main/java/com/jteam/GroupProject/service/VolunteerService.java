package com.jteam.GroupProject.service;

import com.jteam.GroupProject.model.Volunteer;

import java.util.List;

public interface VolunteerService {
    /**
     * Создание и сохранение волонтёра в бд
     * @param volunteer Волонтёр для сохранения в бд, не может быть null
     * @return Сохранённый волонтёр
     */
    Volunteer create(Volunteer volunteer);

    /**
     * Получение волонтёра по id
     * @param id Id волонтёра, не может быть null
     * @return Полученный из бд волонтёр
     */
    Volunteer getById(Long id);

    /**
     * Получение волонтёра по id<br>
     * @return Список всех волонтёров
     */
    List<Volunteer> getAll();

    /**
     * Изменение волонтёра
     * @param volunteer Волонтёр, не может быть null
     * @return Изменённый волонтёр
     */
    Volunteer update(Volunteer volunteer);

    /**
     * Удаление волонтёра через объект
     * @param volunteer Волонтёр, который уже есть в бд
     */
    void delete(Volunteer volunteer);

    /**
     * Удаление волонтёра по id
     * @param id Id волонтёра
     */
    void deleteById(Long id);
}
