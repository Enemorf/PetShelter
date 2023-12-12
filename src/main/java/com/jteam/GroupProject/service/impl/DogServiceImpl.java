package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.repository.DogRepository;
import com.jteam.GroupProject.service.DogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;
    public DogServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }
    /**
     * Возвращает объект собаки по его идентификатору.
     *
     * @param id идентификатор собаки в базе данных
     * @return объект собаки с указанным идентификатором
     */
    @Override
    public Dog getById(Long id) {
        return dogRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException("Dog not found with id: " + id));
    }

    /**
     * Возвращает объект собаки, принадлежащей хозяину с указанным идентификатором.
     *
     * @param id идентификатор хозяина собаки в базе данных
     * @return собаки принадлежащие к указанному хозяину
     */
    @Override
    public List<Dog> getAllByUserId(Long id) {
        return dogRepository.findAllByOwnerId(id);
    }

    /**
     * Создает новую запись о собаке в базе данных, используя переданный объект собаки.
     *
     * @param dog объект собаки, содержащий информацию о создаваемой записи
     * @return созданный объект собаки с присвоенным идентификатором, сохраненный в базе данных
     */
    @Override
    public Dog create(Dog dog) {
        return dogRepository.save(dog);
    }

    /**
     * Обновляет информацию о собаке, используя переданный объект собаки.
     *
     * @param dog объект собаки, содержащий обновленную информацию
     * @return объект собаки с обновленной информацией, сохраненный в базе данных
     */
    @Override
    public Dog update(Dog dog) {
        if (!dogRepository.existsById(dog.getId())) {
            throw new NotFoundIdException("Dog not found with id: " + dog.getId());
        }
        try {
            return dogRepository.save(dog);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Dog", e);
        }
    }

    /**
     * Возвращает коллекцию всех объектов собаки, находящихся в базе данных.
     *
     * @return коллекция всех объектов собаки, находящихся в базе данных
     */
    @Override
    public List<Dog> getAll() {
        return dogRepository.findAll();
    }

    /**
     * Удаляет запись о собаке с указанным идентификатором из базы данных.
     *
     * @param id идентификатор собаки, которую нужно удалить
     */
    @Override
    public void remove(Long id) {
        Optional<Dog> dog = dogRepository.findById(id);
        if (dog.isPresent()) {
            dogRepository.deleteById(id);
        } else {
            throw new NotFoundIdException("Dog not found with id: " + id);
        }
    }
}
