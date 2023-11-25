package com.jteam.GroupProject.service;

import com.jteam.GroupProject.model.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    /**
     * Сохранение отчёта в бд (Он же отвечает за обновление уже существующего отчёта)
     * @param report Отчёт для сохранения в бд
     * @return Созданный отчёт
     */
    Report create(Report report);

    /**
     * Получение отчёта из бд по id<
     * @param id id отчёта
     * @return Отчёт
     */
    Report getById(Long id);

    /**
     * Получение отчёта из бд по дате
     * @param date Дата создания отчёта
     * @param id   id Испытательного срока
     * @return Отчёт
     */
    Report getByDateAndTrialId(LocalDate date, Long id);

    /**
     * Получение всех отчётов
     * @return Список всех отчётов
     */
    List<Report> getAll();

    /**
     * Получение всех отчётов по id испытательного срока
     * @param id id испытательного срока из бд
     * @return Список отчётов по испытательному сроку
     */
    List<Report> gelAllByTrialPeriodId(Long id);

    /**
     * Изменение существующего отчёта
     * @param report Отчёт
     * @return Изменённый отчёт
     */
    Report update(Report report);

    /**
     * Удаление полученного из бд отчёта
     * @param report Отчёт, который уже есть в бд
     */
    void delete(Report report);

    /**
     * Удаление отчёта по id
     * @param id id отчёта
     */
    void deleteById(Long id);

    /**
     * Создание отчёта по данным из телеграма
     * Используются методы из сервиса испытательных сроков {@link TrialPeriodService#getAllByOwnerId(Long)}
     * Так же методы этого же сервиса {@link ReportService#create(Report)}
     * @param photoId Id фотографии из чата в телеграме
     * @param caption Описание под фотографией
     * @param id      id пользователя
     * @return Созданный отчёт
     */
    Report createFromTelegram(String photoId, String caption, Long id);
}
