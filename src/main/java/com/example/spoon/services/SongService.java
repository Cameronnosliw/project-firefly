package com.example.spoon.services;

import com.example.spoon.dtos.SongDTO;
import com.example.spoon.entities.Song;
import com.example.spoon.mappers.SongMapper;
import com.example.spoon.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final SongMapper songMapper;

    @Autowired
    public SongService(SongRepository songRepository,
                       SongMapper songMapper) {

        if (songRepository == null || songMapper == null) {
            throw new IllegalArgumentException("Repository and mapper cannot be null");
        }

        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    public List<SongDTO> getAllSongs() {

        return songRepository.findAll()
                .stream()
                .map(songMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<SongDTO> getSongsByGenre(String genreName) {

        if (genreName == null || genreName.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre name cannot be null or empty");
        }

        return songRepository.findByGenres_NameIgnoreCase(genreName)
                .stream()
                .map(songMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SongDTO getSongById(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Song song = songRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Song not found"));

        return songMapper.toDTO(song);
    }

    public SongDTO saveSong(SongDTO songDTO) {

        if (songDTO == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }

        Song song = songMapper.toEntity(songDTO);

        Song savedSong =
                songRepository.save(song);

        return songMapper.toDTO(savedSong);
    }

    public SongDTO updateSong(SongDTO songDTO) {

        if (songDTO == null || songDTO.getId() == null) {
            throw new IllegalArgumentException("Song and Song ID cannot be null");
        }

        if (!songRepository.existsById(songDTO.getId())) {
            throw new IllegalArgumentException(
                    "Song with ID " + songDTO.getId() + " does not exist"
            );
        }

        Song song =
                songMapper.toEntity(songDTO);

        Song updatedSong =
                songRepository.save(song);

        return songMapper.toDTO(updatedSong);
    }

    public boolean deleteSong(Integer id) {

        if (id != null && songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
