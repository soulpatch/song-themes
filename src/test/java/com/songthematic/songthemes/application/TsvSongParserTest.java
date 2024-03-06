package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TsvSongParserTest {
    @Test
    void parseSongFromTabSeparatedValues() throws Exception {
        String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("Earth, Wind & Fire\tGratitude\tReleaseTitle\tReleaseType\tSkippedNotes\tThank You\tThanks\tGratitude\tTheme4\tRizzi");

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parse(tsvSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "ReleaseTitle", "ReleaseType", List.of("Thank You", "Thanks", "Gratitude", "Theme4")));
    }

    @Test
    void parseSongWithOnlyOneThemeHasOneTheme() throws Exception {
        String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\t\tDontCareContributor");

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parse(tsvSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
    }

    @Test
    void stopAddingThemesWhenHitFirstBlankTheme() throws Exception {
        String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\tIgnoredTheme\tDontCareContributor");

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parse(tsvSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
    }

    @Test
    void handlesRowsWithNotEnoughColumns() throws Exception {
        String tsvTwoSongs = """
                Husker Du\tGreen Eyes
                Kinks\tAround the Dial\tGive The People What They Want
                """;
        TsvSongParser tsvSongParser = new TsvSongParser();

        Result result = tsvSongParser.parse(tsvTwoSongs);

        assertThat(result.isSuccess())
                .isFalse();
    }

    @Test
    void parseSingleSongReturnsFailureResultForRowWithNotEnoughColumns() throws Exception {
        String tsvSong = "Husker Du\tGreen Eyes";
        TsvSongParser tsvSongParser = new TsvSongParser();

        Result songResult = tsvSongParser.parseSong(tsvSong);

        assertThat(songResult.isSuccess())
                .isFalse();
        assertThat(songResult.failureMessage())
                .isEqualTo("Number of columns was: 2, must have at least 9, row contains: [Husker Du, Green Eyes]");
    }


    @Test
    void skipEmptyAndBlankRows() throws Exception {
        String tsvThreeRows = TsvSongFactory.createTsvSongsWithHeader("""
                                                               Earth, Wind & Fire\tGratitude\t\t\t\tThank You\tThanks\tGratitude\t\tRizzi
                                                                       
                                                               \s
                                                               Joey Ramone\tWhat A Wonderful World\tDon’t Worry About Me\t\t\tThank You\tThanks\tGratitude\tJoy\tRizzi
                                                               \t
                                                               """);
        TsvSongParser tsvSongParser = new TsvSongParser();

        Result result = tsvSongParser.parse(tsvThreeRows);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .as("expecting 2 songs")
                .hasSize(2);
    }

    @Test
    void parseMultipleSongs() throws Exception {
        String tsvTwoSongs = TsvSongFactory.createTsvSongsWithHeader("""
                                                              Earth, Wind & Fire\tGratitude\t\t\t\tThank You\tThanks\tGratitude\t\tRizzi
                                                              Joey Ramone\tWhat A Wonderful World\tDon’t Worry About Me\t\t\tThank You\tThanks\tGratitude\tJoy\tRizzi
                                                              """);
        TsvSongParser tsvSongParser = new TsvSongParser();

        Result result = tsvSongParser.parse(tsvTwoSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .as("expecting 2 songs")
                .hasSize(2)
                .as("unexpected song content")
                .containsExactlyInAnyOrder(
                        new Song("Earth, Wind & Fire", "Gratitude", "", "", List.of("Thank You", "Thanks", "Gratitude")),
                        new Song("Joey Ramone", "What A Wonderful World", "Don’t Worry About Me", "", List.of("Thank You", "Thanks", "Gratitude", "Joy"))
                );
    }
}