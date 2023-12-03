package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.animal.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long>{

    List<Cat> findAllByOwnerId(Long id);
}
