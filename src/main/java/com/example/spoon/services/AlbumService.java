package com.example.spoon.services;

import com.example.spoon.dtos.AlbumDTO;
import com.example.spoon.entities.Album;
import com.example.spoon.mappers.AlbumMapper;
import com.example.spoon.repositories.AlbumRepository;
import com.example.spoon.repositories.ArtistRepository;
import com.example.spoon.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper) {

        if (albumRepository == null || albumMapper == null) {
            throw new IllegalArgumentException("Repository and mapper cannot be null");
        }
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    public List<AlbumDTO> getAllAlbums() {

        return albumRepository.findAll()
                .stream()
                .map(albumMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AlbumDTO getAlbumById(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Album album = albumRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Album not found"));

        return albumMapper.toDTO(album);

    }

    public AlbumDTO saveAlbum(AlbumDTO albumDTO) {
        if (albumDTO == null) {
            throw new IllegalArgumentException("Album cannot be null");
        }
        Album album = albumMapper.toEntity(albumDTO);

        Album savedAlbum = albumRepository.save(album);

        return albumMapper.toDTO(savedAlbum);
    }

    public AlbumDTO updateAlbum( AlbumDTO albumDTO) {
        if (!albumRepository.existsById(albumDTO.getId())) {
            throw new IllegalArgumentException(
                    "Album with ID " + albumDTO.getId() + " does not exist"
            );
        }
        Album album = albumMapper.toEntity(albumDTO);

        Album updatedAlbum = albumRepository.save(album);

        return albumMapper.toDTO(updatedAlbum);
    }

    public boolean deleteAlbum(Integer id) {

        if (albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
            return true;
        }

        return false;
    }



}
