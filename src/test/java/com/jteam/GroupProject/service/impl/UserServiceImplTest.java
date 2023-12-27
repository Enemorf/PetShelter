package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl userService;
    private Long userId;
    @BeforeEach
    void setUp() {
        userId = 1L;
    }

    @Test
    void testCreate() {
        User userToSave = new User();
        when(userRepositoryMock.save(any(User.class))).thenReturn(userToSave);

        User savedUser = userService.create(userToSave);

        assertNotNull(savedUser);
        assertEquals(userToSave, savedUser);

        verify(userRepositoryMock, times(1)).save(userToSave);

    }

    @Test
    void testGetById() {
        Long userId = 1L;
        User expectedUser = new User();

        when(userRepositoryMock.findByTelegramId(userId)).thenReturn(Optional.of(expectedUser));

        User retrievedUser = userService.getById(userId);

        assertNotNull(retrievedUser);
        assertEquals(expectedUser, retrievedUser);
        verify(userRepositoryMock, times(1)).findByTelegramId(userId);
    }

    @Test
    void getShelterById() {
        User user = new User();
        user.setShelterType("Собачий приют");
        when(userRepositoryMock.findByTelegramId(userId)).thenReturn(Optional.of(user));

        // Act
        String retrievedUser = userService.getShelterById(userId);

        // Assert
        assertNotNull(retrievedUser);
        assertEquals(user.getShelterType(), retrievedUser);

        verify(userRepositoryMock, times(1)).findByTelegramId(userId);
    }

    @Test
    void testGetAll() {
        List<User> users = new ArrayList<>();
        when(userRepositoryMock.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.getAll();

        assertNotNull(retrievedUsers);
        assertEquals(users, retrievedUsers);
        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        User existingUser = new User();
        existingUser.setTelegramId(userId);
        when(userRepositoryMock.save(any(User.class))).thenReturn(existingUser);
        when(userRepositoryMock.findByTelegramId(anyLong())).thenReturn(Optional.of(existingUser));
        User updatedUser = userService.update(existingUser);

        assertNotNull(updatedUser);
        assertEquals(existingUser, updatedUser);
        verify(userRepositoryMock, times(1)).save(existingUser);
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setTelegramId(userId);

        when(userRepositoryMock.findByTelegramId(anyLong())).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.delete(user));
        verify(userRepositoryMock, times(1)).delete(user);
    }

}