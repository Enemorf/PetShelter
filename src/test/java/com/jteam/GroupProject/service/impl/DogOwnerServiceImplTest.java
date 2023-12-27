package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.AlreadyExistsException;
import com.jteam.GroupProject.exceptions.NotFoundException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.animal.Dog;
import com.jteam.GroupProject.model.owners.DogOwner;
import com.jteam.GroupProject.repository.DogOwnerRepository;
import com.jteam.GroupProject.service.DogService;
import com.jteam.GroupProject.service.TrialPeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DogOwnerServiceImplTest {
    @Mock
    private DogOwnerRepository dogOwnerRepositoryMock;
    @Mock
    private TrialPeriodService trialPeriodServiceMock;
    @Mock
    private DogService dogServiceMock;

    @InjectMocks
    private DogOwnerServiceImpl dogOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Arrange
        Long telegramId = 123L;
        Long animalId = 456L;

        DogOwner dogOwner = new DogOwner();
        dogOwner.setTelegramId(telegramId);

        when(dogServiceMock.getById(animalId)).thenReturn(new Dog());
        when(dogOwnerRepositoryMock.save(dogOwner)).thenReturn(dogOwner);

        // Act
        DogOwner createdDogOwner = dogOwnerService.create(dogOwner, TrialPeriod.AnimalType.DOG, animalId);

        // Assert
        assertNotNull(createdDogOwner);
        assertEquals(dogOwner, createdDogOwner);
        verify(trialPeriodServiceMock, times(1)).create(any(), eq(TrialPeriod.AnimalType.DOG));
        verify(dogServiceMock, times(2)).getById(animalId);
        //verify(dogServiceMock, times(1)).updateOwnerId(animalId, telegramId);
        verify(dogOwnerRepositoryMock, times(1)).save(dogOwner);
    }

    @Test
    void testCreateDogWithExistingOwner() {
        // Arrange
        Long telegramId = 123L;
        Long animalId = 456L;

        DogOwner dogOwner = new DogOwner();
        dogOwner.setTelegramId(telegramId);
        Dog dog = new Dog();
        dog.setOwnerId(telegramId);

        when(dogServiceMock.getById(animalId)).thenReturn(dog);

        // Act and Assert
        assertThrows(AlreadyExistsException.class, () -> dogOwnerService.create(dogOwner, TrialPeriod.AnimalType.DOG, animalId));

        // Verify
        verify(dogServiceMock, times(1)).getById(animalId);
        verifyNoInteractions(trialPeriodServiceMock, dogOwnerRepositoryMock);
    }

    @Test
    void testGetById() {
        Long ownerId = 1L;
        DogOwner dogOwner = new DogOwner();
        when(dogOwnerRepositoryMock.findByTelegramId(ownerId)).thenReturn(Optional.of(dogOwner));

        DogOwner retrievedDogOwner = dogOwnerService.getById(ownerId);

        assertNotNull(retrievedDogOwner);
        assertEquals(dogOwner, retrievedDogOwner);
        verify(dogOwnerRepositoryMock, times(1)).findByTelegramId(ownerId);
    }

    @Test
    void testGetAll() {
        List<DogOwner> dogOwners = new ArrayList<>();
        dogOwners.add(new DogOwner());
        when(dogOwnerRepositoryMock.findAll()).thenReturn(dogOwners);

        List<DogOwner> retrievedDogOwners = dogOwnerService.getAll();

        assertNotNull(retrievedDogOwners);
        assertEquals(dogOwners, retrievedDogOwners);
        verify(dogOwnerRepositoryMock, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        DogOwner existingDogOwner = new DogOwner();
        when(dogOwnerRepositoryMock.findByTelegramId(existingDogOwner.getId())).thenReturn(Optional.of(existingDogOwner));
        when(dogOwnerRepositoryMock.save(existingDogOwner)).thenReturn(existingDogOwner);

        DogOwner updatedDogOwner = dogOwnerService.update(existingDogOwner);

        assertNotNull(updatedDogOwner);
        assertEquals(existingDogOwner, updatedDogOwner);
        verify(dogOwnerRepositoryMock, times(1)).save(existingDogOwner);
    }

    @Test
    void testDelete() {
        DogOwner dogOwner = new DogOwner();
        Long dogOwnerId = 1L;
        dogOwner.setTelegramId(dogOwnerId);

        when(dogOwnerRepositoryMock.findByTelegramId(anyLong())).thenReturn(Optional.of(dogOwner));
        doNothing().when(dogOwnerRepositoryMock).deleteById(anyLong());

        assertDoesNotThrow(() -> dogOwnerService.delete(dogOwner));
        verify(dogOwnerRepositoryMock, times(1)).delete(dogOwner);
    }

    @Test
    @DisplayName("Test get existing dog owner by id")
    void testGetExistingDogOwnerById() {
        Long dogOwnerId = 1L;
        DogOwner expectedDogOwner = new DogOwner();
        when(dogOwnerRepositoryMock.findByTelegramId(dogOwnerId)).thenReturn(Optional.of(expectedDogOwner));

        DogOwner retrievedDogOwner = dogOwnerService.getById(dogOwnerId);

        assertNotNull(retrievedDogOwner);
        assertEquals(expectedDogOwner, retrievedDogOwner);
        verify(dogOwnerRepositoryMock, times(1)).findByTelegramId(dogOwnerId);
    }

    @Test
    @DisplayName("Test get non-existing dog owner by id")
    void testGetNonExistingDogOwnerById() {
        Long dogOwnerId = 1L;
        when(dogOwnerRepositoryMock.findByTelegramId(dogOwnerId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> dogOwnerService.getById(dogOwnerId));
        assertEquals("Хозяин собаки не найден!", exception.getMessage());

        verify(dogOwnerRepositoryMock, times(1)).findByTelegramId(dogOwnerId);
    }
}