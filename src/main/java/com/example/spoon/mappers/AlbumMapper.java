package com.example.spoon.mappers;

import com.example.spoon.dtos.AlbumDTO;
import com.example.spoon.entities.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface AlbumMapper {

    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "artist.name", target = "artistName")

    AlbumDTO toDTO(Album album);
    Album toEntity(AlbumDTO albumDTO);
}
