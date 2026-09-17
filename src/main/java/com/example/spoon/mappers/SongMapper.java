package com.example.spoon.mappers;


import com.example.spoon.dtos.SongDTO;
import com.example.spoon.entities.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "album.albumName", target = "albumTitle")

    SongDTO toDTO(Song song);
    Song toEntity(SongDTO songDTO);
}
