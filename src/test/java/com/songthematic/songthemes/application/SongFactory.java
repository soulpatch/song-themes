package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SongFactory {
    @NotNull
    public static Song createSong(String title, String theme) {
        return createSong(title, List.of(theme));
    }

    @NotNull
    public static Song createSong(String songTitle, List<String> themes) {
        return new Song("IrrelevantArtist", songTitle, "IrrelevantReleaseTitle", "IrrelevantReleaseType", themes);
    }

    public static Song createSong(String artist, String songTitle, String releaseTitle, String releaseType, String theme1) {
        return new Song(artist, songTitle, releaseTitle, releaseType, List.of(theme1));
    }

    public static Song createSong(String artist, String songTitle, String releaseTitle, String releaseType, String theme1, String theme2) {
        return new Song(artist, songTitle, releaseTitle, releaseType, List.of(theme1, theme2));
    }
}
