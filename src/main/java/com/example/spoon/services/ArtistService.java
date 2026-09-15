package com.example.spoon.services;

import com.example.spoon.dtos.ArtistDTO;
import com.example.spoon.entities.Artist;
import com.example.spoon.mappers.ArtistMapper;
import com.example.spoon.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper) {

        if (artistRepository == null || artistMapper == null) {
            throw new IllegalArgumentException("Repository and mapper cannot be null");
        }

        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    public List<ArtistDTO> getAllArtists() {

        return artistRepository.findAll()
                .stream()
                .map(artistMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ArtistDTO getArtistById(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Artist not found"));

        return artistMapper.toDTO(artist);
    }

    public ArtistDTO saveArtist(ArtistDTO artistDTO) {

        if (artistDTO == null) {
            throw new IllegalArgumentException("Artist cannot be null");
        }

        Artist artist = artistMapper.toEntity(artistDTO);

        Artist savedArtist =
                artistRepository.save(artist);

        return artistMapper.toDTO(savedArtist);
    }

    public ArtistDTO updateArtist(ArtistDTO artistDTO) {

        if (artistDTO == null || artistDTO.getId() == null) {
            throw new IllegalArgumentException("Artist and Artist ID cannot be null");
        }

        if (!artistRepository.existsById(artistDTO.getId())) {
            throw new IllegalArgumentException(
                    "Artist with ID " + artistDTO.getId() + " does not exist"
            );
        }

        Artist artist =
                artistMapper.toEntity(artistDTO);

        Artist updatedArtist =
                artistRepository.save(artist);

        return artistMapper.toDTO(updatedArtist);
    }

    public boolean deleteArtist(Integer id) {

        if (id != null && artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
