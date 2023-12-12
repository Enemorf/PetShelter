package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.repository.DogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class DogServiceImplTest {
    @Mock
    private DogRepository dogRepositoryMock;

    @InjectMocks
    private DogServiceImpl dogService;
    private Long dogId;
    @BeforeEach
    void setUp() {
        dogId = 1L;
    }

    @Test
    void testGetById() {
        Dog dog = new Dog();

        when(dogRepositoryMock.findById(dogId)).thenReturn(Optional.of(dog));
        Dog retrievedDog = dogService.getById(dogId);

        assertNotNull(retrievedDog);
        assertEquals(dog, retrievedDog);
        verify(dogRepositoryMock, times(1)).findById(dogId);
    }

    @Test
    void testGetAllByUserId() {
        Long ownerId = 1L;
        List<Dog> dogs = new ArrayList<>();

        when(dogRepositoryMock.findAllByOwnerId(ownerId)).thenReturn(dogs);

        List<Dog> retrievedDogs = dogService.getAllByUserId(ownerId);

        assertNotNull(retrievedDogs);
        assertEquals(dogs, retrievedDogs);
        verify(dogRepositoryMock, times(1)).findAllByOwnerId(dogId);
    }

    @Test
    void testCreate() {
        Dog dog = new Dog();

        when(dogRepositoryMock.save(dog)).thenReturn(dog);
        Dog createdDog = dogService.create(dog);

        assertNotNull(createdDog);
        assertEquals(dog, createdDog);
        verify(dogRepositoryMock, times(1)).save(dog);

    }


    @Test
    void testUpdate() {
        Dog existingDog = new Dog();

        when(dogRepositoryMock.existsById(existingDog.getId())).thenReturn(true);
        when(dogRepositoryMock.save(existingDog)).thenReturn(existingDog);

        Dog updatedDog = dogService.update(existingDog);

        assertNotNull(updatedDog);
        assertEquals(existingDog, updatedDog);
        verify(dogRepositoryMock, times(1)).save(existingDog);
    }

    @Test
    void testGetAll() {
        List<Dog> dogs = new ArrayList<>();

        when(dogRepositoryMock.findAll()).thenReturn(dogs);

        List<Dog> retrievedDogs = dogService.getAll();

        assertNotNull(retrievedDogs);
        assertEquals(dogs, retrievedDogs);
        verify(dogRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Test successful removal of dog")
    void testRemove() {
        Dog dog = new Dog();

        when(dogRepositoryMock.findById(dogId)).thenReturn(Optional.of(dog));
        doNothing().when(dogRepositoryMock).deleteById(dogId);

        assertDoesNotThrow(() -> dogService.remove(dogId));

        verify(dogRepositoryMock, times(1)).deleteById(dogId);
    }
    @Test
    @DisplayName("Test removal of non-existing dog")
    void testRemoveNonExistingDog() {
        when(dogRepositoryMock.findById(dogId)).thenReturn(Optional.empty());

        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> dogService.remove(dogId));
        assertEquals("Dog not found with id: " + dogId, exception.getMessage());

        verify(dogRepositoryMock, times(0)).deleteById(dogId);
    }
}