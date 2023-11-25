package com.jteam.GroupProject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "photo_id")
    private String photoId;
    @Column(name = "food_ration")
    private String foodRation;
    @Column(name = "general_health")
    private String generalHealth;
    @Column(name = "behavior_changes")
    private String behaviorChanges;
    @Column(name = "receive_date")
    private LocalDate receiveDate;
    @Column(name = "trial_period_Id")
    private Long trialPeriodId;

    public Report(String photoId, String foodRation, String generalHealth,
                  String behaviorChanges, LocalDate receiveDate, Long trialPeriodId) {
        this.photoId = photoId;
        this.foodRation = foodRation;
        this.generalHealth = generalHealth;
        this.behaviorChanges = behaviorChanges;
        this.receiveDate = receiveDate;
        this.trialPeriodId = trialPeriodId;
    }
}
