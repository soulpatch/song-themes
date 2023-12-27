package com.songthematic.songthemes;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SongSearchByThemeTest {

    @Test
    public void searchForThemeThatDoesNotExistReturnsNoResults() throws Exception {
        SongSearcher songSearcher = SongSearcher.withOneSongForTheme("new years");

        List<String> foundSongs = songSearcher.byTheme("Applesauce");

        assertThat(foundSongs)
                .isEmpty();
    }

    @ParameterizedTest()
    @CsvSource({
            "new years,New Years",
            "new years,new years",
            "New Years,New Years",
            "New Years,new years"
    })
    void searchForThemeFindsOneMatchingSongIgnoringCase(String songTheme, String requestedTheme) throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(new Song(songTheme, "auld lang syne"));

        List<String> foundSong = songSearcher.byTheme(requestedTheme);

        assertThat(foundSong)
                .containsExactly("auld lang syne");
    }

    @Test
    @Disabled
    void searchForThemeFindsMultipleMatchingSongs() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(new Song("new years", "auld lang syne"),
                                                                    new Song("new years", "New Year's Eve In A Haunted House"));
        List<String> foundSongs = songSearcher.byTheme("New Years");

        assertThat(foundSongs)
                .containsExactly("auld lang syne",
                                 "New Year's Eve In A Haunted House");
    }
}
