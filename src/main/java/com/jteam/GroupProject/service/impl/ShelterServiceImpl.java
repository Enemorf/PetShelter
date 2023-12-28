package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.animal.Animal;
import com.jteam.GroupProject.repository.ShelterRepository;
import com.jteam.GroupProject.service.ShelterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterServiceImpl implements ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }
    /**
     * Сохранить приют в БД
     *
     * @param shelter объект приют
     * @return сохранение приюта в БД
     */
    @Override
    public Object addShelter(Object shelter) {
        return shelterRepository.save(shelter);
    }

    /**
     * Обновление данных приюта
     *
     * @param shelter объект приют
     * @return shelter объект приют
     */
    @Override
    public Object updateShelter(Object shelter) {
        return shelterRepository.save(shelter);
    }

    /**
     * Получение приюта по id
     *
     * @param id приюта
     * @return приют
     */
    @Override
    public Object getSheltersId(long id) {
        return shelterRepository.findById(id);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Object getShelterByName(String name) {
        return shelterRepository.getShelterByName(name);
    }

    /**
     * Выдача списка приютов
     *
     * @return List со списком приютов
     */
    @Override
    public List getShelter() {
        return shelterRepository.findAll();
    }

    /**
     * Выдача списка животных приютов
     *
     * @param index индекс приюта
     * @return List со списком приютов
     */
    @Override
    public List<Animal> getAnimal(long index) {
        List<Animal> animals = shelterRepository.findAllById(index);

        if (animals.isEmpty()) {
            throw new NotFoundIdException("No animals found for shelter with index: " + index);
        }

        return animals;
    }

    /**
     * Удаление приюта
     *
     * @param index номер
     */
    @Override
    public String delShelter(long index) {
        if (!shelterRepository.existsById(index)) {
            throw new NotFoundIdException("Shelter not found with index: " + index);
        }
        return shelterRepository.deleteById(index);
    }
}
