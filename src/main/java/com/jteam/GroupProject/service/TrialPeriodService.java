package com.jteam.GroupProject.service;

import com.jteam.GroupProject.model.TrialPeriod;

import java.util.List;

public interface TrialPeriodService {
    /**
     * Сохранение испытательного периода в бд
     * @param trialPeriod Испытательный срок для сохранения в бд
     * @return Созданный испытательный срок
     */
    TrialPeriod create(TrialPeriod trialPeriod);

    /**
     * Сохранение испытательного периода в бд при создании владельца
     * @param trialPeriod Испытательный срок для сохранения в бд
     * @param animalType  Тип животного
     * @return Созданный испытательный срок
     */
    TrialPeriod create(TrialPeriod trialPeriod, TrialPeriod.AnimalType animalType);

    /**
     * Получение испытательного срока из бд по id
     * @param id id испытательного срока
     * @return Испытательный срок
     */
    TrialPeriod getById(Long id);

    /**
     * Получение всех отчётов
     * @return Список всех отчётов
     */
    List<TrialPeriod> getAll();

    /**
     * Получение всех отчётов конкретного пользователя
     * @param ownerId id хозяина, по которому будет идти поиск
     * @return Список испытательных сроков по хозяину
     */
    List<TrialPeriod> getAllByOwnerId(Long ownerId);

    /**
     * Изменение существующего испытательного срока
     * @param trialPeriod Испытательный срок
     * @return Обновлённый испытательный срок
     */
    TrialPeriod update(TrialPeriod trialPeriod);

    /**
     * Удаление полученного из бд испытательного срока
     * @param trialPeriod Испытательный срок, который уже есть в бд
     */
    void delete(TrialPeriod trialPeriod);

    /**
     * Удаление испытательного срока по id
     * @param id id испытательного срока
     */
    void deleteById(Long id);
}