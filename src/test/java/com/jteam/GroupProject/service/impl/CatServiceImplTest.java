package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.model.animal.Cat;
import com.jteam.GroupProject.repository.CatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CatServiceImplTest {
    @Mock
    private CatRepository catRepositoryMock;

    @InjectMocks
    private CatServiceImpl catService;
    private Long catId;

    @BeforeEach
    void setUp() {
        catId = 1L;
    }

    @Test
    void testGetById() {
        Cat cat = new Cat();

        when(catRepositoryMock.findById(catId)).thenReturn(Optional.of(cat));
        Cat retrievedCat = catService.getById(catId);

        assertNotNull(retrievedCat);
        assertEquals(cat, retrievedCat);
        verify(catRepositoryMock, times(1)).findById(catId);
    }

    @Test
    void testGetAllByUserId() {
        Long ownerId = 1L;
        List<Cat> cats = new ArrayList<>();

        when(catRepositoryMock.findAllByOwnerId(ownerId)).thenReturn(cats);

        List<Cat> retrievedCats = catService.getAllByUserId(ownerId);

        assertNotNull(retrievedCats);
        assertEquals(cats, retrievedCats);
        verify(catRepositoryMock, times(1)).findAllByOwnerId(ownerId);
    }

    @Test
    void testCreate() {
        Cat cat = new Cat();

        when(catRepositoryMock.save(cat)).thenReturn(cat);

        Cat createdCat = catService.create(cat);

        assertNotNull(createdCat);
        assertEquals(cat, createdCat);
        verify(catRepositoryMock, times(1)).save(cat);
    }

    @Test
    void testUpdate() {
        Cat existingCat = new Cat();

        when(catRepositoryMock.findById(existingCat.getId())).thenReturn(Optional.of(existingCat));
        when(catRepositoryMock.save(existingCat)).thenReturn(existingCat);

        Cat updatedCat = catService.update(existingCat);

        assertNotNull(updatedCat);
        assertEquals(existingCat, updatedCat);
        verify(catRepositoryMock, times(1)).save(existingCat);
    }

    @Test
    void testGetAll() {
        List<Cat> cats = new ArrayList<>();

        when(catRepositoryMock.findAll()).thenReturn(cats);

        List<Cat> retrievedCats = catService.getAll();

        assertNotNull(retrievedCats);
        assertEquals(cats, retrievedCats);
        verify(catRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Test successful removal of cat")
    void testRemove() {
        Cat cat = new Cat();

        when(catRepositoryMock.findById(catId)).thenReturn(Optional.of(cat));
        doNothing().when(catRepositoryMock).deleteById(catId);

        assertDoesNotThrow(() -> catService.remove(catId));

        verify(catRepositoryMock, times(1)).deleteById(catId);

    }
}