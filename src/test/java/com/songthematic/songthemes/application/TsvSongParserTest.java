package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TsvSongParserTest {

    // * number of columns in header match number of columns for song data?
    // * Song has required fields
    // * Make sure Songs is not empty when Success is True

    @Test
    void parseAllReturnFailureWhenFewerThanTwoRows() throws Exception {
        String tsvSongs = "Earth, Wind & Fire\tGratitude\tReleaseTitle\tReleaseType\tSkippedNotes\tThank You\tThanks\tGratitude\tTheme4\tRizzi";

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseAll(tsvSongs);

        assertThat(result.isSuccess())
                .isFalse();
        assertThat(result.failureMessages())
                .containsExactly("Must have at least two rows of import data");
    }

    @Test
    @Disabled("Enable once we return Result.failure for mismatched column count")
    void parseAllReturnsMultipleFailureMessages() throws Exception {
        String tsvTwoMalformedSongs =
                """
                        Artist\tSong Title\tRelease Title
                        Blue Oyster Cult\tDon't Fear The Reaper\tAgents of Fortune
                        Kinks\tAround the Dial\tGive The People What They Want
                        """;

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseAll(tsvTwoMalformedSongs);

        assertThat(result.isSuccess())
                .as("Should not have succeeded but it did")
                .isFalse();
        assertThat(result.failureMessages())
                .hasSize(2)
                .containsExactly("Number of columns was: 3, must have at least 5, row contains: [Blue Oyster Cult, Don't Fear The Reaper, Agents of Fortune]",
                                 "Number of columns was: 3, must have at least 5, row contains: [Kinks, Around the Dial, Give The People What They Want]");
    }

    // * Does the header row have the 8 required columns
    @Test
//    @Disabled("Can enable once the header is fully parsed")
    void parseAllReturnsSuccessWhenHeaderRowHasRequiredColumns() throws Exception {
        String header = "Artist\tSong Title\tTheme1\n";
        String tsvSongs = header + "Earth, Wind & Fire\tGratitude\tThank You";

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseAll(tsvSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.failureMessages())
                .isEmpty();
    }

    @Test
    @Disabled
    void parseAllReturnsFailureWhenMissingRequiredHeaderColumns() throws Exception {

        // assert? isFailure
    }

    @Test
    void parseAllForSingleSongFromTabSeparatedValues() throws Exception {
        String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("Earth, Wind & Fire\tGratitude\tReleaseTitle\tReleaseType\tSkippedNotes\tThank You\tThanks\tGratitude\tTheme4\tRizzi");

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseAll(tsvSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "ReleaseTitle", "ReleaseType", List.of("Thank You", "Thanks", "Gratitude", "Theme4")));
    }

    @Test
    void parseSongWithOnlyOneThemeHasOneTheme() throws Exception {
        String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\t\tDontCareContributor");

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseAll(tsvSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
    }

    @Test
    void stopAddingThemesWhenHitFirstBlankTheme() throws Exception {
        String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\tNo Thanks\tDontCareContributor");

        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseAll(tsvSongs);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(result.songs())
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You", "No Thanks")));
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

        Result result = tsvSongParser.parseAll(tsvThreeRows);

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

        Result result = tsvSongParser.parseAll(tsvTwoSongs);

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

    @Nested
    class ParseSingleSongTest {
        @Test
        @Disabled("Enable once we return Result.failure for mismatched column count")
        void returnsFailureResultForRowWithNotEnoughColumns() throws Exception {
            String tsvSong = "Husker Du\tGreen Eyes";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result songResult = tsvSongParser.parseSong(tsvSong, new ColumnMapper("Artist\tSong Title\tTheme1"));

            assertThat(songResult.isSuccess())
                    .isFalse();
            assertThat(songResult.failureMessages())
                    .containsExactly("Number of columns was: 2, must have at least 3, row contains: [Husker Du, Green Eyes]");
        }

        @Test
        void returnsSuccessForRowWithRequiredColumns() throws Exception {
            String header = "Artist\tSong Title\tTheme1";
            String tsvSong = "Earth, Wind & Fire\tGratitude\tThank You";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result songResult = tsvSongParser.parseSong(tsvSong, new ColumnMapper(header));

            assertThat(songResult.isSuccess())
                    .as("Song with required columns should have succeeded, but did not.")
                    .isTrue();
            assertThat(songResult.songs())
                    .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "", "", List.of("Thank You")));
        }

        @Test
        void returnsSuccessForRowWithRequiredColumnsInDifferentOrder() throws Exception {
            String header = "Song Title\tTheme1\tArtist";
            String tsvSong = "Gratitude\tThank You\tEarth, Wind & Fire";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result songResult = tsvSongParser.parseSong(tsvSong, new ColumnMapper(header));

            assertThat(songResult.isSuccess())
                    .as("Song with required columns should have succeeded, but did not.")
                    .isTrue();
            assertThat(songResult.songs())
                    .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "", "", List.of("Thank You")));
        }
    }
}