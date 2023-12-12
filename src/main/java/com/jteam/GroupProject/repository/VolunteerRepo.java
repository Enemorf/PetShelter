package com.jteam.GroupProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jteam.GroupProject.model.Volunteer;

@Repository
public interface VolunteerRepo extends JpaRepository<Volunteer, Long> {

}
