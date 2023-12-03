package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.TrialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrialPeriodRepository extends JpaRepository<TrialPeriod, Long> {
    TrialPeriod save(TrialPeriod trialPeriod, TrialPeriod.AnimalType animalType);

    List<TrialPeriod> findAllByOwnerId(Long ownerId);
}
