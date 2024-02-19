package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.domain.Song;

import java.util.List;

public record SongView(String title) {
    static List<SongView> from(List<Song> foundSongs) {
        return foundSongs.stream()
                         .map(Song::songTitle)
                         .map(SongView::new)
                         .toList();
    }
}
