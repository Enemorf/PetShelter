package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.Report;
import com.jteam.GroupProject.repository.ReportRepository;
import com.jteam.GroupProject.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    /**
     * Сохранение отчёта в бд (Он же отвечает за обновление уже существующего отчёта)
     *
     * @param report Отчёт для сохранения в бд
     * @return Созданный отчёт
     */
    @Override
    public Report create(Report report) {
        return reportRepository.save(report);
    }

    /**
     * Получение отчёта из бд по id<
     *
     * @param id id отчёта
     * @return Отчёт
     */
    @Override
    public Report getById(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        try {
            return report.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение отчёта из бд по дате
     *
     * @param date Дата создания отчёта
     * @param id   id Испытательного срока
     * @return Отчёт
     */
    @Override
    public Report getByDateAndTrialId(LocalDate date, Long id) {
        return reportRepository.findByDateAndTrialPeriodId(date, id);
    }

    /**
     * Получение всех отчётов
     *
     * @return Список всех отчётов
     */
    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    /**
     * Получение всех отчётов по id испытательного срока
     *
     * @param id id испытательного срока из бд
     * @return Список отчётов по испытательному сроку
     */
    @Override
    public List<Report> gelAllByTrialPeriodId(Long id) {
        return reportRepository.findAllByTrialPeriodId(id);
    }

    /**
     * Изменение существующего отчёта
     *
     * @param report Отчёт
     * @return Изменённый отчёт
     */
    @Override
    public Report update(Report report) {
        Optional<Report> report2 = reportRepository.findById(report.getId());
        try {
            reportRepository.save(report);
            return report2.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление полученного из бд отчёта
     *
     * @param report Отчёт, который уже есть в бд
     */
    @Override
    public void delete(Report report) {
        reportRepository.save(report);
    }

    /**
     * Удаление отчёта по id
     *
     * @param id id отчёта
     */
    @Override
    public void deleteById(Long id) {
        try {
            reportRepository.deleteById(id);
            throw new NotFoundIdException();
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создание отчёта по данным из телеграма
     * Используются методы из сервиса испытательных сроков {@link /TrialPeriodService#/getAllByOwnerId(Long)}
     * Так же методы этого же сервиса {@link ReportService#create(Report)}
     *
     * @param photoId Id фотографии из чата в телеграме
     * @param caption Описание под фотографией
     * @param id      id пользователя
     * @return Созданный отчёт
     */
    @Override
    public Report createFromTelegram(String photoId, String caption, Long id) {
        return reportRepository.saveFromTelegram(photoId, caption, id);
    }
}
