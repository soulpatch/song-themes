package com.songthematic.songthemes.domain;

import com.songthematic.songthemes.application.SongFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongSearcherTest {
    @Test
    void addSongWithMultipleThemesAreAddedCorrectly() throws Exception {
        Song song = SongFactory.createSong("auld lang syne", List.of("new years", "protest"));

        SongSearcher songSearcher = SongSearcher.createSongSearcher();
        songSearcher = songSearcher.add(song);

        assertThat(songSearcher.byTheme("protest"))
                .hasSize(1);
    }

    @Test
    void addMultipleSongsWithMultipleThemesAreAddedCorrectly() throws Exception {
        Song song = SongFactory.createSong("auld lang syne", List.of("new years", "protest"));
        Song song2 = SongFactory.createSong("Creature with the Atom Brain", List.of("protest", "halloween"));

        SongSearcher songSearcher = SongSearcher.createSongSearcher();
        songSearcher = songSearcher.add(song);
        songSearcher = songSearcher.add(song2);

        assertThat(songSearcher.byTheme("protest"))
                .hasSize(2);
    }
}