package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    Optional<CatOwner> findByTelegramId(Long telegramId);
}
