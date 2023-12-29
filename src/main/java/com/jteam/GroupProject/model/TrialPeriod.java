package com.jteam.GroupProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trial_periods")
public class TrialPeriod {
    public enum Result {
        IN_PROGRESS,
        SUCCESSFUL,
        NOT_SUCCESSFUL,
        EXTENDED
    }

    public enum AnimalType {
        CAT,
        DOG
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "last_report_date")
    private LocalDate lastReportDate;

    @OneToMany(mappedBy = "trialPeriodId", cascade = CascadeType.ALL)
    private List<Report> reports;

    /**
     * Стадия испытательного срока
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private Result result;

    @Column(name = "owner_id")
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type")
    private AnimalType animalType;

    @Column(name = "animal_id")
    private Long animalId;

    public TrialPeriod(LocalDate startDate, LocalDate endDate, LocalDate lastReportDate, List<Report> reports, Result result, Long ownerId, AnimalType animalType, Long animalId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastReportDate = lastReportDate;
        this.reports = reports;
        this.result = result;
        this.ownerId = ownerId;
        this.animalType = animalType;
        this.animalId = animalId;
    }
}
