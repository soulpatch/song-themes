package com.songthematic.songthemes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SongSearchByThemeTest {

    @Test
    public void searchForThemeThatDoesNotExistReturnsNoResults() throws Exception {
        SongSearcher songSearcher = new SongSearcher();

        List<String> foundSongs = songSearcher.byTheme("Applesauce");

        assertThat(foundSongs)
                .isEmpty();
    }
}
