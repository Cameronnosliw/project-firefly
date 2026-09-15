package com.example.spoon.mappers;

import com.example.spoon.dtos.AlbumDTO;
import com.example.spoon.entities.Album;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
    AlbumDTO toDTO(Album album);
    Album toEntity(AlbumDTO albumDTO);
}
