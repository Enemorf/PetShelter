package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.model.animal.Cat;
import com.jteam.GroupProject.model.shelters.CatShelter;
import com.jteam.GroupProject.repository.CatShelterRepository;
import com.jteam.GroupProject.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatShelterServiceImpl implements ShelterService<CatShelter, Cat> {
    private final CatShelterRepository catRepository;
    private static final Logger logger = LoggerFactory.getLogger(CatShelterServiceImpl.class);

    /**
     * Сохранить приют в БД
     *
     * @param shelter объект приют
     * @return сохранение приюта в БД
     */
    @Override
    public CatShelter addShelter(CatShelter shelter) {
        logger.info("Adding shelter: {}", shelter);
        return catRepository.save(shelter);
    }

    /**
     * Обновление данных приюта
     *
     * @param catShelter объект приют
     * @return shelter объект приют
     */
    @Override
    public CatShelter updateShelter(CatShelter catShelter) {
        logger.info("Updating shelter with id {}: {}", catShelter.getId(), catShelter);
        CatShelter currentShelter = getSheltersId(catShelter.getId());
        EntityUtils.copyNonNullFields(catShelter, currentShelter);
        return catRepository.save(currentShelter);
    }

    /**
     * Получение приюта по id
     *
     * @param id приюта
     * @return приют
     */
    @Override
    public CatShelter getSheltersId(long id) {
        return catRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Приют не найден. Кошки остались без дома"));
    }

    /**
     * @param name
     * @return
     */
    @Override
    public CatShelter getShelterByName(String name) {
        return catRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Приют не найден. Кошки остались без дома"));
    }

    /**
     * Выдача списка приютов
     *
     * @return List со списком приютов
     */
    @Override
    public List<CatShelter> getShelter() {
        return catRepository.findAll();
    }

    /**
     * Выдача списка животных приютов
     *
     * @param index индекс приюта
     * @return List со списком приютов
     */
    @Override
    public List<Cat> getAnimal(long index) {
        return getSheltersId(index).getList();
    }

    /**
     * Удаление приюта
     *
     * @param index номер
     */
    @Override
    public void delShelter(long index) {
        Optional<CatShelter> catShelter = catRepository.findById(index);
        if (catShelter.isPresent()) {
            catRepository.deleteById(index);
            logger.info("Shelter with id {} deleted", index);
        } else {
            logger.warn("Attempt to delete non-existing shelter with id: {}", index);
            throw new NotFoundException("Котятки остались без приюта. Не нашли приют");
        }
    }
}
