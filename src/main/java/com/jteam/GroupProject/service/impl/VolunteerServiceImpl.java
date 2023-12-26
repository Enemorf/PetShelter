package com.jteam.GroupProject.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.model.Volunteer;
import com.jteam.GroupProject.repository.VolunteerRepo;
import com.jteam.GroupProject.service.VolunteerService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepo volunteerRepo;
    private final Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);

    @Override
    public Volunteer create(Volunteer volunteer) {

        Volunteer savedVolunteer = volunteerRepo.save(volunteer);
        logger.info("Volunteer created: {}", savedVolunteer);
        return savedVolunteer;
    }

    @Override
    public Volunteer getById(Long id) {
        return volunteerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("По указанному id волонтёр не найден!"));

    }

    @Override
    public List<Volunteer> getAll() {
        List<Volunteer> all = volunteerRepo.findAll();
        if (all.isEmpty()) {
            throw new NotFoundException("Волонтёры не найдены!");
        }
        return all;
    }

    @Override
    public Volunteer update(Volunteer volunteer) {
        Volunteer currentVolunteer = getById(volunteer.getTelegramId());
        EntityUtils.copyNonNullFields(volunteer, currentVolunteer);
        Volunteer updatedVolunteer = volunteerRepo.save(currentVolunteer);
        logger.info("Volunteer updated: {}", updatedVolunteer);
        return updatedVolunteer;
    }

    @Override
    public void delete(Volunteer volunteer) {
        volunteerRepo.delete(getById(volunteer.getTelegramId()));
        logger.info("Volunteer deleted: {}", volunteer);
    }
    @Override
    public void deleteById(Long id) {
        if (volunteerRepo.existsById(id)) {
            volunteerRepo.deleteById(id);
            logger.info("Volunteer deleted by ID: {}", id);
        } else {
            logger.warn("Volunteer with ID {} not found, delete operation skipped.", id);
        }
    }
}
