package com.jteam.GroupProject.repository;

import com.jteam.GroupProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

    User findAllById(Long id);
}
