package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.AlreadyExistsException;
import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.Report;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.repository.ReportRepository;
import com.jteam.GroupProject.service.ReportService;
import com.jteam.GroupProject.service.TrialPeriodService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final TrialPeriodService trialPeriodService;
    private final Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);

    /**
     * Сохранение отчёта в бд (Он же отвечает за обновление уже существующего отчёта)
     *
     * @param report Отчёт для сохранения в бд
     * @return Созданный отчёт
     */
    @Override
    public Report create(Report report) {

        Report savedReport = reportRepository.save(report);
        logger.info("Report created: {}", savedReport);
        return savedReport;
    }

    /**
     * Получение отчёта из бд по id<
     *
     * @param id id отчёта
     * @return Отчёт
     */
    @Override
    public Report getById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Отчёт не найден!"));

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
        Optional<Report> optionalReport = reportRepository.findByReceiveDateAndTrialPeriodId(date, id);
        if (optionalReport.isEmpty()) {
            throw new NotFoundException("Отчёт не найден!");
        }
        return optionalReport.get();
    }

    /**
     * Получение всех отчётов
     *
     * @return Список всех отчётов
     */
    @Override
    public List<Report> getAll() {
        return Optional.ofNullable(reportRepository.findAll())
                .orElseThrow(() -> new NotFoundException("Отчёты не найдены!"));

    }

    /**
     * Получение всех отчётов по id испытательного срока
     *
     * @param id id испытательного срока из бд
     * @return Список отчётов по испытательному сроку
     */
    @Override
    public List<Report> gelAllByTrialPeriodId(Long id) {
        List<Report> allByTrialPeriodId = reportRepository.findAllByTrialPeriodId(id);
        if (allByTrialPeriodId.isEmpty()) {
            throw new NotFoundException("Отчёты не найдены!");
        }
        return allByTrialPeriodId;
    }

    /**
     * Изменение существующего отчёта
     *
     * @param report Отчёт
     * @return Изменённый отчёт
     */
    @Override
    public Report update(Report report) {
        Long reportId = report.getId();

        // Проверка, что reportId не равен null
        if (reportId == null) {
            throw new IllegalArgumentException("Report id cannot be null");
        }

        // Проверка наличия отчета с заданным id
        Report currentReport = getById(reportId);

        // Копирование ненулевых полей
        EntityUtils.copyNonNullFields(report, currentReport);

        // Сохранение обновленного отчета
        Report updatedReport = reportRepository.save(currentReport);
        logger.info("Report updated: {}", updatedReport);

        return updatedReport;
    }

    /**
     * Удаление полученного из бд отчёта
     *
     * @param report Отчёт, который уже есть в бд
     */
    @Override
    public void delete(Report report) {
        reportRepository.delete(getById(report.getId()));
        logger.info("Report deleted: {}", report);
    }
    /**
     * Удаление отчёта по id
     *
     * @param id id отчёта
     */
    @Override
    public void deleteById(Long id) {

        reportRepository.deleteById(getById(id).getId());
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
        TrialPeriod trialPeriod = trialPeriodService.getAllByOwnerId(id).stream()
                .filter(trialPeriod1 -> trialPeriod1.getResult().equals(TrialPeriod.Result.IN_PROGRESS))
                .findFirst().get();
        if (trialPeriod.getLastReportDate().isEqual(LocalDate.now())) {
            throw new AlreadyExistsException("Вы уже отправляли отчёт сегодня.");
        }
        List<String> captionParts = splitCaption(caption);
        Report report = create(new Report(photoId, captionParts.get(0), captionParts.get(1),
                captionParts.get(2), LocalDate.now(), trialPeriod.getId()));
        trialPeriod.setLastReportDate(LocalDate.now());
        trialPeriodService.update(trialPeriod);
        return report;
    }

    /**
     * Метод разбивающий описание фотографии на части для создания отчёта
     *
     * @param caption Описание под фотографией
     * @return Список с частями для создания отчёта
     */
    private List<String> splitCaption(String caption) {
        Pattern pattern = Pattern.compile("(Рацион:)\\s(\\W+);\\n(Самочувствие:)\\s(\\W+);\\n(Поведение:)\\s(\\W+);");
        if (caption == null || caption.isBlank()) {
            throw new IllegalArgumentException("Описание под фотографией не должно быть пустым. Отправьте отчёт заново");
        }
        Matcher matcher = pattern.matcher(caption);
        if (matcher.find()) {
            return new ArrayList<>(List.of(matcher.group(2), matcher.group(4), matcher.group(6)));
        } else {
            throw new IllegalArgumentException("Invalid caption format. Check the entered data and submit the report again.");
        }
    }
}
