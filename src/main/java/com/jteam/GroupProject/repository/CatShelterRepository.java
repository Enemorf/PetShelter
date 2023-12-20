package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.shelters.CatShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatShelterRepository extends JpaRepository<CatShelter, Long> {
    Optional<CatShelter> findByName(String name);
}
