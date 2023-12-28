package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByTrialPeriodId(Long id);
    Optional<Report> findByReceiveDateAndTrialPeriodId(LocalDate date, Long id);
}
