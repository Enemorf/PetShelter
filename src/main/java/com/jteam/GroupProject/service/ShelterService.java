package com.jteam.GroupProject.service;

import java.util.List;

public interface ShelterService<T, D> {
    /**
     * Сохранить приют в БД
     * @param shelter объект приют
     * @return сохранение приюта в БД
     */

    T addShelter(T shelter);

    /**
     * Обновление данных приюта
     * @param shelter объект приют
     * @return shelter объект приют
     */

    T updateShelter(T shelter);

    /**
     * Получение приюта по id
     * @param id приюта
     * @return приют
     */
    T getSheltersId(long id);

    T getShelterByName(String name);

    /**
     * Выдача списка приютов
     * @return List со списком приютов
     */

    List<T> getShelter();

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
    String delShelter(long index);
}
