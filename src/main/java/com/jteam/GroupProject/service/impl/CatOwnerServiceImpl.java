package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.AlreadyExistsException;
import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.CatOwner;
import com.jteam.GroupProject.repository.CatOwnerRepository;
import com.jteam.GroupProject.service.CatOwnerService;
import com.jteam.GroupProject.service.CatService;
import com.jteam.GroupProject.service.TrialPeriodService;
import com.jteam.GroupProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatOwnerServiceImpl implements CatOwnerService {
    private final CatOwnerRepository catOwnerRepository;
    private final UserService userService;
    private final CatService catService;
    private final TrialPeriodService trialPeriodService;

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
        if(catService.getById(animalId).getOwnerId() != null){
            throw new AlreadyExistsException("У этого кота уже есть хозяин!");
        }
        trialPeriodService.create(new TrialPeriod(LocalDate.now(), LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(1), new ArrayList<>(),
                TrialPeriod.Result.IN_PROGRESS, catOwner.getTelegramId(), animalType, animalId), animalType);
        catService.getById(animalId).setOwnerId(catOwner.getTelegramId());
        return catOwnerRepository.save(catOwner);
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
        if (catService.getById(animalId).getOwnerId() != null) {
            throw new AlreadyExistsException("У этого кота уже есть хозяин!");
        }
        CatOwner catOwner = new CatOwner(userService.getById(id));
        trialPeriodService.create(new TrialPeriod(LocalDate.now(), LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(1), new ArrayList<>(),
                TrialPeriod.Result.IN_PROGRESS, id, animalType, animalId), animalType);
        catService.getById(animalId).setOwnerId(id);
        return catOwnerRepository.save(catOwner);
    }

    /**
     * Получение хозяина кота по id
     *
     * @param id Id хозяина кота
     * @return Полученный из бд хозяин кота
     */
    @Override
    public CatOwner getById(Long id) {
        return catOwnerRepository.findByTelegramId(id)
                .orElseThrow(() -> new NotFoundException("Хозяин кота не найден!"));
    }

    /**
     * @return Список всех владельцев котов
     */
    @Override
    public List<CatOwner> getAll() {
        List<CatOwner> all = catOwnerRepository.findAll();
        if (all.isEmpty()) {
            throw new NotFoundException("Владельцев котов нет!");
        }
        return all;
    }

    /**
     * Изменение хозяина кота
     *
     * @param catOwner хозяин кота
     * @return Изменённый хозяин кота
     */
    @Override
    public CatOwner update(CatOwner catOwner) {
        CatOwner currentCatOwner = getById(catOwner.getTelegramId());
        EntityUtils.copyNonNullFields(catOwner, currentCatOwner);
        return catOwnerRepository.save(currentCatOwner);
    }

    /**
     * @param catOwner хозяин кота, который уже есть в бд
     */
    @Override
    public void delete(CatOwner catOwner) {
        catService.getAllByUserId(catOwner.getTelegramId()).forEach(cat -> {
            cat.setOwnerId(null);
            catService.update(cat);
        });
        catOwnerRepository.delete(getById(catOwner.getTelegramId()));
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
