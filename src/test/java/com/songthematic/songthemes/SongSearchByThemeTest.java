package com.songthematic.songthemes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SongSearchByThemeTest {

    @Test
    public void searchForThemeThatDoesNotExistReturnsNoResults() throws Exception {
        SongSearcher songSearcher = SongSearcher.withSongsForTheme("new years");

        List<String> foundSongs = songSearcher.byTheme("Applesauce");

        assertThat(foundSongs)
                .isEmpty();
    }

    @Test
    void searchForThemeFindsOneMatchingSong() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher("new years", "auld lang syne");

        List<String> foundSong = songSearcher.byTheme("New Years");

        assertThat(foundSong)
                .containsExactly("auld lang syne");
    }
}
