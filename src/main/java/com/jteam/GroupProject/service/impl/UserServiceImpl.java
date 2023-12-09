package com.jteam.GroupProject.service.impl;


import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.repository.UserRepository;
import com.jteam.GroupProject.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создание и сохранение пользователя в бд
     *
     * @param user Пользователь для сохранения в бд
     * @return Сохранённый пользователь
     */
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * Получение пользователя по id
     *
     * @param id Id пользователя
     * @return Полученный из бд пользователь
     */
    @Override
    public User getById(Long id) {
        return userRepository.findAllById(id);
    }

    /**
     * Получение выбранного в боте приюта по id пользователя
     *
     * @param id Id пользователя
     * @return "CAT" или "DOG"
     */
    @Override
    public User getShelterById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException("User not found with id: " + id));
    }

    /**
     * @return Список всех пользователей
     */
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Изменение пользователя
     *
     * @param user пользователь
     * @return Изменённый пользователь
     */
    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    /**
     * @param user Пользователь, который уже есть в бд
     */
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * Удаление пользователя по id
     *
     * @param id Id пользователя
     */
    @Override
    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }
}
