package com.jteam.GroupProject.service.impl;

import lombok.RequiredArgsConstructor;
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

    @Override
    public Volunteer create(Volunteer volunteer) {
        return volunteerRepo.save(volunteer);
    }

    @Override
    public Volunteer getById(Long id) {
        Optional<Volunteer> optionalVolunteer = volunteerRepo.findById(id);
        if (optionalVolunteer.isEmpty()) {
            throw new NotFoundException("По указанному id волонтёр не найден!");
        }
        return optionalVolunteer.get();
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
        Volunteer currentVolunteer =getById(volunteer.getTelegramId());
        EntityUtils.copyNonNullFields(volunteer, currentVolunteer);
        return volunteerRepo.save(currentVolunteer);
    }

    @Override
    public void delete(Volunteer volunteer) {
        volunteerRepo.delete(getById(volunteer.getTelegramId()));
    }

    @Override
    public void deleteById(Long id) {
        volunteerRepo.deleteById(getById(id).getTelegramId());
    }
}
