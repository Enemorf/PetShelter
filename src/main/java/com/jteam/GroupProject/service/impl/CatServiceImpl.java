package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.animal.Cat;
import com.jteam.GroupProject.repository.CatRepository;
import com.jteam.GroupProject.service.CatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }
    /**
     * Возвращает объект кота по его идентификатору.
     *
     * @param id идентификатор кота в базе данных
     * @return объект кота с указанным идентификатором
     */
    @Override
    public Cat getById(Long id) {
        Optional<Cat> cat = catRepository.findById(id);
        try {
            return cat.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Возвращает объект кота, принадлежащего хозяину с указанным идентификатором.
     *
     * @param id идентификатор хозяина кота в базе данных
     * @return список котов указанного хозяина
     */
    @Override
    public List<Cat> getAllByUserId(Long id) {
        return catRepository.findAllByOwnerId(id);
    }

    /**
     * Создает новую запись о коте в базе данных, используя переданный объект кота.
     *
     * @param cat объект кота, содержащий информацию о создаваемой записи
     * @return созданный объект кота с присвоенным идентификатором, сохраненный в базе данных
     */
    @Override
    public Cat create(Cat cat) {
        return catRepository.save(cat);
    }

    /**
     * Обновляет информацию о коте, используя переданный объект кота.
     *
     * @param cat объект кота, содержащий обновленную информацию
     * @return объект кота с обновленной информацией, сохраненный в базе данных
     */
    @Override
    public Cat update(Cat cat) {
        Optional<Cat> cat2 = catRepository.findById(cat.getId());
        try {
            catRepository.save(cat);
            return cat2.orElseThrow(NotFoundIdException::new);
        } catch (NotFoundIdException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает коллекцию всех объектов кота, находящихся в базе данных.
     *
     * @return коллекция всех объектов кота, находящихся в базе данных
     */
    @Override
    public List<Cat> getAll() {
        return catRepository.findAll();
    }

    /**
     * Удаляет запись о коте с указанным идентификатором из базы данных.
     *
     * @param id идентификатор кота, который должен быть удален
     */
    @Override
    public void remove(Long id) {
        Optional<Cat> cat = catRepository.findById(id);
        if (cat.isPresent()) {
            catRepository.deleteById(id);
        } else {
            throw new NotFoundIdException("Cat not found with id: " + id);
        }
    }
}
