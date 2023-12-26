package com.jteam.GroupProject.service.impl;


import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.repository.UserRepository;
import com.jteam.GroupProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Создание и сохранение пользователя в бд
     *
     * @param user Пользователь для сохранения в бд
     * @return Сохранённый пользователь
     */
    @Override
    public User create(User user) {

        User savedUser = userRepository.save(user);
        logger.info("User created: {}", savedUser);
        return savedUser;
    }

    /**
     * Получение пользователя по id
     *
     * @param id Id пользователя
     * @return Полученный из бд пользователь
     */
    @Override
    public User getById(Long id) {
        return userRepository.findByTelegramId(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

    }

    /**
     * Получение выбранного в боте приюта по id пользователя
     *
     * @param id Id пользователя
     * @return "CAT" или "DOG"
     */
    @Override
    public String getShelterById(Long id) {
        return getById(id).getShelterType();
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
        User currentUser = getById(user.getTelegramId());
        EntityUtils.copyNonNullFields(user, currentUser);
        User updatedUser = userRepository.save(currentUser);
        logger.info("User updated: {}", updatedUser);
        return updatedUser;
    }

    /**
     * @param user Пользователь, который уже есть в бд
     */
    @Override
    public void delete(User user) {

        userRepository.delete(getById(user.getTelegramId()));
        logger.info("User deleted: {}", user);
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
            logger.info("User deleted by ID: {}", id);
        } else {
            logger.warn("User with ID {} not found, delete operation skipped.", id);
        }
    }
}
