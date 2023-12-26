package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.model.shelters.DogShelter;
import com.jteam.GroupProject.repository.DogShelterRepository;
import com.jteam.GroupProject.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogShelterServiceImpl implements ShelterService<DogShelter, Dog> {
    private final DogShelterRepository dogShelterRepository;
    private final Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);


    @Override
    public DogShelter addShelter(DogShelter shelter) {
        DogShelter savedShelter = dogShelterRepository.save(shelter);
        logger.info("DogShelter added: {}", savedShelter);
        return savedShelter;
    }

    @Override
    public DogShelter updateShelter(DogShelter shelter) {
        DogShelter currentShelter = getSheltersId(shelter.getId());
        EntityUtils.copyNonNullFields(shelter, currentShelter);
        DogShelter updatedShelter = dogShelterRepository.save(currentShelter);
        logger.info("DogShelter updated: {}", updatedShelter);
        return updatedShelter;
    }


    @Override
    public DogShelter getSheltersId(long id) {
        return dogShelterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Приют не найден. Собачки остались без дома"));

    }

    @Override
    public DogShelter getShelterByName(String name) {
        return dogShelterRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Приют не найден. Собачки остались без дома"));

    }

    @Override
    public List<DogShelter> getShelter() {
        try {
            List<DogShelter> shelters = dogShelterRepository.findAll();
            logger.info("Retrieved list of dog shelters: {}", shelters);
            return shelters;
        } catch (Exception e) {
            logger.error("Error retrieving dog shelters: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving dog shelters", e);
        }
    }

    @Override
    public List<Dog> getAnimal(long index) {
        try {
            DogShelter shelter = getSheltersId(index);
            List<Dog> animals = shelter.getList();
            logger.info("Retrieved list of dogs from shelter {}: {}", index, animals);
            return animals;
        } catch (NotFoundException e) {
            logger.warn("Unable to retrieve dogs from shelter {}: {}", index, e.getMessage());
            return Collections.emptyList();
        }
    }


    @Override
    public void delShelter(long index) {
        Optional<DogShelter> dogShelterOptional = dogShelterRepository.findById(index);

        if (dogShelterOptional.isPresent()) {
            DogShelter dogShelter = dogShelterOptional.get();
            dogShelterRepository.deleteById(index);
            logger.info("DogShelter deleted: {}", dogShelter);
        } else {
            logger.warn("Attempt to delete non-existing DogShelter with id: {}", index);
            throw new NotFoundException("Собачки без приюта. Мы его не нашли.");
        }
    }
}
