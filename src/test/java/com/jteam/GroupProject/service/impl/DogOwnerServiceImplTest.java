package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.CatOwner;
import com.jteam.GroupProject.model.owners.DogOwner;
import com.jteam.GroupProject.repository.DogOwnerRepository;
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

    @InjectMocks
    private DogOwnerServiceImpl dogOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        DogOwner dogOwner = new DogOwner();
        when(dogOwnerRepositoryMock.save(dogOwner, TrialPeriod.AnimalType.CAT, 1L)).thenReturn(dogOwner);

        DogOwner createdDogOwner = dogOwnerService.create(dogOwner, TrialPeriod.AnimalType.CAT, 1L);

        assertNotNull(createdDogOwner);
        assertEquals(dogOwner, createdDogOwner);
        verify(dogOwnerRepositoryMock, times(1)).save(dogOwner, TrialPeriod.AnimalType.CAT, 1L);
    }

    @Test
    void testGetById() {
        Long ownerId = 1L;
        DogOwner dogOwner = new DogOwner();
        when(dogOwnerRepositoryMock.findById(ownerId)).thenReturn(Optional.of(dogOwner));

        DogOwner retrievedDogOwner = dogOwnerService.getById(ownerId);

        assertNotNull(retrievedDogOwner);
        assertEquals(dogOwner, retrievedDogOwner);
        verify(dogOwnerRepositoryMock, times(1)).findById(ownerId);
    }

    @Test
    void testGetAll() {
        List<DogOwner> dogOwners = new ArrayList<>();
        when(dogOwnerRepositoryMock.findAll()).thenReturn(dogOwners);

        List<DogOwner> retrievedDogOwners = dogOwnerService.getAll();

        assertNotNull(retrievedDogOwners);
        assertEquals(dogOwners, retrievedDogOwners);
        verify(dogOwnerRepositoryMock, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        DogOwner existingDogOwner = new DogOwner();
        when(dogOwnerRepositoryMock.findById(existingDogOwner.getId())).thenReturn(Optional.of(existingDogOwner));
        when(dogOwnerRepositoryMock.save(existingDogOwner)).thenReturn(existingDogOwner);

        DogOwner updatedDogOwner = dogOwnerService.update(existingDogOwner);

        assertNotNull(updatedDogOwner);
        assertEquals(existingDogOwner, updatedDogOwner);
        verify(dogOwnerRepositoryMock, times(1)).save(existingDogOwner);
    }

    @Test
    void testDelete() {
        DogOwner dogOwner = new DogOwner();

        assertDoesNotThrow(() -> dogOwnerService.delete(dogOwner));
        verify(dogOwnerRepositoryMock, times(1)).delete(dogOwner);
    }

    @Test
    @DisplayName("Test get existing dog owner by id")
    void testGetExistingDogOwnerById() {
        Long dogOwnerId = 1L;
        DogOwner expectedDogOwner = new DogOwner();
        when(dogOwnerRepositoryMock.findById(dogOwnerId)).thenReturn(Optional.of(expectedDogOwner));

        DogOwner retrievedDogOwner = dogOwnerService.getById(dogOwnerId);

        assertNotNull(retrievedDogOwner);
        assertEquals(expectedDogOwner, retrievedDogOwner);
        verify(dogOwnerRepositoryMock, times(1)).findById(dogOwnerId);
    }

    @Test
    @DisplayName("Test get non-existing dog owner by id")
    void testGetNonExistingDogOwnerById() {
        Long dogOwnerId = 1L;
        when(dogOwnerRepositoryMock.findById(dogOwnerId)).thenReturn(Optional.empty());

        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> dogOwnerService.getById(dogOwnerId));
        assertEquals("Dog owner not found with id: " + dogOwnerId, exception.getMessage());

        verify(dogOwnerRepositoryMock, times(1)).findById(dogOwnerId);
    }
}