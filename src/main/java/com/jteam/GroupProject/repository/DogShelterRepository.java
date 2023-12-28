package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.shelters.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogShelterRepository extends JpaRepository<DogShelter, Long> {
    Optional<DogShelter> findByName(String name);
}
