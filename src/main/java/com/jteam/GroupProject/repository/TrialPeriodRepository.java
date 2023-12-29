package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.TrialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TrialPeriodRepository extends JpaRepository<TrialPeriod, Long> {
    List<TrialPeriod> findAllByOwnerId(Long ownerId);
}
