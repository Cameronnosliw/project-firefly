package com.example.spoon.services;

import com.example.spoon.dtos.AlbumDTO;
import com.example.spoon.entities.Album;
import com.example.spoon.mappers.AlbumMapper;
import com.example.spoon.mappers.AlbumMapperImpl;
import com.example.spoon.repositories.AlbumRepository;
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

public class AlbumServiceTest {
    AlbumService albumService;
    AlbumMapper albumMapper;
    AlbumRepository albumRepository;
    Album album;
    AlbumDTO albumDTO;

    @BeforeEach
    void setUp() {
        albumRepository = Mockito.mock(AlbumRepository.class);
        albumMapper = new AlbumMapperImpl();
        albumService = new AlbumService(albumRepository, albumMapper);
        album = new Album();
        album.setId(1);
        album.setAlbumName("Test Album");
        albumDTO = albumMapper.toDTO(album);
    }

    @Test
    @DisplayName("getAllAlbums returns mapped DTOs")
    void getAllAlbumsTest() {
        Album second = new Album();
        second.setId(2);
        Mockito.when(albumRepository.findAll()).thenReturn(List.of(album, second));
        List<AlbumDTO> result = albumService.getAllAlbums();
        MatcherAssert.assertThat(result, Matchers.hasSize(2));
        MatcherAssert.assertThat(result.get(0).getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.get(1).getId(), Matchers.is(2));
    }

    @Test
    @DisplayName("Given an existing ID, getAlbumById returns a DTO")
    void getAlbumByIdTest() {
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        AlbumDTO result = albumService.getAlbumById(1);
        MatcherAssert.assertThat(result, Matchers.notNullValue());
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.getAlbumName(), Matchers.is("Test Album"));
    }

    @Test
    @DisplayName("Given a valid DTO, saveAlbum saves its mapped entity")
    void saveAlbumTest() {
        albumDTO.setId(null);
        Mockito.when(albumRepository.save(Mockito.any(Album.class))).thenReturn(album);
        AlbumDTO result = albumService.saveAlbum(albumDTO);
        ArgumentCaptor<Album> captor = ArgumentCaptor.forClass(Album.class);
        Mockito.verify(albumRepository).save(captor.capture());
        MatcherAssert.assertThat(captor.getValue().getId(), Matchers.nullValue());
        MatcherAssert.assertThat(result.getId(), Matchers.is(1));
        MatcherAssert.assertThat(captor.getValue().getAlbumName(), Matchers.is("Test Album"));
    }

    @Test
    @DisplayName("Given an existing ID, updateAlbum saves and returns the DTO")
    void updateAlbumTest() {
        Mockito.when(albumRepository.existsById(1)).thenReturn(true);
        Mockito.when(albumRepository.save(Mockito.any(Album.class))).thenReturn(album);
        AlbumDTO result = albumService.updateAlbum(albumDTO);
        ArgumentCaptor<Album> captor = ArgumentCaptor.forClass(Album.class);
        Mockito.verify(albumRepository).save(captor.capture());
        MatcherAssert.assertThat(captor.getValue().getId(), Matchers.is(1));
        MatcherAssert.assertThat(result.getAlbumName(), Matchers.is("Test Album"));
    }

    @Test
    @DisplayName("Given an existing ID, deleteAlbum deletes the correct entity")
    void deleteAlbumTest() {
        Mockito.when(albumRepository.existsById(1)).thenReturn(true);
        MatcherAssert.assertThat(albumService.deleteAlbum(1), Matchers.is(true));
        Mockito.verify(albumRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Given an unknown ID, getAlbumById throws")
    void getMissingAlbumTest() {
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> albumService.getAlbumById(1));
    }

    @Test
    @DisplayName("Given an unknown ID, updateAlbum does not save")
    void updateMissingAlbumTest() {
        Mockito.when(albumRepository.existsById(1)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> albumService.updateAlbum(albumDTO));
        Mockito.verify(albumRepository, Mockito.never()).save(Mockito.any(Album.class));
    }

    @Test
    @DisplayName("Given an unknown ID, deleteAlbum returns false")
    void deleteMissingAlbumTest() {
        Mockito.when(albumRepository.existsById(1)).thenReturn(false);
        MatcherAssert.assertThat(albumService.deleteAlbum(1), Matchers.is(false));
        Mockito.verify(albumRepository, Mockito.never()).deleteById(Mockito.anyInt());
    }
}

