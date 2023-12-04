package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.animal.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long>{

    List<Dog> findAllByOwnerId(Long id);
}
