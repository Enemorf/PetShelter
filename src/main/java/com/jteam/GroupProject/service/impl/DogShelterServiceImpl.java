package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.model.shelters.DogShelter;
import com.jteam.GroupProject.repository.DogShelterRepository;
import com.jteam.GroupProject.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogShelterServiceImpl implements ShelterService<DogShelter, Dog> {
    private final DogShelterRepository dogShelterRepository;


    @Override
    public DogShelter addShelter(DogShelter shelter) {
        return dogShelterRepository.save(shelter);
    }

    @Override
    public DogShelter updateShelter(DogShelter shelter) {
        DogShelter currentShelter = getSheltersId(shelter.getId());
        EntityUtils.copyNonNullFields(shelter, currentShelter);
        return dogShelterRepository.save(currentShelter);
    }

    @Override
    public DogShelter getSheltersId(long id) {
        Optional<DogShelter> shelterId = dogShelterRepository.findById(id);
        if (shelterId.isEmpty()) {
            throw new NotFoundException("Приют не найден. Собачки остались без дома");
        }
        return shelterId.get();
    }

    @Override
    public DogShelter getShelterByName(String name) {
        Optional<DogShelter> shelterId = dogShelterRepository.findByName(name);
        if (shelterId.isEmpty()) {
            throw new NotFoundException("Приют не найден. Собачки остались без дома");
        }
        return shelterId.get();
    }

    @Override
    public List<DogShelter> getShelter() {
        return dogShelterRepository.findAll();
    }

    @Override
    public List<Dog> getAnimal(long index) {
        return getSheltersId(index).getList();
    }


    @Override
    public String delShelter(long index) {
        String result;
        Optional<DogShelter> dogShelter = dogShelterRepository.findById(index);
        if (dogShelter.isPresent()) {
            dogShelterRepository.deleteById(index);
            result = "Запись удалена";
        } else {
            throw new NotFoundException("Собачки без приюта. Мы его не нашли(");
        }
        return result;
    }
}
