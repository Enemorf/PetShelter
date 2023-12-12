package com.jteam.GroupProject.service.impl;


import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.animal.Animal;
import com.jteam.GroupProject.repository.ShelterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ShelterServiceImplTest {
    @Mock
    private ShelterRepository shelterRepositoryMock;

    @InjectMocks
    private ShelterServiceImpl shelterService;
    private Long shelterId;
    @BeforeEach
    void setUp() {

        shelterId = 1L;
    }


    @Test
    @DisplayName("Test adding shelter")
    void testAddShelter() {
        Object shelter = new Object();

        when(shelterRepositoryMock.save(shelter)).thenReturn(shelter);

        Object result = shelterService.addShelter(shelter);

        assertNotNull(result);
        assertEquals(shelter, result);
        verify(shelterRepositoryMock, times(1)).save(shelter);
    }

    @Test
    void testUpdateShelter() {
        Object shelter = new Object();
        when(shelterRepositoryMock.save(shelter)).thenReturn(shelter);

        Object result = shelterService.updateShelter(shelter);

        assertNotNull(result);
        assertEquals(shelter, result);
        verify(shelterRepositoryMock, times(1)).save(shelter);
    }

    @Test
    void testGetSheltersId() {
        Object shelter = new Object();

        when(shelterRepositoryMock.findById(shelterId)).thenReturn(Optional.of(shelter));

        Object result = shelterService.getSheltersId(shelterId);

        assertNotNull(result);
        assertEquals(shelter, ((Optional<?>) result).get()); // использование get() для извлечения значения
        verify(shelterRepositoryMock, times(1)).findById(shelterId);
    }

    @Test
    void testGetShelterByName() {
        String shelterName = "Example Shelter";
        Object shelter = new Object();

        when(shelterRepositoryMock.getShelterByName(shelterName)).thenReturn(shelter);

        Object result = shelterService.getShelterByName(shelterName);

        assertNotNull(result);
        assertEquals(shelter, result);
        verify(shelterRepositoryMock, times(1)).getShelterByName(shelterName);

    }

    @Test
    void testGetShelter() {
        List<Object> shelters = Arrays.asList(new Object(), new Object());

        when(shelterRepositoryMock.findAll()).thenReturn(shelters);

        List<Object> result = shelterService.getShelter();

        assertNotNull(result);
        assertEquals(shelters, result);
        verify(shelterRepositoryMock, times(1)).findAll();
    }

    @Test
    void getAnimal_ShouldReturnListOfAnimals_WhenValidShelterIndex() {

        when(shelterRepositoryMock.findAllById(shelterId)).thenReturn(Collections.emptyList());

        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> {
            shelterService.getAnimal(shelterId);
        });

        assertEquals("No animals found for shelter with index: 1", exception.getMessage());
        verify(shelterRepositoryMock, times(1)).findAllById(shelterId);
    }

    @Test
    void getAnimal_ShouldThrowNotFoundIdException_WhenInvalidShelterIndex() {

        when(shelterRepositoryMock.findAllById(shelterId)).thenReturn(Collections.emptyList());

        // Act and Assert
        assertThrows(NotFoundIdException.class, () -> shelterService.getAnimal(shelterId));
        verify(shelterRepositoryMock, times(1)).findAllById(shelterId);
    }

    @Test
    void testDelShelter() {
        when(shelterRepositoryMock.existsById(shelterId)).thenReturn(false);

        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> shelterService.delShelter(shelterId));
        assertEquals("Shelter not found with index: " + shelterId, exception.getMessage());

        verify(shelterRepositoryMock, never()).deleteById(shelterId);
    }
}