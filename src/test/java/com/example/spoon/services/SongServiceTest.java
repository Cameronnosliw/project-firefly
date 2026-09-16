package com.example.spoon.services;

import com.example.spoon.dtos.SongDTO;
import com.example.spoon.entities.Song;
import com.example.spoon.mappers.SongMapper;
import com.example.spoon.mappers.SongMapperImpl;
import com.example.spoon.repositories.SongRepository;
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

public class SongServiceTest {
    SongService songService;
    SongMapper songMapper;
    SongRepository songRepository;
    Song song;
    SongDTO songDTO;

    @BeforeEach
    void setUp() {
        songRepository = Mockito.mock(SongRepository.class);
        songMapper = new SongMapperImpl();
        songService = new SongService(songRepository, songMapper);
        song = new Song();
        song.setId(1);
        song.setDuration(210);
        songDTO = songMapper.toDTO(song);
    }

    @Test
    @DisplayName("getAllSongs returns mapped DTOs")
    void getAllSongsTest() {
        Song second = new Song();
        second.setId(2);
        Mockito.when(songRepository.findAll()).thenReturn(List.of(song, second));
        List<SongDTO> result = songService.getAllSongs();
        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.get(1).getId(), Matchers.is(2));
    }

    @Test
    @DisplayName("Given an existing ID, getSongById returns a DTO")
    void getSongByIdTest() {
        Mockito.when(songRepository.findById(1)).thenReturn(Optional.of(song));
        SongDTO result = songService.getSongById(1);
        MatcherAssert.assertThat(result, Matchers.notNullValue());
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.getDuration(), Matchers.is(210));
    }

    @Test
    @DisplayName("Given a valid DTO, saveSong saves its mapped entity")
    void saveSongTest() {
        songDTO.setId(null);
        Mockito.when(songRepository.save(Mockito.any(Song.class))).thenReturn(song);
        SongDTO result = songService.saveSong(songDTO);
        ArgumentCaptor<Song> captor = ArgumentCaptor.forClass(Song.class);
        Mockito.verify(songRepository).save(captor.capture());
        MatcherAssert.assertThat(captor.getValue().getId(), Matchers.nullValue());
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
        MatcherAssert.assertThat(captor.getValue().getDuration(), Matchers.is(210));
    }

    @Test
    @DisplayName("Given an existing ID, updateSong saves and returns the DTO")
    void updateSongTest() {
        Mockito.when(songRepository.existsById(1)).thenReturn(true);
        Mockito.when(songRepository.save(Mockito.any(Song.class))).thenReturn(song);
        SongDTO result = songService.updateSong(songDTO);
        ArgumentCaptor<Song> captor = ArgumentCaptor.forClass(Song.class);
        Mockito.verify(songRepository).save(captor.capture());
        MatcherAssert.assertThat(captor.getValue().getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.getDuration(), Matchers.is(210));
    }

    @Test
    @DisplayName("Given an existing ID, deleteSong deletes the correct entity")
    void deleteSongTest() {
        Mockito.when(songRepository.existsById(1)).thenReturn(true);
        MatcherAssert.assertThat(songService.deleteSong(1), Matchers.is(true));
        Mockito.verify(songRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Given an unknown ID, getSongById throws")
    void getMissingSongTest() {
        Mockito.when(songRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> songService.getSongById(1));
    }

    @Test
    @DisplayName("Given an unknown ID, updateSong does not save")
    void updateMissingSongTest() {
        Mockito.when(songRepository.existsById(1)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> songService.updateSong(songDTO));
        Mockito.verify(songRepository, Mockito.never()).save(Mockito.any(Song.class));
    }

    @Test
    @DisplayName("Given an unknown ID, deleteSong returns false")
    void deleteMissingSongTest() {
        Mockito.when(songRepository.existsById(1)).thenReturn(false);
        MatcherAssert.assertThat(songService.deleteSong(1), Matchers.is(false));
        Mockito.verify(songRepository, Mockito.never()).deleteById(Mockito.anyInt());
    }

    @Test
    @DisplayName("Given a genre, getSongsByGenre returns matching song DTOs")
    void getSongsByGenreTest() {
        Mockito.when(songRepository.findByGenres_NameIgnoreCase("ROCK")).thenReturn(List.of(song));
        List<SongDTO> result = songService.getSongsByGenre("ROCK");
        MatcherAssert.assertThat(result, Matchers.hasSize(1));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.is(1));
        Mockito.verify(songRepository).findByGenres_NameIgnoreCase("ROCK");
    }

    @Test
    @DisplayName("Given a blank genre, getSongsByGenre rejects it")
    void getSongsByBlankGenreTest() {
        assertThrows(IllegalArgumentException.class, () -> songService.getSongsByGenre(" "));
        Mockito.verifyNoInteractions(songRepository);
    }
}

