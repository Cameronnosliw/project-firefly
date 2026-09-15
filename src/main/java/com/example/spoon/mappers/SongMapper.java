package com.example.spoon.mappers;


import com.example.spoon.dtos.SongDTO;

import com.example.spoon.entities.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {
    SongDTO toDTO(Song song);
    Song toEntity(SongDTO songDTO);
}
