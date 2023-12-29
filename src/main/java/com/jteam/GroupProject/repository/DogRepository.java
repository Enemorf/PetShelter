package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.animal.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DogRepository extends JpaRepository<Dog, Long>{

    List<Dog> findAllByOwnerId(Long id);
}
