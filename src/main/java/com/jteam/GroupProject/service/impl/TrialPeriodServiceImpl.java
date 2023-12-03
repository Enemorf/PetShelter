package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.repository.TrialPeriodRepository;
import com.jteam.GroupProject.service.TrialPeriodService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrialPeriodServiceImpl implements TrialPeriodService {
    private final TrialPeriodRepository trialPeriodRepository;

    public TrialPeriodServiceImpl(TrialPeriodRepository trialPeriodRepository) {
        System.out.println();
        this.trialPeriodRepository = trialPeriodRepository;
    }
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
        return trialPeriodRepository.save(trialPeriod, animalType);
    }

    /**
     * Получение испытательного срока из бд по id
     *
     * @param id id испытательного срока
     * @return Испытательный срок
     */
    @Override
    public TrialPeriod getById(Long id) {
        Optional<TrialPeriod> trialPeriod = trialPeriodRepository.findById(id);
        try {
            return trialPeriod.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение всех отчётов
     *
     * @return Список всех отчётов
     */
    @Override
    public List<TrialPeriod> getAll() {
        return trialPeriodRepository.findAll();
    }

    /**
     * Получение всех отчётов конкретного пользователя
     *
     * @param ownerId id хозяина, по которому будет идти поиск
     * @return Список испытательных сроков по хозяину
     */
    @Override
    public List<TrialPeriod> getAllByOwnerId(Long ownerId) {
        return trialPeriodRepository.findAllByOwnerId(ownerId);
    }

    /**
     * Изменение существующего испытательного срока
     *
     * @param trialPeriod Испытательный срок
     * @return Обновлённый испытательный срок
     */
    @Override
    public TrialPeriod update(TrialPeriod trialPeriod) {
        Optional<TrialPeriod> trialPeriod2 = trialPeriodRepository.findById(trialPeriod.getId());
        try {
            trialPeriodRepository.save(trialPeriod);
            return trialPeriod2.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление полученного из бд испытательного срока
     *
     * @param trialPeriod Испытательный срок, который уже есть в бд
     */
    @Override
    public void delete(TrialPeriod trialPeriod) {
        trialPeriodRepository.delete(trialPeriod);
    }

    /**
     * Удаление испытательного срока по id
     *
     * @param id id испытательного срока
     */
    @Override
    public void deleteById(Long id) {
        Optional<TrialPeriod> trialPeriod = trialPeriodRepository.findById(id);
        try {
            trialPeriodRepository.deleteById(id);
            throw new NotFoundIdException();
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }
}
