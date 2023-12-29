package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DogOwnerRepository extends JpaRepository<DogOwner, Long> {
    Optional<DogOwner> findByTelegramId(Long telegramId);
}
