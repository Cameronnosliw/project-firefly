package com.example.spoon.mappers;


import com.example.spoon.dtos.SongDTO;
import com.example.spoon.entities.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "album.albumName", target = "albumTitle")
    @Mapping(target = "artistId", expression = "java(song.getArtists().isEmpty() ? null : song.getArtists().get(0).getId())")
    @Mapping(target = "artistName", expression = "java(song.getArtists().isEmpty() ? null : song.getArtists().get(0).getName())")
    SongDTO toDTO(Song song);
    Song toEntity(SongDTO songDTO);
}
