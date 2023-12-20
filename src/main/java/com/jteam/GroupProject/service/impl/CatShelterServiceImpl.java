package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.model.animal.Cat;
import com.jteam.GroupProject.model.shelters.CatShelter;
import com.jteam.GroupProject.repository.CatShelterRepository;
import com.jteam.GroupProject.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatShelterServiceImpl implements ShelterService<CatShelter, Cat> {
    private final CatShelterRepository catRepository;

    /**
     * Сохранить приют в БД
     *
     * @param shelter объект приют
     * @return сохранение приюта в БД
     */
    @Override
    public CatShelter addShelter(CatShelter shelter) {
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
        Optional<CatShelter> shelterId = catRepository.findById(id);
        if (shelterId.isEmpty()) {
            throw new NotFoundException("Приют не найден. Кошки остались без дома");
        }
        return shelterId.get();
    }

    /**
     * @param name
     * @return
     */
    @Override
    public CatShelter getShelterByName(String name) {
        Optional<CatShelter> shelterId = catRepository.findByName(name);
        if (shelterId.isEmpty()) {
            throw new NotFoundException("Приют не найден. Кошки остались без дома");
        }
        return shelterId.get();
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
    public String delShelter(long index) {
        String result;
        Optional<CatShelter> catShelter = catRepository.findById(index);
        if (catShelter.isPresent()) {
            catRepository.deleteById(index);
            result = "Запись удалена";
        } else {
            throw new NotFoundException("Котятки остались без приюта. Не нашли приют");
        }
        return result;
    }
}
