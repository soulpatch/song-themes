package com.songthematic.songthemes.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SongSearchByThemeTest {

    @Test
    public void searchForThemeThatDoesNotExistReturnsNoResults() throws Exception {
        SongSearcher songSearcher = SongSearcher.withOneSongForTheme("new years");

        List<String> foundSongs = songSearcher.songTitlesByTheme("Applesauce");

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

        List<String> foundSong = songSearcher.songTitlesByTheme(requestedTheme);

        assertThat(foundSong)
                .containsExactly("auld lang syne");
    }

    @Test
    void searchForThemeFindsMultipleMatchingSongs() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(
                new Song("new years", "auld lang syne"),
                new Song("new years", "New Year's Eve In A Haunted House"));
        List<String> foundSongs = songSearcher.songTitlesByTheme("New Years");

        assertThat(foundSongs)
                .containsExactly("auld lang syne",
                                 "New Year's Eve In A Haunted House");
    }

    @Test
    void forSongsWithDifferentThemesSearchFindsAllSongs() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(
                new Song("new years", "auld lang syne"),
                new Song("christmas", "The Christmas Tree is on Fire"));

        assertThat(songSearcher.songTitlesByTheme("new years"))
                .containsExactly("auld lang syne");
        assertThat(songSearcher.songTitlesByTheme("Christmas"))
                .containsExactly("The Christmas Tree is on Fire");

    }
}
