package com.example.spoon.controllers;

import com.example.spoon.dtos.SongDTO;
import com.example.spoon.services.SongService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @Operation(summary = "Get all songs", description = "Optionally filter by genre name")
    @GetMapping
    public ResponseEntity<List<SongDTO>> getAllSongs(
            @RequestParam(required = false) String genre) {
        List<SongDTO> songs = (genre == null || genre.trim().isEmpty())
                ? songService.getAllSongs()
                : songService.getSongsByGenre(genre);
        return ResponseEntity.ok(songs);
    }

    @Operation(summary = "Get song by id")
    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Integer id) {
        try {
            SongDTO song = songService.getSongById(id);
            return ResponseEntity.ok(song);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new song")
    @PostMapping
    public ResponseEntity<SongDTO> createSong(@RequestBody SongDTO songDTO) {
        SongDTO savedSong = songService.saveSong(songDTO);
        return new ResponseEntity<>(savedSong, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing song")
    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(
            @PathVariable("id") Integer id,
            @RequestBody SongDTO songDTO) {
        try {
            songDTO.setId(id);
            SongDTO updatedSong = songService.updateSong(songDTO);
            return ResponseEntity.ok(updatedSong);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a song")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Integer id) {
        boolean isDeleted = songService.deleteSong(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
