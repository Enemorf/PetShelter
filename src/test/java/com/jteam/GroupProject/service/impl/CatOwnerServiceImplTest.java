package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.owners.CatOwner;
import com.jteam.GroupProject.repository.CatOwnerRepository;
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

    @InjectMocks
    private CatOwnerServiceImpl catOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        CatOwner catOwner = new CatOwner();
        when(catOwnerRepositoryMock.save(catOwner, TrialPeriod.AnimalType.CAT, 1L)).thenReturn(catOwner);

        CatOwner createdCatOwner = catOwnerService.create(catOwner, TrialPeriod.AnimalType.CAT, 1L);

        assertNotNull(createdCatOwner);
        assertEquals(catOwner, createdCatOwner);
        verify(catOwnerRepositoryMock, times(1)).save(catOwner, TrialPeriod.AnimalType.CAT, 1L);
    }

    @Test
    void testGetById() {
        Long ownerId = 1L;
        CatOwner catOwner = new CatOwner();
        when(catOwnerRepositoryMock.findById(ownerId)).thenReturn(Optional.of(catOwner));

        CatOwner retrievedCatOwner = catOwnerService.getById(ownerId);

        assertNotNull(retrievedCatOwner);
        assertEquals(catOwner, retrievedCatOwner);
        verify(catOwnerRepositoryMock, times(1)).findById(ownerId);
    }

    @Test
    void testGetAll() {
        List<CatOwner> catOwners = new ArrayList<>();
        when(catOwnerRepositoryMock.findAll()).thenReturn(catOwners);

        List<CatOwner> retrievedCatOwners = catOwnerService.getAll();

        assertNotNull(retrievedCatOwners);
        assertEquals(catOwners, retrievedCatOwners);
        verify(catOwnerRepositoryMock, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        CatOwner existingCatOwner = new CatOwner();
        when(catOwnerRepositoryMock.findById(existingCatOwner.getId())).thenReturn(Optional.of(existingCatOwner));
        when(catOwnerRepositoryMock.save(existingCatOwner)).thenReturn(existingCatOwner);

        CatOwner updatedCatOwner = catOwnerService.update(existingCatOwner);

        assertNotNull(updatedCatOwner);
        assertEquals(existingCatOwner, updatedCatOwner);
        verify(catOwnerRepositoryMock, times(1)).save(existingCatOwner);
    }

    @Test
    void testDelete() {
        CatOwner catOwner = new CatOwner();

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