package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.DogOwner;
import com.jteam.GroupProject.repository.DogOwnerRepository;
import com.jteam.GroupProject.service.DogOwnerService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogOwnerServiceImpl implements DogOwnerService {
    private final DogOwnerRepository dogOwnerRepository;

    public DogOwnerServiceImpl(DogOwnerRepository dogOwnerRepository) {
        this.dogOwnerRepository = dogOwnerRepository;
    }
    /**
     * Создание и сохранение хозяина собаки в бд
     *
     * @param dogOwner   Хозяин собаки для сохранения в бд
     * @param animalType
     * @param animalId
     * @return Сохранённый хозяин собаки
     */
    @Override
    public DogOwner create(DogOwner dogOwner, TrialPeriod.AnimalType animalType, Long animalId) {
        return dogOwnerRepository.save(dogOwner, animalType, animalId);
    }

    /**
     * Создание хозяина собаки в бд из пользователя
     *
     * @param id         Пользователь из бота для сохранения в бд в качестве хозяина собаки
     * @param animalType
     * @param animalId
     * @return Сохранённый хозяин собаки
     */
    @Override
    public DogOwner create(Long id, TrialPeriod.AnimalType animalType, Long animalId) {
        return dogOwnerRepository.saveAnother(id, animalType, animalId);
    }

    /**
     * Получение хозяина собаки по id
     *
     * @param id Id хозяина собаки
     * @return Полученный из бд хозяин собаки
     */
    @Override
    public DogOwner getById(Long id) {
        return dogOwnerRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException("Dog owner not found with id: " + id));
    }

    /**
     * @return Список всех владельцев собак
     */
    @Override
    public List<DogOwner> getAll() {
        return dogOwnerRepository.findAll();
    }

    /**
     * Изменение хозяина собаки
     *
     * @param dogOwner хозяин собаки
     * @return Изменённый хозяин собаки
     */
    @Override
    public DogOwner update(DogOwner dogOwner) {
        try {
            dogOwnerRepository.save(dogOwner);
            return dogOwner;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update DogOwner", e);
        }
    }

    /**
     * @param dogOwner хозяин собаки, который уже есть в бд
     */
    @Override
    public void delete(DogOwner dogOwner) {
        dogOwnerRepository.delete(dogOwner);
    }

    /**
     * Удаление хозяина собаки по id
     *
     * @param id Id хозяина собаки
     */
    @Override
    public void deleteById(Long id) {
        try {
            dogOwnerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundIdException("DogOwner not found with id: " + id);
        }
    }
}
