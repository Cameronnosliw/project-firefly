package com.example.spoon.controllers;

import com.example.spoon.dtos.SongDTO;
import com.example.spoon.services.SongService;
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

public class SongControllerTest {
    SongController songController;
    SongService songService;
    SongDTO songDTO;

    @BeforeEach
    void setUp() {
        songService = Mockito.mock(SongService.class);
        songController = new SongController(songService);
        songDTO = new SongDTO();
        songDTO.setId(1);
    }

    @Test
    @DisplayName("getAllSongs returns OK with the service results")
    void getAllSongsTest() {
        List<SongDTO> expected = List.of(songDTO);
        Mockito.when(songService.getAllSongs()).thenReturn(expected);
        ResponseEntity<List<SongDTO>> result = songController.getAllSongs(null);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.is(expected));
        Mockito.verify(songService).getAllSongs();
    }

    @Test
    @DisplayName("Given an existing ID, getSongById returns OK")
    void getSongByIdTest() {
        Mockito.when(songService.getSongById(1)).thenReturn(songDTO);
        ResponseEntity<SongDTO> result = songController.getSongById(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(songDTO));
    }

    @Test
    @DisplayName("Given an unknown ID, getSongById returns not found")
    void getMissingSongTest() {
        Mockito.when(songService.getSongById(1)).thenThrow(new NoSuchElementException());
        ResponseEntity<SongDTO> result = songController.getSongById(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
    }

    @Test
    @DisplayName("Creating a song returns created with the saved DTO")
    void createSongTest() {
        Mockito.when(songService.saveSong(songDTO)).thenReturn(songDTO);
        ResponseEntity<SongDTO> result = songController.createSong(songDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.CREATED));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(songDTO));
        Mockito.verify(songService).saveSong(songDTO);
    }

    @Test
    @DisplayName("Updating a song uses the path ID and returns OK")
    void updateSongTest() {
        songDTO.setId(99);
        Mockito.when(songService.updateSong(songDTO)).thenReturn(songDTO);
        ResponseEntity<SongDTO> result = songController.updateSong(1, songDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(songDTO));
        Mockito.verify(songService).updateSong(Mockito.argThat(dto -> Integer.valueOf(1).equals(dto.getId())));
    }

    @Test
    @DisplayName("Updating an unknown song returns not found")
    void updateMissingSongTest() {
        Mockito.when(songService.updateSong(songDTO)).thenThrow(new IllegalArgumentException());
        ResponseEntity<SongDTO> result = songController.updateSong(1, songDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
    }

    @Test
    @DisplayName("Deleting an existing song returns no content")
    void deleteSongTest() {
        Mockito.when(songService.deleteSong(1)).thenReturn(true);
        ResponseEntity<Void> result = songController.deleteSong(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NO_CONTENT));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
        Mockito.verify(songService).deleteSong(1);
    }

    @Test
    @DisplayName("Deleting an unknown song returns not found")
    void deleteMissingSongTest() {
        Mockito.when(songService.deleteSong(1)).thenReturn(false);
        MatcherAssert.assertThat(songController.deleteSong(1).getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        Mockito.verify(songService).deleteSong(1);
    }

    @Test
    @DisplayName("Given a genre, getAllSongs returns filtered results")
    void getSongsByGenreTest() {
        List<SongDTO> expected = List.of(songDTO);
        Mockito.when(songService.getSongsByGenre("ROCK")).thenReturn(expected);
        ResponseEntity<List<SongDTO>> result = songController.getAllSongs("ROCK");
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.is(expected));
        Mockito.verify(songService).getSongsByGenre("ROCK");
        Mockito.verify(songService, Mockito.never()).getAllSongs();
    }

    @Test
    @DisplayName("Given a blank genre, getAllSongs returns all songs")
    void getSongsWithBlankGenreTest() {
        Mockito.when(songService.getAllSongs()).thenReturn(List.of(songDTO));
        ResponseEntity<List<SongDTO>> result = songController.getAllSongs(" ");
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.hasSize(1));
        Mockito.verify(songService).getAllSongs();
        Mockito.verify(songService, Mockito.never()).getSongsByGenre(Mockito.anyString());
    }
}

