package com.songthematic.songthemes.domain;

import com.songthematic.songthemes.application.SongFactory;
import com.songthematic.songthemes.application.SongRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongRepositoryTest {
    @Test
    void addSongWithMultipleThemesAreAddedCorrectly() throws Exception {
        Song song = SongFactory.createSong("auld lang syne", List.of("new years", "protest"));

        SongRepository songRepository = SongRepository.createEmpty();
        songRepository.add(song);

        assertThat(songRepository.byTheme("protest"))
                .hasSize(1);
    }

    @Test
    void addMultipleSongsWithMultipleThemesAreAddedCorrectly() throws Exception {
        Song song = SongFactory.createSong("auld lang syne", List.of("new years", "protest"));
        Song song2 = SongFactory.createSong("Creature with the Atom Brain", List.of("protest", "halloween"));

        SongRepository songRepository = SongRepository.createEmpty();
        songRepository.add(song);
        songRepository.add(song2);

        assertThat(songRepository.byTheme("protest"))
                .hasSize(2);
    }
}