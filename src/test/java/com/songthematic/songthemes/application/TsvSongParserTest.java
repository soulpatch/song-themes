package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TsvSongParserTest {
    @Test
    void parseSongFromTabSeparatedValues() throws Exception {
        // Artist\tSong Title\tRelease Title\tRelease Type\tNotes\tTheme1\tTheme2\tTheme3\tTheme4\tContributor
        String tsvSongs = "Earth, Wind & Fire\tGratitude\tReleaseTitle\tReleaseType\tSkippedNotes\tThank You\tThanks\tGratitude\tTheme4\tRizzi";

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvSongs);

        assertThat(songs)
                .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "ReleaseTitle", "ReleaseType", List.of("Thank You", "Thanks", "Gratitude", "Theme4")));
    }

    @Test
    void parseSongWithOnlyOneThemeHasOneTheme() throws Exception {
        String tsvSongs = "DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\t\tDontCareContributor";

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvSongs);

        assertThat(songs)
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
    }

    @Test
    void stopAddingThemesWhenHitFirstBlankTheme() throws Exception {
        String tsvSongs = "DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\tIgnoredTheme\tDontCareContributor";

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvSongs);

        assertThat(songs)
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
    }

    @Test
    void handlesRowsWithNotEnoughColumns() throws Exception {
        String tsvTwoSongs = """
                Artist\tSongTitle
                Artist2\tSongTitle2\tReleaseTitle
                """;

        TsvSongParser tsvSongParser = new TsvSongParser();

        Result result = tsvSongParser.parseWithResult(tsvTwoSongs);
        assertThat(result.isSuccess())
                .isFalse();
    }

    @Test
    void returnsFailureResultForRowWithNotEnoughColumns() throws Exception {
        String tsvSong = "Artist\tSongTitle";
        TsvSongParser tsvSongParser = new TsvSongParser();

        Result songResult = tsvSongParser.parseSong(tsvSong);

        assertThat(songResult.isSuccess())
                .isFalse();
        assertThat(songResult.failureMessage())
                .isEqualTo("Number of columns was: 2, must have at least 9, row contains: [Artist, SongTitle]");
    }


    @Test
    void skipEmptyAndBlankRows() throws Exception {
        String tsvThreeRows = """
                Earth, Wind & Fire\tGratitude\t\t\t\tThank You\tThanks\tGratitude\t\tRizzi

                \s
                Joey Ramone\tWhat A Wonderful World\tDon’t Worry About Me\t\t\tThank You\tThanks\tGratitude\tJoy\tRizzi
                \t
                """;

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvThreeRows);

        assertThat(songs)
                .as("expecting 2 songs")
                .hasSize(2);
    }

    @Test
    void parseMultipleSongsWithSuccessfulResult() throws Exception {
        String tsvTwoSongs = """
                Earth, Wind & Fire\tGratitude\t\t\t\tThank You\tThanks\tGratitude\t\tRizzi
                Joey Ramone\tWhat A Wonderful World\tDon’t Worry About Me\t\t\tThank You\tThanks\tGratitude\tJoy\tRizzi
                """;

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseWithResult(tsvTwoSongs);

        assertThat(result.isSuccess())
                .isTrue();

    }

    @Test
    void parseMultipleSongs() throws Exception {
        String tsvTwoSongs = """
                Earth, Wind & Fire\tGratitude\t\t\t\tThank You\tThanks\tGratitude\t\tRizzi
                Joey Ramone\tWhat A Wonderful World\tDon’t Worry About Me\t\t\tThank You\tThanks\tGratitude\tJoy\tRizzi
                """;

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvTwoSongs);

        assertThat(songs)
                .as("expecting 2 songs")
                .hasSize(2)
                .as("unexpected song content")
                .containsExactlyInAnyOrder(
                        new Song("Earth, Wind & Fire", "Gratitude", "", "", List.of("Thank You", "Thanks", "Gratitude")),
                        new Song("Joey Ramone", "What A Wonderful World", "Don’t Worry About Me", "", List.of("Thank You", "Thanks", "Gratitude", "Joy"))
                );
    }
}