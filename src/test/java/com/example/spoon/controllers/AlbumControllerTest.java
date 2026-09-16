package com.example.spoon.controllers;

import com.example.spoon.dtos.AlbumDTO;
import com.example.spoon.services.AlbumService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AlbumControllerTest {
    AlbumController albumController;
    AlbumService albumService;
    AlbumDTO albumDTO;

    @BeforeEach
    void setUp() {
        albumService = Mockito.mock(AlbumService.class);
        albumController = new AlbumController(albumService);
        albumDTO = new AlbumDTO();
        albumDTO.setId(1);
    }

    @Test
    @DisplayName("getAllAlbums returns OK with the service results")
    void getAllAlbumsTest() {
        List<AlbumDTO> expected = List.of(albumDTO);
        Mockito.when(albumService.getAllAlbums()).thenReturn(expected);
        ResponseEntity<List<AlbumDTO>> result = albumController.getAllAlbums();
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.is(expected));
        Mockito.verify(albumService).getAllAlbums();
    }

    @Test
    @DisplayName("Given an existing ID, getAlbumById returns OK")
    void getAlbumByIdTest() {
        Mockito.when(albumService.getAlbumById(1)).thenReturn(albumDTO);
        ResponseEntity<AlbumDTO> result = albumController.getAlbumById(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(albumDTO));
    }

    @Test
    @DisplayName("Given a null service result, getAlbumById returns not found")
    void getMissingAlbumTest() {
        Mockito.when(albumService.getAlbumById(1)).thenReturn(null);
        ResponseEntity<AlbumDTO> result = albumController.getAlbumById(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
    }

    @Test
    @DisplayName("Creating a album returns created with the saved DTO")
    void createAlbumTest() {
        Mockito.when(albumService.saveAlbum(albumDTO)).thenReturn(albumDTO);
        ResponseEntity<AlbumDTO> result = albumController.addAlbum(albumDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.CREATED));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(albumDTO));
        Mockito.verify(albumService).saveAlbum(albumDTO);
    }

    @Test
    @DisplayName("Updating a album uses the path ID and returns OK")
    void updateAlbumTest() {
        albumDTO.setId(99);
        Mockito.when(albumService.updateAlbum(albumDTO)).thenReturn(albumDTO);
        ResponseEntity<AlbumDTO> result = albumController.updateAlbum(1, albumDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(result.getBody(), Matchers.sameInstance(albumDTO));
        Mockito.verify(albumService).updateAlbum(Mockito.argThat(dto -> Integer.valueOf(1).equals(dto.getId())));
    }

    @Test
    @DisplayName("Updating an unknown album returns not found")
    void updateMissingAlbumTest() {
        Mockito.when(albumService.updateAlbum(albumDTO)).thenThrow(new IllegalArgumentException());
        ResponseEntity<AlbumDTO> result = albumController.updateAlbum(1, albumDTO);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
    }

    @Test
    @DisplayName("Deleting an existing album returns no content")
    void deleteAlbumTest() {
        Mockito.when(albumService.deleteAlbum(1)).thenReturn(true);
        ResponseEntity<Void> result = albumController.deleteAlbum(1);
        MatcherAssert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.NO_CONTENT));
        MatcherAssert.assertThat(result.getBody(), Matchers.nullValue());
        Mockito.verify(albumService).deleteAlbum(1);
    }

    @Test
    @DisplayName("Deleting an unknown album returns not found")
    void deleteMissingAlbumTest() {
        Mockito.when(albumService.deleteAlbum(1)).thenReturn(false);
        MatcherAssert.assertThat(albumController.deleteAlbum(1).getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        Mockito.verify(albumService).deleteAlbum(1);
    }
}

