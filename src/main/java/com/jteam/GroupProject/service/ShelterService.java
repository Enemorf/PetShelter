package com.jteam.GroupProject.service;

import java.util.List;

public interface ShelterService<Shelter, D> {
    /**
     * Сохранить приют в БД
     * @param shelter объект приют
     * @return сохранение приюта в БД
     */

    Shelter addShelter(Shelter shelter);

    /**
     * Обновление данных приюта
     * @param shelter объект приют
     * @return shelter объект приют
     */

    Shelter updateShelter(Shelter shelter);

    /**
     * Получение приюта по id
     * @param id приюта
     * @return приют
     */
    Shelter getSheltersId(long id);

    Shelter getShelterByName(String name);

    /**
     * Выдача списка приютов
     * @return List со списком приютов
     */

    List<Shelter> getShelter();

    /**
     * Выдача списка животных приютов
     * @param index индекс приюта
     * @return List со списком приютов
     */
    List<D> getAnimal(long index);


    /**
     * Удаление приюта
     * @param index номер
     */
    void delShelter(long index);
}
