package com.example.spoon.mappers;

import com.example.spoon.dtos.AlbumDTO;
import com.example.spoon.dtos.ArtistDTO;
import com.example.spoon.entities.Album;
import com.example.spoon.entities.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    ArtistDTO toDTO(Artist artist);
    Artist toEntity(ArtistDTO artistDTO);
}