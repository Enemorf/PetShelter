package com.jteam.GroupProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface ShelterRepository<T, D extends Serializable> extends JpaRepository<T, D> {
    Object getShelterByName(String name);

    String deleteById(long index);

    List findAllById(long index);
}
