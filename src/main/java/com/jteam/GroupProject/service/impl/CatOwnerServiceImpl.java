package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.CatOwner;
import com.jteam.GroupProject.repository.CatOwnerRepository;
import com.jteam.GroupProject.service.CatOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatOwnerServiceImpl implements CatOwnerService {
    private final CatOwnerRepository catOwnerRepository;

    public CatOwnerServiceImpl(CatOwnerRepository catOwnerRepository) {
        this.catOwnerRepository = catOwnerRepository;
    }
    /**
     * Создание и сохранение хозяина кота в бд
     *
     * @param catOwner   Хозяин кота для сохранения в бд
     * @param animalType
     * @param animalId
     * @return Сохранённый хозяин кота
     */
    @Override
    public CatOwner create(CatOwner catOwner, TrialPeriod.AnimalType animalType, Long animalId) {
        return catOwnerRepository.save(catOwner, animalType, animalId);
    }

    /**
     * Создание хозяина кота в бд из пользователя
     *
     * @param id         Пользователь из бота для сохранения в бд в качестве хозяина кота
     * @param animalType
     * @param animalId
     * @return Сохранённый хозяин кота
     */
    @Override
    public CatOwner create(Long id, TrialPeriod.AnimalType animalType, Long animalId) {
        return catOwnerRepository.saveAnother(id, animalType, animalId);
    }

    /**
     * Получение хозяина кота по id
     *
     * @param id Id хозяина кота
     * @return Полученный из бд хозяин кота
     */
    @Override
    public CatOwner getById(Long id) {
        Optional<CatOwner> catOwner = catOwnerRepository.findById(id);
        try {
            return catOwner.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return Список всех владельцев котов
     */
    @Override
    public List<CatOwner> getAll() {
        return catOwnerRepository.findAll();
    }

    /**
     * Изменение хозяина кота
     *
     * @param catOwner хозяин кота
     * @return Изменённый хозяин кота
     */
    @Override
    public CatOwner update(CatOwner catOwner) {
        Optional<CatOwner> catOwner2 = catOwnerRepository.findById(catOwner.getId());
        try {
            catOwnerRepository.save(catOwner);
            return catOwner2.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param catOwner хозяин кота, который уже есть в бд
     */
    @Override
    public void delete(CatOwner catOwner) {
        catOwnerRepository.delete(catOwner);
    }

    /**
     * Удаление хозяина кота по id
     *
     * @param id Id хозяина кота
     */
    @Override
    public void deleteById(Long id) {
        if (catOwnerRepository.existsById(id)) {
            catOwnerRepository.deleteById(id);
        } else {
            throw new NotFoundIdException("Cat owner not found with id: " + id);
        }
    }
}
