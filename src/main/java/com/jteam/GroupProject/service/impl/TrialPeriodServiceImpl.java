package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.listener.TelegramBotUpdatesListener;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.repository.TrialPeriodRepository;
import com.jteam.GroupProject.service.CatService;
import com.jteam.GroupProject.service.DogService;
import com.jteam.GroupProject.service.TrialPeriodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrialPeriodServiceImpl implements TrialPeriodService {
    private final TrialPeriodRepository trialPeriodRepository;
    private final CatService catService;
    private final DogService dogService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

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
    public TrialPeriod create(@Valid TrialPeriod trialPeriod, TrialPeriod.AnimalType animalType) {
        if (animalType.equals(TrialPeriod.AnimalType.CAT)) {
            catService.updateOwnerId(trialPeriod.getAnimalId(), trialPeriod.getOwnerId());
        } else if (animalType.equals(TrialPeriod.AnimalType.DOG)) {
            dogService.updateOwnerId(trialPeriod.getAnimalId(), trialPeriod.getOwnerId());
        }

        try {
            TrialPeriod savedTrialPeriod = trialPeriodRepository.save(trialPeriod);
            logger.info("TrialPeriod created: {}", savedTrialPeriod);
            return savedTrialPeriod;
        } catch (Exception e) {
            logger.error("Error creating TrialPeriod: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating TrialPeriod", e);
        }
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
        return optionalTrialPeriod.orElseThrow(() -> new NotFoundException("Испытательный срок не найден!"));
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
        try {
            Objects.requireNonNull(trialPeriod, "TrialPeriod cannot be null");
            Objects.requireNonNull(trialPeriod.getId(), "TrialPeriod ID cannot be null");

            trialPeriodRepository.delete(trialPeriod);
            logger.info("TrialPeriod deleted: {}", trialPeriod);
        } catch (DataAccessException e) {
            logger.error("Error deleting TrialPeriod", e);
            throw new IllegalStateException("Error deleting TrialPeriod", e);
        }
    }

    /**
     * Удаление испытательного срока по id
     *
     * @param id id испытательного срока
     */
    @Override
    public void deleteById(Long id) {
        try {
            Optional<TrialPeriod> trialPeriodOptional = trialPeriodRepository.findById(id);

            if (trialPeriodOptional.isPresent()) {
                trialPeriodRepository.deleteById(id);
                logger.info("TrialPeriod with ID {} deleted", id);
            } else {
                throw new NotFoundIdException("TrialPeriod with ID " + id + " not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting TrialPeriod by ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting TrialPeriod by ID " + id, e);
        }
    }

}
