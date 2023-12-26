package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.AlreadyExistsException;
import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.DogOwner;
import com.jteam.GroupProject.repository.DogOwnerRepository;
import com.jteam.GroupProject.service.DogOwnerService;
import com.jteam.GroupProject.service.DogService;
import com.jteam.GroupProject.service.TrialPeriodService;
import com.jteam.GroupProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogOwnerServiceImpl implements DogOwnerService {
    private final DogOwnerRepository dogOwnerRepository;
    private final UserService userService;
    private final DogService dogService;
    private final TrialPeriodService trialPeriodService;

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
        if (dogService.getById(animalId).getOwnerId() != null) {
            throw new AlreadyExistsException("У этой собаки уже есть хозяин!");
        }
        trialPeriodService.create(new TrialPeriod(LocalDate.now(), LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(1), new ArrayList<>(),
                TrialPeriod.Result.IN_PROGRESS, dogOwner.getTelegramId(), animalType, animalId), animalType);
        dogService.getById(animalId).setOwnerId(dogOwner.getTelegramId());
        return dogOwnerRepository.save(dogOwner);
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
        if (dogService.getById(animalId).getOwnerId() != null) {
            throw new AlreadyExistsException("У этой собаки уже есть хозяин!");
        }
        DogOwner dogOwner = new DogOwner(userService.getById(id));
        trialPeriodService.create(new TrialPeriod(LocalDate.now(), LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(1), new ArrayList<>(),
                TrialPeriod.Result.IN_PROGRESS, id, animalType, animalId), animalType);
        dogService.getById(animalId).setOwnerId(id);
        return dogOwnerRepository.save(dogOwner);
    }

    /**
     * Получение хозяина собаки по id
     *
     * @param id Id хозяина собаки
     * @return Полученный из бд хозяин собаки
     */
    @Override
    public DogOwner getById(Long id) {
        return dogOwnerRepository.findByTelegramId(id)
                .orElseThrow(() -> new NotFoundException("Хозяин собаки не найден!"));

    }

    /**
     * @return Список всех владельцев собак
     */
    @Override
    public List<DogOwner> getAll() {
        List<DogOwner> all = dogOwnerRepository.findAll();
        if (all.isEmpty()) {
            throw new NotFoundException("Владельцев собак нет!");
        }
        return all;
    }

    /**
     * Изменение хозяина собаки
     *
     * @param dogOwner хозяин собаки
     * @return Изменённый хозяин собаки
     */
    @Override
    public DogOwner update(DogOwner dogOwner) {
        DogOwner currentDogOwner = getById(dogOwner.getTelegramId());
        EntityUtils.copyNonNullFields(dogOwner, currentDogOwner);
        return dogOwnerRepository.save(currentDogOwner);
    }

    /**
     * @param dogOwner хозяин собаки, который уже есть в бд
     */
    @Override
    public void delete(DogOwner dogOwner) {
        dogService.getAllByUserId(dogOwner.getTelegramId()).forEach(dog -> {
            dog.setOwnerId(null);
            dogService.update(dog);
        });
        dogOwnerRepository.delete(getById(dogOwner.getTelegramId()));
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
