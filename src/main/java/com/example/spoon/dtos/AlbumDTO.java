package com.example.spoon.dtos;

import java.time.LocalDate;
import java.util.List;

public class AlbumDTO {

    private Integer id;
    private LocalDate releaseDate;
    private String artistName;
    private String albumName;
    private Integer artistId;
    private List<String> songTitles;

    public AlbumDTO() {}

    public AlbumDTO(Integer id, LocalDate releaseDate, String artistName, String albumName, Integer artistId, List<String> songTitles) {
        this.id = id;
        this.releaseDate = releaseDate;
        this.artistName = artistName;
        this.albumName = albumName;
        this.artistId = artistId;
        this.songTitles = songTitles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public List<String> getSongTitles() {
        return songTitles;
    }

    public void setSongTitles(List<String> songTitles) {
        this.songTitles = songTitles;
    }
}
