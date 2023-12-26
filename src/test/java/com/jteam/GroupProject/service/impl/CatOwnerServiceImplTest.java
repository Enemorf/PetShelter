package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.AlreadyExistsException;
import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.animal.Cat;
import com.jteam.GroupProject.model.owners.CatOwner;
import com.jteam.GroupProject.repository.CatOwnerRepository;
import com.jteam.GroupProject.service.CatService;
import com.jteam.GroupProject.service.TrialPeriodService;
import com.jteam.GroupProject.service.UserService;
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

class CatOwnerServiceImplTest {
    @Mock
    private CatOwnerRepository catOwnerRepositoryMock;
    @Mock
    private UserService userServiceMock;

    @Mock
    private CatService catServiceMock;

    @Mock
    private TrialPeriodService trialPeriodServiceMock;


    @InjectMocks
    private CatOwnerServiceImpl catOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testCreate() {
        // Arrange
        Long animalId = 1L;
        CatOwner catOwner = new CatOwner();

        when(catServiceMock.getById(animalId)).thenReturn(new Cat());
        when(trialPeriodServiceMock.create(any(), any())).thenReturn(new TrialPeriod());
        when(catOwnerRepositoryMock.save(catOwner)).thenReturn(catOwner);

        // Act
        CatOwner createdCatOwner = catOwnerService.create(catOwner, TrialPeriod.AnimalType.CAT, animalId);

        // Assert
        verify(trialPeriodServiceMock).create(any(), any());
        verify(catServiceMock, times(2)).getById(animalId);
        verify(catServiceMock).update(any());
        verify(catOwnerRepositoryMock).save(catOwner);

        assertSame(catOwner, createdCatOwner);
    }

    @Test
    void testGetById() {
        // Arrange
        Long ownerId = 1L;
        CatOwner catOwner = new CatOwner();
        when(catOwnerRepositoryMock.findById(ownerId)).thenReturn(Optional.of(catOwner));

        // Act
        CatOwner retrievedCatOwner = catOwnerService.getById(ownerId);

        // Assert
        assertNotNull(retrievedCatOwner);
        assertEquals(catOwner, retrievedCatOwner);
        verify(catOwnerRepositoryMock, times(1)).findById(ownerId);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<CatOwner> catOwners = new ArrayList<>();
        when(catOwnerRepositoryMock.findAll()).thenReturn(catOwners);

        // Act
        List<CatOwner> retrievedCatOwners = catOwnerService.getAll();

        // Assert
        assertNotNull(retrievedCatOwners);
        assertEquals(catOwners, retrievedCatOwners);
        verify(catOwnerRepositoryMock, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        // Arrange
        CatOwner existingCatOwner = new CatOwner();
        when(catOwnerRepositoryMock.findById(existingCatOwner.getId())).thenReturn(Optional.of(existingCatOwner));
        when(catOwnerRepositoryMock.save(existingCatOwner)).thenReturn(existingCatOwner);

        // Act
        CatOwner updatedCatOwner = catOwnerService.update(existingCatOwner);

        // Assert
        assertNotNull(updatedCatOwner);
        assertEquals(existingCatOwner, updatedCatOwner);
        verify(catOwnerRepositoryMock, times(1)).save(existingCatOwner);
    }

    @Test
    void testDelete() {
        // Arrange
        CatOwner catOwner = new CatOwner();

        // Act/Assert
        assertDoesNotThrow(() -> catOwnerService.delete(catOwner));
        verify(catOwnerRepositoryMock, times(1)).delete(catOwner);
    }
    @Test
    @DisplayName("Test successful removal of cat owner by id")
    void testRemoveCatOwnerById() {
        Long catOwnerId = 1L;
        when(catOwnerRepositoryMock.existsById(catOwnerId)).thenReturn(true);

        assertDoesNotThrow(() -> catOwnerService.deleteById(catOwnerId));
        verify(catOwnerRepositoryMock, times(1)).deleteById(catOwnerId);
    }

    @Test
    @DisplayName("Test removal attempt of non-existing cat owner by id")
    void testRemoveNonExistingCatOwnerById() {
        Long catOwnerId = 1L;
        when(catOwnerRepositoryMock.existsById(catOwnerId)).thenReturn(false);

        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> catOwnerService.deleteById(catOwnerId));
        assertEquals("Cat owner not found with id: " + catOwnerId, exception.getMessage());

        verify(catOwnerRepositoryMock, never()).deleteById(catOwnerId);
    }
}