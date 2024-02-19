package com.songthematic.songthemes.domain;

import com.songthematic.songthemes.application.SongFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SongSearchByThemeTest {

    @Test
    public void searchForThemeThatDoesNotExistReturnsNoResults() throws Exception {
        SongSearcher songSearcher = SongSearcher.withOneDefaultSongAndTheme("new years");

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
        SongSearcher songSearcher = SongSearcher.createSongSearcher(SongFactory.createSong("auld lang syne", songTheme));

        List<String> foundSong = songSearcher.songTitlesByTheme(requestedTheme);

        assertThat(foundSong)
                .containsExactly("auld lang syne");
    }

    @Test
    void searchForThemeFindsMultipleMatchingSongTitles() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(
                SongFactory.createSong("auld lang syne", "new years"),
                SongFactory.createSong("New Year's Eve In A Haunted House", "new years"));
        List<String> foundSongs = songSearcher.songTitlesByTheme("New Years");

        assertThat(foundSongs)
                .containsExactly("auld lang syne",
                                 "New Year's Eve In A Haunted House");
    }

    @Test
    void searchByThemeFindsMultipleMatchingSongs() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(
                SongFactory.createSong("auld lang syne", "new years"),
                SongFactory.createSong("New Year's Eve In A Haunted House", "new years"));

        List<Song> foundSongs = songSearcher.byTheme("New Years");

        assertThat(foundSongs)
                .containsExactly(
                        SongFactory.createSong("auld lang syne", "new years"),
                        SongFactory.createSong("New Year's Eve In A Haunted House", "new years"));
    }

    @Test
    void forSongsWithDifferentThemesSearchFindsAllSongs() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(
                SongFactory.createSong("auld lang syne", "new years"),
                SongFactory.createSong("The Christmas Tree is on Fire", "christmas"));

        assertThat(songSearcher.songTitlesByTheme("new years"))
                .containsExactly("auld lang syne");
        assertThat(songSearcher.songTitlesByTheme("Christmas"))
                .containsExactly("The Christmas Tree is on Fire");

    }

    @Test
    void songWithMultipleThemesIsFoundByItsSecondTheme() throws Exception {
        SongSearcher songSearcher = SongSearcher.createSongSearcher(
                SongFactory.createSong("Nightmare Before Christmas", List.of("Christmas", "Halloween")));

        List<Song> songsFound = songSearcher.byTheme("halloween");
        assertThat(songsFound)
                .extracting(Song::songTitle)
                .containsExactly("Nightmare Before Christmas");
    }

}
