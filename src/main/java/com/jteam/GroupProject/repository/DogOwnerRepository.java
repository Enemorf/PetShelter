package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogOwnerRepository extends JpaRepository<DogOwner, Long> {
    DogOwner save(DogOwner dogOwner, TrialPeriod.AnimalType animalType, Long animalId);

    DogOwner saveAnother(Long id, TrialPeriod.AnimalType animalType, Long animalId);
}
