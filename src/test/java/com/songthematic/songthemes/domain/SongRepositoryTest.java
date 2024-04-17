package com.songthematic.songthemes.domain;

import com.songthematic.songthemes.application.InMemorySongRepository;
import com.songthematic.songthemes.application.SongFactory;
import com.songthematic.songthemes.application.port.SongRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongRepositoryTest {
    @Test
    void addSongWithMultipleThemesAreAddedCorrectly() throws Exception {
        Song song = SongFactory.createSong("auld lang syne", List.of("new years", "protest"));

        SongRepository songRepository = InMemorySongRepository.createEmpty();
        songRepository.add(song);

        assertThat(songRepository.findByTheme("protest"))
                .hasSize(1);
    }

    @Test
    void addMultipleSongsWithMultipleThemesAreAddedCorrectly() throws Exception {
        Song song = SongFactory.createSong("auld lang syne", List.of("new years", "protest"));
        Song song2 = SongFactory.createSong("Creature with the Atom Brain", List.of("protest", "halloween"));

        SongRepository songRepository = InMemorySongRepository.createEmpty();
        songRepository.add(song);
        songRepository.add(song2);

        assertThat(songRepository.findByTheme("protest"))
                .hasSize(2);
    }

    public static InMemorySongRepository withOneDefaultSongAndTheme(String theme) {
        Song song = new Song("artist", "songTitle", "releaseTitle", "Song with theme " + theme, List.of(theme));
        return createSongRepositoryWith(song);
    }

    @NotNull
    private static InMemorySongRepository createSongRepositoryWith(Song... songs) {
        InMemorySongRepository repository = InMemorySongRepository.createEmpty();
        for (Song song : songs) {
            repository.add(song);
        }
        return repository;
    }

    @Test
    public void searchForThemeThatDoesNotExistReturnsNoResults() throws Exception {
        InMemorySongRepository songRepository = withOneDefaultSongAndTheme("new years");

        List<String> foundSongs = songRepository.songTitlesByTheme("Applesauce");

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
        Song song = SongFactory.createSong("auld lang syne", songTheme);
        InMemorySongRepository songRepository = createSongRepositoryWith(song);

        List<String> foundSong = songRepository.songTitlesByTheme(requestedTheme);

        assertThat(foundSong)
                .containsExactly("auld lang syne");
    }

    @Test
    void searchForThemeFindsMultipleMatchingSongTitles() throws Exception {
        InMemorySongRepository songRepository = createSongRepositoryWith(
                SongFactory.createSong("auld lang syne", "new years"),
                SongFactory.createSong("New Year's Eve In A Haunted House", "new years"));

        List<String> foundSongs = songRepository.songTitlesByTheme("New Years");

        assertThat(foundSongs)
                .containsExactly("auld lang syne",
                                 "New Year's Eve In A Haunted House");
    }

    @Test
    void searchByThemeFindsMultipleMatchingSongs() throws Exception {
        SongRepository songRepository = createSongRepositoryWith(
                SongFactory.createSong("auld lang syne", "new years"),
                SongFactory.createSong("New Year's Eve In A Haunted House", "new years"));

        List<Song> foundSongs = songRepository.findByTheme("New Years");

        assertThat(foundSongs)
                .containsExactly(
                        SongFactory.createSong("auld lang syne", "new years"),
                        SongFactory.createSong("New Year's Eve In A Haunted House", "new years"));
    }

    @Test
    void forSongsWithDifferentThemesSearchFindsAllSongs() throws Exception {
        InMemorySongRepository songRepository = createSongRepositoryWith(
                SongFactory.createSong("auld lang syne", "new years"),
                SongFactory.createSong("The Christmas Tree is on Fire", "christmas"));

        assertThat(songRepository.songTitlesByTheme("new years"))
                .containsExactly("auld lang syne");
        assertThat(songRepository.songTitlesByTheme("Christmas"))
                .containsExactly("The Christmas Tree is on Fire");
    }

    @Test
    void songWithMultipleThemesIsFoundByItsSecondTheme() throws Exception {
        SongRepository songRepository = createSongRepositoryWith(
                SongFactory.createSong("Nightmare Before Christmas", List.of("Christmas", "Halloween")));

        List<Song> songsFound = songRepository.findByTheme("halloween");

        assertThat(songsFound)
                .extracting(Song::songTitle)
                .containsExactly("Nightmare Before Christmas");
    }
}