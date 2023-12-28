package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.Report;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    private ReportRepository reportRepositoryMock;

    @InjectMocks
    private ReportServiceImpl reportService;
    private Long reportId;
    @BeforeEach
    void setUp() {

        reportId = 1L;
    }
    @Test
    void testCreate() {
        Report report = new Report();

        when(reportRepositoryMock.save(report)).thenReturn(report);
        Report createdReport = reportService.create(report);

        assertNotNull(createdReport);
        assertEquals(report, createdReport);
        verify(reportRepositoryMock, times(1)).save(report);

    }
    @Test
    void testGetReportById() {
        Report expectedReport = new Report();

        when(reportRepositoryMock.findById(reportId)).thenReturn(Optional.of(expectedReport));

        Report retrievedReport = reportService.getById(reportId);

        assertNotNull(retrievedReport);
        assertEquals(expectedReport, retrievedReport);
    }
    @Test
    void testGetReportByDateAndTrialId() {
        LocalDate date = LocalDate.now();
        Report expectedReport = new Report();

        when(reportRepositoryMock.findByReceiveDateAndTrialPeriodId(date, reportId)).thenReturn(Optional.of(expectedReport));

        Report retrievedReport = reportService.getByDateAndTrialId(date, reportId);

        assertNotNull(retrievedReport);
        assertEquals(expectedReport, retrievedReport);
    }
    @Test
    void testGetAll() {
        List<Report> reports = new ArrayList<>();

        when(reportRepositoryMock.findAll()).thenReturn(reports);

        List<Report> retrievedReports = reportService.getAll();

        assertNotNull(retrievedReports);
        assertEquals(reports, retrievedReports);
        verify(reportRepositoryMock, times(1)).findAll();
    }
    @Test
    void testUpdateReport() {
        Report existingReport = new Report();
        existingReport.setId(1L);

        // Настройка мока для возврата существующего отчета при вызове findById с id = 1L
        when(reportRepositoryMock.findById(existingReport.getId())).thenReturn(Optional.of(existingReport));

        // Настройка мока для возврата existingReport при вызове save
        when(reportRepositoryMock.save(existingReport)).thenReturn(existingReport);

        Report updatedReport = reportService.update(existingReport);

        assertNotNull(updatedReport);
        assertEquals(existingReport, updatedReport);
    }
    @Test
    @DisplayName("Test getting all reports by trial period ID with empty list")
    void testGetAllReportsByTrialPeriodIdEmptyList() {
        Long trialPeriodId = 1L;

        // Предположим, что репозиторий возвращает пустой список
        when(reportRepositoryMock.findAllByTrialPeriodId(trialPeriodId)).thenReturn(new ArrayList<>());

        // Проверяем, что выбрасывается NotFoundException
        assertThrows(NotFoundException.class, () -> reportService.gelAllByTrialPeriodId(trialPeriodId));
    }

    @Test
    @DisplayName("Test getting all reports by trial period ID with non-empty list")
    void testGetAllReportsByTrialPeriodIdNonEmptyList() {
        Long trialPeriodId = 1L;

        // Предположим, что репозиторий возвращает непустой список
        List<Report> reports = List.of(new Report(), new Report());
        when(reportRepositoryMock.findAllByTrialPeriodId(trialPeriodId)).thenReturn(reports);

        // Проверяем, что метод возвращает ожидаемый непустой список
        List<Report> retrievedReports = reportService.gelAllByTrialPeriodId(trialPeriodId);

        assertNotNull(retrievedReports);
        assertEquals(reports, retrievedReports);
    }

    @Test
    @DisplayName("Test deleting report by ID")
    void testDeleteById() {

        // Предположим, что отчет существует
        Report existingReport = new Report();
        existingReport.setId(reportId);
        when(reportRepositoryMock.findById(reportId)).thenReturn(Optional.of(existingReport));

        // Вызываем метод удаления
        reportService.deleteById(reportId);

        // Проверяем, что метод deleteById был вызван с ожидаемым ID
        verify(reportRepositoryMock, times(1)).deleteById(reportId);
    }

    @Test
    @DisplayName("Test deleting non-existing report by ID")
    void testDeleteNonExistingById() {
        Long nonExistingReportId = 2L;

        // Предположим, что отчет не существует
        when(reportRepositoryMock.findById(nonExistingReportId)).thenReturn(Optional.empty());

        // Проверяем, что вызывается NotFoundException при попытке удаления
        NotFoundException exception = assertThrows(NotFoundException.class, () -> reportService.deleteById(nonExistingReportId));
        assertEquals("Отчёт не найден!", exception.getMessage());

        // Проверяем, что метод deleteById не вызывается
        verify(reportRepositoryMock, times(0)).deleteById(nonExistingReportId);
    }
}

