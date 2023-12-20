package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.repository.TrialPeriodRepository;
import com.jteam.GroupProject.service.CatService;
import com.jteam.GroupProject.service.DogService;
import com.jteam.GroupProject.service.TrialPeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrialPeriodServiceImpl implements TrialPeriodService {
    private final TrialPeriodRepository trialPeriodRepository;
    private final CatService catService;
    private final DogService dogService;

    /**
     * Сохранение испытательного периода в бд
     *
     * @param trialPeriod Испытательный срок для сохранения в бд
     * @return Созданный испытательный срок
     */
    @Override
    public TrialPeriod create(TrialPeriod trialPeriod) {
        return trialPeriodRepository.save(trialPeriod);
    }

    /**
     * Сохранение испытательного периода в бд при создании владельца
     *
     * @param trialPeriod Испытательный срок для сохранения в бд
     * @param animalType  Тип животного
     * @return Созданный испытательный срок
     */
    @Override
    public TrialPeriod create(TrialPeriod trialPeriod, TrialPeriod.AnimalType animalType) {
        if (animalType.equals(TrialPeriod.AnimalType.CAT)) {
            catService.getById(trialPeriod.getAnimalId()).setOwnerId(trialPeriod.getOwnerId());
        } else if (animalType.equals(TrialPeriod.AnimalType.DOG)) {
            dogService.getById(trialPeriod.getAnimalId()).setOwnerId(trialPeriod.getOwnerId());
        }
        return trialPeriodRepository.save(trialPeriod);
    }

    /**
     * Получение испытательного срока из бд по id
     *
     * @param id id испытательного срока
     * @return Испытательный срок
     */
    @Override
    public TrialPeriod getById(Long id) {
        Optional<TrialPeriod> optionalTrialPeriod = trialPeriodRepository.findById(id);
        if (optionalTrialPeriod.isEmpty()) {
            throw new NotFoundException("Испытательный срок не найден!");
        }
        return optionalTrialPeriod.get();
    }

    /**
     * Получение всех отчётов
     *
     * @return Список всех отчётов
     */
    @Override
    public List<TrialPeriod> getAll() {
        List<TrialPeriod> all = trialPeriodRepository.findAll();
        if (all.isEmpty()) {
            throw new NotFoundException("Испытательные сроки не найдены!");
        }
        return all;
    }

    /**
     * Получение всех отчётов конкретного пользователя
     *
     * @param ownerId id хозяина, по которому будет идти поиск
     * @return Список испытательных сроков по хозяину
     */
    @Override
    public List<TrialPeriod> getAllByOwnerId(Long ownerId) {
        List<TrialPeriod> allByOwnerId = trialPeriodRepository.findAllByOwnerId(ownerId);
        if (allByOwnerId.isEmpty()) {
            throw new NotFoundException("Испытательные сроки не найдены!");
        }
        return allByOwnerId;
    }

    /**
     * Изменение существующего испытательного срока
     *
     * @param trialPeriod Испытательный срок
     * @return Обновлённый испытательный срок
     */
    @Override
    public TrialPeriod update(TrialPeriod trialPeriod) {
        TrialPeriod currentTrialPeriod = getById(trialPeriod.getId());
        EntityUtils.copyNonNullFields(trialPeriod, currentTrialPeriod);
        return trialPeriodRepository.save(currentTrialPeriod);
    }

    /**
     * Удаление полученного из бд испытательного срока
     *
     * @param trialPeriod Испытательный срок, который уже есть в бд
     */
    @Override
    public void delete(TrialPeriod trialPeriod) {
        if (trialPeriod != null && trialPeriod.getId() != null) {
            trialPeriodRepository.delete(trialPeriod);
        } else {
            throw new IllegalArgumentException("TrialPeriod or its ID cannot be null");
        }
    }

    /**
     * Удаление испытательного срока по id
     *
     * @param id id испытательного срока
     */
    @Override
    public void deleteById(Long id) {
        Optional<TrialPeriod> trialPeriodOptional = trialPeriodRepository.findById(id);

        if (trialPeriodOptional.isPresent()) {
            trialPeriodRepository.deleteById(id);
        } else {
            throw new NotFoundIdException("TrialPeriod with ID " + id + " not found");
        }
    }
}
