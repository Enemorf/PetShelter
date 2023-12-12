package com.jteam.GroupProject.service.impl;

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

        when(reportRepositoryMock.findByDateAndTrialPeriodId(date, reportId)).thenReturn(expectedReport);

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

        when(reportRepositoryMock.existsById(existingReport.getId())).thenReturn(true);
        when(reportRepositoryMock.save(existingReport)).thenReturn(existingReport);

        Report updatedReport = reportService.update(existingReport);

        assertNotNull(updatedReport);
        assertEquals(existingReport, updatedReport);
    }
    @Test
    void testGetAllReportsByTrialPeriodId() {
        List<Report> reports = new ArrayList<>();
        when(reportRepositoryMock.findAllByTrialPeriodId(reportId)).thenReturn(reports);

        List<Report> retrievedReports = reportService.gelAllByTrialPeriodId(reportId);

        assertNotNull(retrievedReports);
        assertEquals(reports, retrievedReports);
        verify(reportRepositoryMock, times(1)).findAllByTrialPeriodId(reportId);
    }
    @Test
    void testDeleteReport() {
        Report report = new Report();

        assertDoesNotThrow(() -> reportService.delete(report));

        verify(reportRepositoryMock, times(1)).delete(report);
    }
    @Test
    @DisplayName("Test successful removal of report by id")
    void testRemoveReportById() {

        when(reportRepositoryMock.existsById(reportId)).thenReturn(true);

        assertDoesNotThrow(() -> reportService.deleteById(reportId));
        verify(reportRepositoryMock, times(1)).deleteById(reportId);
    }

    @Test
    @DisplayName("Test removal of non-existing report by id")
    void testRemoveNonExistingReportById() {

        when(reportRepositoryMock.existsById(reportId)).thenReturn(false);

        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> reportService.deleteById(reportId));
        assertEquals("Report not found with id: " + reportId, exception.getMessage());

        verify(reportRepositoryMock, times(0)).deleteById(reportId);
    }
}

