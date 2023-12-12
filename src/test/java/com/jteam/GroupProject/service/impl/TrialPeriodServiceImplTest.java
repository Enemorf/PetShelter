package com.jteam.GroupProject.service.impl;

import com.jteam.GroupProject.exceptions.NotFoundIdException;
import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.repository.TrialPeriodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrialPeriodServiceImplTest {
    @Mock
    private TrialPeriodRepository trialPeriodRepositoryMock;
    @InjectMocks
    private TrialPeriodServiceImpl trialPeriodService;

    @Test
    void testCreateTrialPeriod() {

        TrialPeriod inputTrialPeriod = new TrialPeriod();
        when(trialPeriodRepositoryMock.save(any(TrialPeriod.class))).thenReturn(inputTrialPeriod);

        TrialPeriod createdTrialPeriod = trialPeriodService.create(inputTrialPeriod);

        assertNotNull(createdTrialPeriod);
        assertEquals(inputTrialPeriod, createdTrialPeriod);

        verify(trialPeriodRepositoryMock, times(1)).save(inputTrialPeriod);
    }
    @Test
    void testCreateTrialPeriod2() {
        TrialPeriod inputTrialPeriod = new TrialPeriod();
        TrialPeriod.AnimalType animalType = TrialPeriod.AnimalType.DOG;

        when(trialPeriodRepositoryMock.save(any(TrialPeriod.class), any(TrialPeriod.AnimalType.class)))
                .thenReturn(inputTrialPeriod);

        TrialPeriod createdTrialPeriod = trialPeriodService.create(inputTrialPeriod, animalType);

        assertNotNull(createdTrialPeriod);
        assertEquals(inputTrialPeriod, createdTrialPeriod);


        verify(trialPeriodRepositoryMock, times(1)).save(eq(inputTrialPeriod), eq(animalType));
    }
    @Test
    void testGetById() {
        Long trialPeriodId = 1L;
        TrialPeriod expectedTrialPeriod = new TrialPeriod();
        when(trialPeriodRepositoryMock.findById(trialPeriodId)).thenReturn(Optional.of(expectedTrialPeriod));

        TrialPeriod retrievedTrialPeriod = trialPeriodService.getById(trialPeriodId);

        assertNotNull(retrievedTrialPeriod);
        assertEquals(expectedTrialPeriod, retrievedTrialPeriod);
        verify(trialPeriodRepositoryMock, times(1)).findById(trialPeriodId);
    }

    @Test
    void testGetByIdNotFound() {
        Long trialPeriodId = 1L;
        when(trialPeriodRepositoryMock.findById(trialPeriodId)).thenReturn(Optional.empty());

        assertThrows(NotFoundIdException.class, () -> trialPeriodService.getById(trialPeriodId));
        verify(trialPeriodRepositoryMock, times(1)).findById(trialPeriodId);
    }

    @Test
    void testGetAll() {
        List<TrialPeriod> expectedTrialPeriods = List.of(new TrialPeriod(), new TrialPeriod());
        when(trialPeriodRepositoryMock.findAll()).thenReturn(expectedTrialPeriods);

        List<TrialPeriod> retrievedTrialPeriods = trialPeriodService.getAll();

        assertNotNull(retrievedTrialPeriods);
        assertEquals(expectedTrialPeriods, retrievedTrialPeriods);
        verify(trialPeriodRepositoryMock, times(1)).findAll();
    }

    @Test
    void testGetAllByOwnerId() {
        Long ownerId = 123L;
        List<TrialPeriod> expectedTrialPeriods = List.of(new TrialPeriod(), new TrialPeriod());
        when(trialPeriodRepositoryMock.findAllByOwnerId(ownerId)).thenReturn(expectedTrialPeriods);

        List<TrialPeriod> retrievedTrialPeriods = trialPeriodService.getAllByOwnerId(ownerId);

        assertNotNull(retrievedTrialPeriods);
        assertEquals(expectedTrialPeriods, retrievedTrialPeriods);
        verify(trialPeriodRepositoryMock, times(1)).findAllByOwnerId(ownerId);

    }
    @Test
    void testUpdate() {
        TrialPeriod existingTrialPeriod = new TrialPeriod();
        existingTrialPeriod.setId(1L);

        when(trialPeriodRepositoryMock.findById(existingTrialPeriod.getId()))
                .thenReturn(Optional.of(existingTrialPeriod));

        when(trialPeriodRepositoryMock.save(existingTrialPeriod))
                .thenReturn(existingTrialPeriod);

        TrialPeriod updatedTrialPeriod = trialPeriodService.update(existingTrialPeriod);

        assertNotNull(updatedTrialPeriod);
        assertEquals(existingTrialPeriod, updatedTrialPeriod);

        verify(trialPeriodRepositoryMock, times(1)).findById(existingTrialPeriod.getId());
        verify(trialPeriodRepositoryMock, times(1)).save(existingTrialPeriod);
    }

    @Test
    void testDelete() {
        Long trialPeriodId = 1L;
        TrialPeriod trialPeriod = new TrialPeriod();
        trialPeriod.setId(trialPeriodId);

        assertDoesNotThrow(() -> trialPeriodService.delete(trialPeriod));

        verify(trialPeriodRepositoryMock, times(1)).delete(trialPeriod);
    }

    @Test
    void testDeleteNullTrialPeriod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trialPeriodService.delete(null));

        verify(trialPeriodRepositoryMock, never()).delete(any());

        assertEquals("TrialPeriod or its ID cannot be null", exception.getMessage());
    }

    @Test
    void testDeleteNullTrialPeriodId() {
        TrialPeriod trialPeriod = new TrialPeriod();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trialPeriodService.delete(trialPeriod));

        verify(trialPeriodRepositoryMock, never()).delete(any());

        assertEquals("TrialPeriod or its ID cannot be null", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        Long existingTrialPeriodId = 1L;

        when(trialPeriodRepositoryMock.findById(existingTrialPeriodId))
                .thenReturn(Optional.of(new TrialPeriod()));

        assertDoesNotThrow(() -> trialPeriodService.deleteById(existingTrialPeriodId));

        verify(trialPeriodRepositoryMock, times(1)).findById(existingTrialPeriodId);
        verify(trialPeriodRepositoryMock, times(1)).deleteById(existingTrialPeriodId);
    }

}