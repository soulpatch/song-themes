package com.songthematic.songthemes.adapter.out.jdbc;

import com.songthematic.songthemes.domain.Song;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Objects;

@Table("songs")
public class SongDbo {
    @Id
    private Long id;

    private String artist;
    private String songTitle;
    private String releaseTitle;
    private String releaseType;
    private List<String> themes;

    @PersistenceCreator
    public SongDbo(Long id, String artist, String songTitle, String releaseTitle, String releaseType, List<String> themes) {
        this.id = id;
        this.artist = artist;
        this.songTitle = songTitle;
        this.releaseTitle = releaseTitle;
        this.releaseType = releaseType;
        this.themes = themes;
    }

    public SongDbo(String artist, String songTitle, String releaseTitle, String releaseType, List<String> themes) {
        this.artist = artist;
        this.songTitle = songTitle;
        this.releaseTitle = releaseTitle;
        this.releaseType = releaseType;
        this.themes = themes;
    }

    static SongDbo from(Song song) {
        return new SongDbo(song.artist(), song.songTitle(), song.releaseTitle(), song.releaseType(), song.themes());
    }

    public Song toDomain() {
        return new Song(artist, songTitle, releaseTitle, releaseType, themes);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getReleaseTitle() {
        return releaseTitle;
    }

    public void setReleaseTitle(String releaseTitle) {
        this.releaseTitle = releaseTitle;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public List<String> getThemes() {
        return themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
    }

    @Override
    public String toString() {
        return "SongDbo{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", songTitle='" + songTitle + '\'' +
                ", releaseTitle='" + releaseTitle + '\'' +
                ", releaseType='" + releaseType + '\'' +
                ", themes=" + themes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongDbo songDbo = (SongDbo) o;

        return Objects.equals(id, songDbo.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
