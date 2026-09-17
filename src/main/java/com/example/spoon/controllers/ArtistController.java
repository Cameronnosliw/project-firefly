package com.example.spoon.controllers;

import com.example.spoon.dtos.ArtistDTO;
import com.example.spoon.services.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping( "/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {this.artistService = artistService;}

    @Operation(summary = "Get all artists")
    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        List<ArtistDTO> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @Operation(summary = "Get artist by id")
    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Integer id) {
        try {
            ArtistDTO artist = artistService.getArtistById(id);
            return ResponseEntity.ok(artist);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new artist")
    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistDTO artistDTO) {
        ArtistDTO savedArtist = artistService.saveArtist(artistDTO);
        return new ResponseEntity<>(savedArtist, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing artist")
    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(
            @PathVariable("id") Integer id,
            @RequestBody ArtistDTO artistDTO) {
        try {
            artistDTO.setId(id);
            ArtistDTO updatedArtist = artistService.updateArtist(artistDTO);
            return ResponseEntity.ok(updatedArtist);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an artist")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable("id") Integer id) {
        boolean isDeleted = artistService.deleteArtist(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
