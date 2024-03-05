package com.songthematic.songthemes.application;

import org.jetbrains.annotations.NotNull;

public class TsvSongFactory {
    @NotNull
    public static String createTsvSongsWithHeader(String tsvSongs) {
        String header = "Artist\tSong Title\tRelease Title\tRelease Type\tNotes\tTheme1\tTheme2\tTheme3\tTheme4\tContributor\n";
        return header + tsvSongs;
    }
}
