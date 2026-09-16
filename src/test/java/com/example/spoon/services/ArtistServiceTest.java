package com.example.spoon.services;

import com.example.spoon.dtos.ArtistDTO;
import com.example.spoon.entities.Artist;
import com.example.spoon.mappers.ArtistMapper;
import com.example.spoon.mappers.ArtistMapperImpl;
import com.example.spoon.repositories.ArtistRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArtistServiceTest {
    ArtistService artistService;
    ArtistMapper artistMapper;
    ArtistRepository artistRepository;
    Artist artist;
    ArtistDTO artistDTO;

    @BeforeEach
    void setUp() {
        artistRepository = Mockito.mock(ArtistRepository.class);
        artistMapper = new ArtistMapperImpl();
        artistService = new ArtistService(artistRepository, artistMapper);
        artist = new Artist();
        artist.setId(1);
        artist.setArtistName("Test Artist");
        artistDTO = artistMapper.toDTO(artist);
    }

    @Test
    @DisplayName("getAllArtists returns mapped DTOs")
    void getAllArtistsTest() {
        Artist second = new Artist();
        second.setId(2);
        Mockito.when(artistRepository.findAll()).thenReturn(List.of(artist, second));
        List<ArtistDTO> result = artistService.getAllArtists();
        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.get(1).getId(), Matchers.is(2));
    }

    @Test
    @DisplayName("Given an existing ID, getArtistById returns a DTO")
    void getArtistByIdTest() {
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        ArtistDTO result = artistService.getArtistById(1);
        MatcherAssert.assertThat(result, Matchers.notNullValue());
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
    }

    @Test
    @DisplayName("Given a valid DTO, saveArtist saves its mapped entity")
    void saveArtistTest() {
        artistDTO.setId(null);
        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenReturn(artist);
        ArtistDTO result = artistService.saveArtist(artistDTO);
        ArgumentCaptor<Artist> captor = ArgumentCaptor.forClass(Artist.class);
        Mockito.verify(artistRepository).save(captor.capture());
        MatcherAssert.assertThat(captor.getValue().getId(), Matchers.nullValue());
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
        
    }

    @Test
    @DisplayName("Given an existing ID, updateArtist saves and returns the DTO")
    void updateArtistTest() {
        Mockito.when(artistRepository.existsById(1)).thenReturn(true);
        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenReturn(artist);
        ArtistDTO result = artistService.updateArtist(artistDTO);
        ArgumentCaptor<Artist> captor = ArgumentCaptor.forClass(Artist.class);
        Mockito.verify(artistRepository).save(captor.capture());
        MatcherAssert.assertThat(captor.getValue().getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
    }

    @Test
    @DisplayName("Given an existing ID, deleteArtist deletes the correct entity")
    void deleteArtistTest() {
        Mockito.when(artistRepository.existsById(1)).thenReturn(true);
        MatcherAssert.assertThat(artistService.deleteArtist(1), Matchers.is(true));
        Mockito.verify(artistRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Given an unknown ID, getArtistById throws")
    void getMissingArtistTest() {
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> artistService.getArtistById(1));
    }

    @Test
    @DisplayName("Given an unknown ID, updateArtist does not save")
    void updateMissingArtistTest() {
        Mockito.when(artistRepository.existsById(1)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> artistService.updateArtist(artistDTO));
        Mockito.verify(artistRepository, Mockito.never()).save(Mockito.any(Artist.class));
    }

    @Test
    @DisplayName("Given an unknown ID, deleteArtist returns false")
    void deleteMissingArtistTest() {
        Mockito.when(artistRepository.existsById(1)).thenReturn(false);
        MatcherAssert.assertThat(artistService.deleteArtist(1), Matchers.is(false));
        Mockito.verify(artistRepository, Mockito.never()).deleteById(Mockito.anyInt());
    }
}

