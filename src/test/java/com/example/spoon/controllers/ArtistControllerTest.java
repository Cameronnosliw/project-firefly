package com.example.spoon.controllers;

import com.example.spoon.dtos.ArtistDTO;
import com.example.spoon.services.ArtistService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

public class ArtistControllerTest {
    ArtistController artistController;
    ArtistService artistService;
    ArtistDTO artistDTO;

    @BeforeEach
    void setUp() {
        artistService = Mockito.mock(ArtistService.class);
        artistController = new ArtistController(artistService);
        artistDTO = new ArtistDTO();
        artistDTO.setId(1);
    }

    @Test
    @DisplayName("getAllArtists returns OK with the service results")
    void getAllArtistsTest() {
        List<ArtistDTO> expected = List.of(artistDTO);
        Mockito.when(artistService.getAllArtists()).thenReturn(expected);
        ResponseEntity<List<ArtistDTO>> result = artistController.getAllArtists();
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.is(expected));
        Mockito.verify(artistService).getAllArtists();
    }

    @Test
    @DisplayName("Given an existing ID, getArtistById returns OK")
    void getArtistByIdTest() {
        Mockito.when(artistService.getArtistById(1)).thenReturn(artistDTO);
        ResponseEntity<ArtistDTO> result = artistController.getArtistById(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(artistDTO));
    }

    @Test
    @DisplayName("Given an unknown ID, getArtistById returns not found")
    void getMissingArtistTest() {
        Mockito.when(artistService.getArtistById(1)).thenThrow(new NoSuchElementException());
        ResponseEntity<ArtistDTO> result = artistController.getArtistById(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
    }

    @Test
    @DisplayName("Creating a artist returns created with the saved DTO")
    void createArtistTest() {
        Mockito.when(artistService.saveArtist(artistDTO)).thenReturn(artistDTO);
        ResponseEntity<ArtistDTO> result = artistController.createArtist(artistDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.CREATED));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(artistDTO));
        Mockito.verify(artistService).saveArtist(artistDTO);
    }

    @Test
    @DisplayName("Updating a artist uses the path ID and returns OK")
    void updateArtistTest() {
        artistDTO.setId(99);
        Mockito.when(artistService.updateArtist(artistDTO)).thenReturn(artistDTO);
        ResponseEntity<ArtistDTO> result = artistController.updateArtist(1, artistDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(artistDTO));
        Mockito.verify(artistService).updateArtist(Mockito.argThat(dto -> Integer.valueOf(1).equals(dto.getId())));
    }

    @Test
    @DisplayName("Updating an unknown artist returns not found")
    void updateMissingArtistTest() {
        Mockito.when(artistService.updateArtist(artistDTO)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ArtistDTO> result = artistController.updateArtist(1, artistDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
    }

    @Test
    @DisplayName("Deleting an existing artist returns no content")
    void deleteArtistTest() {
        Mockito.when(artistService.deleteArtist(1)).thenReturn(true);
        ResponseEntity<Void> result = artistController.deleteArtist(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NO_CONTENT));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
        Mockito.verify(artistService).deleteArtist(1);
    }

    @Test
    @DisplayName("Deleting an unknown artist returns not found")
    void deleteMissingArtistTest() {
        Mockito.when(artistService.deleteArtist(1)).thenReturn(false);
        MatcherAssert.assertThat(artistController.deleteArtist(1).getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        Mockito.verify(artistService).deleteArtist(1);
    }
}

