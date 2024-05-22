package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.domain.Song;

import java.util.List;
import java.util.stream.Collectors;

public record SongView(String artist, String songTitle, String releaseTitle, String releaseType, String themes) {
    static List<SongView> from(List<Song> foundSongs) {
        return foundSongs.stream()
                         .map(song -> new SongView(song.artist(),
                                                   song.songTitle(),
                                                   song.releaseTitle(),
                                                   song.releaseType(),
                                                   song.themes().stream().collect(Collectors.joining(", ", "[", "]"))))
                         .toList();
    }
}
