package com.jteam.GroupProject.service;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.CatOwner;

import java.util.List;

public interface CatOwnerService {

    /**
     * Создание и сохранение хозяина кота в бд
     * @param catOwner Хозяин кота для сохранения в бд
     * @return Сохранённый хозяин кота
     */
    CatOwner create(CatOwner catOwner, TrialPeriod.AnimalType animalType, Long animalId);

    /**
     * Создание хозяина кота в бд из пользователя
     * @param id Пользователь из бота для сохранения в бд в качестве хозяина кота
     * @return Сохранённый хозяин кота
     */
    CatOwner create(Long id, TrialPeriod.AnimalType animalType, Long animalId);

    /**
     * Получение хозяина кота по id
     * @param id Id хозяина кота
     * @return Полученный из бд хозяин кота
     */
    CatOwner getById(Long id);

    /**
     * @return Список всех владельцев котов
     */
    List<CatOwner> getAll();

    /**
     * Изменение хозяина кота
     * @param catOwner хозяин кота
     * @return Изменённый хозяин кота
     */
    CatOwner update(CatOwner catOwner);

    /**
     * @param catOwner хозяин кота, который уже есть в бд
     */
    void delete(CatOwner catOwner);

    /**
     * Удаление хозяина кота по id
     * @param id Id хозяина кота
     */
    void deleteById(Long id);
}
