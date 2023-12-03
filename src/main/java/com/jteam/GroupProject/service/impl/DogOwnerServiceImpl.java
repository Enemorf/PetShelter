package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.DogOwner;
import com.jteam.GroupProject.repository.DogOwnerRepository;
import com.jteam.GroupProject.service.DogOwnerService;
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
        Optional<DogOwner> dogOwner = dogOwnerRepository.findById(id);
        try {
            return dogOwner.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
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
        Optional<DogOwner> dogOwner2 = dogOwnerRepository.findById(dogOwner.getId());
        try {
            dogOwnerRepository.save(dogOwner);
            return dogOwner2.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
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
            throw new NotFoundIdException();
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }
}
