package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    CatOwner save(CatOwner catOwner, TrialPeriod.AnimalType animalType, Long animalId);

    CatOwner saveAnother(Long id, TrialPeriod.AnimalType animalType, Long animalId);
}
