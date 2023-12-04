package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findByDateAndTrialPeriodId(LocalDate date, Long id);

    List<Report> findAllByTrialPeriodId(Long id);

    Report saveFromTelegram(String photoId, String caption, Long id);
}
