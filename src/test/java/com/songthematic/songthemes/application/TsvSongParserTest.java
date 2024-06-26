package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static com.songthematic.songthemes.application.ResultAssertions.assertThat;


class TsvSongParserTest {

    @Nested
    class ParseAll {

        @Nested
        class FailsWith {

            @Test
            void oneFailureMessageWhenMissingRequiredHeaderColumns() throws Exception {
                TsvSongParser tsvSongParser = new TsvSongParser();
                String missingHeaderRow = """
                        Blue Oyster Cult\tDon't Fear The Reaper\tAgents of Fortune
                        Kinks\tAround the Dial\tGive The People What They Want
                        Howlin' Wolf\tEvil\tHis Best: The Chess 50th Anniversary Collection
                        """;

                Result<Song> result = tsvSongParser.parseAll(missingHeaderRow);

                assertThat(result)
                        .isFailure()
                        .failureMessages()
                        .contains("Header is missing the required column(s): [Artist, Song Title, Theme1], header was: [Blue Oyster Cult, Don't Fear The Reaper, Agents of Fortune]");
            }

            @Test
            void fewerThanTwoRows() throws Exception {
                String tsvSongs = "Earth, Wind & Fire\tGratitude\tReleaseTitle\tReleaseType\tSkippedNotes\tThank You\tThanks\tGratitude\tTheme4\tRizzi";

                TsvSongParser tsvSongParser = new TsvSongParser();
                Result<Song> result = tsvSongParser.parseAll(tsvSongs);

                assertThat(result)
                        .isFailure()
                        .failureMessages()
                        .containsExactly("Must have at least two rows of import data");
            }

            @Test
            void multipleFailureMessagesForMultipleProblems() throws Exception {
                String tsvTwoMalformedSongs = """
                        Artist\tSong Title\tRelease Title\tTheme1\tTheme2
                        Blue Oyster Cult\tDon't Fear The Reaper\tAgents of Fortune
                        Kinks\tAround the Dial\tGive The People What They Want
                        """;

                TsvSongParser tsvSongParser = new TsvSongParser();
                Result<Song> result = tsvSongParser.parseAll(tsvTwoMalformedSongs);

                assertThat(result)
                        .isFailure()
                        .failureMessages()
                        .hasSize(2)
                        .containsExactly("Number of columns was 3, row contains: [Blue Oyster Cult, Don't Fear The Reaper, Agents of Fortune]. Must have columns matching the 5 header columns [Artist, Song Title, Release Title, Theme1, Theme2].",
                                         "Number of columns was 3, row contains: [Kinks, Around the Dial, Give The People What They Want]. Must have columns matching the 5 header columns [Artist, Song Title, Release Title, Theme1, Theme2].");
            }
        }

        @Test
        void returnsSuccessWhenHeaderRowHasRequiredColumns() throws Exception {
            String header = "Artist\tSong Title\tTheme1\n";
            String tsvSongs = header + "Earth, Wind & Fire\tGratitude\tThank You";

            TsvSongParser tsvSongParser = new TsvSongParser();
            Result<Song> result = tsvSongParser.parseAll(tsvSongs);

            assertThat(result)
                    .isSuccess();
            assertThat(result.failureMessages())
                    .isEmpty();
        }

        @Test
        void forSingleSongFromTabSeparatedValues() throws Exception {
            String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("Earth, Wind & Fire\tGratitude\tReleaseTitle\tReleaseType\tSkippedNotes\tThank You\tThanks\tGratitude\tTheme4\tRizzi");

            TsvSongParser tsvSongParser = new TsvSongParser();
            Result<Song> result = tsvSongParser.parseAll(tsvSongs);

            assertThat(result)
                    .isSuccess()
                    .successValues()
                    .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "ReleaseTitle", "ReleaseType", List.of("Thank You", "Thanks", "Gratitude", "Theme4")));
        }

        @Test
        void forSongWithOnlyOneThemeHasOneTheme() throws Exception {
            String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\t\tDontCareContributor");

            TsvSongParser tsvSongParser = new TsvSongParser();
            Result<Song> result = tsvSongParser.parseAll(tsvSongs);

            assertThat(result)
                    .isSuccess()
                    .successValues()
                    .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
        }


        @Test
        void stopAddingThemesWhenHitFirstBlankTheme() throws Exception {
            String tsvSongs = TsvSongFactory.createTsvSongsWithHeader("DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\tNo Thanks\tDontCareContributor");

            TsvSongParser tsvSongParser = new TsvSongParser();
            Result<Song> result = tsvSongParser.parseAll(tsvSongs);

            assertThat(result)
                    .isSuccess()
                    .successValues()
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

            Result<Song> result = tsvSongParser.parseAll(tsvThreeRows);

            assertThat(result)
                    .isSuccess()
                    .successValues()
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

            Result<Song> result = tsvSongParser.parseAll(tsvTwoSongs);

            assertThat(result)
                    .isSuccess()
                    .successValues()
                    .as("expecting 2 songs")
                    .hasSize(2)
                    .as("unexpected song content")
                    .containsExactlyInAnyOrder(
                            new Song("Earth, Wind & Fire", "Gratitude", "", "", List.of("Thank You", "Thanks", "Gratitude")),
                            new Song("Joey Ramone", "What A Wonderful World", "Don’t Worry About Me", "", List.of("Thank You", "Thanks", "Gratitude", "Joy"))
                    );
        }


    }

    @Nested
    class ParseSingleSongTest {

        @Test
        void failureResultForRowWithNotEnoughColumns() throws Exception {
            String header = "Artist\tSong Title\tTheme1";
            String tsvSong = "Husker Du\tGreen Eyes";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result<Song> result = tsvSongParser.parseSong(tsvSong, ColumnMapperTest.createColumnMapper(header));

            assertThat(result)
                    .isFailure()
                    .failureMessages()
                    .containsExactly("Number of columns was 2, row contains: [Husker Du, Green Eyes]. Must have columns matching the 3 header columns [Artist, Song Title, Theme1].");
        }

        @ParameterizedTest
        @CsvSource(textBlock = """
                '\tGreen Eyes\tCats', Artist, '[, Green Eyes, Cats]'
                'Husker Du\t\tCats', Song Title, '[Husker Du, , Cats]'
                'Husker Du\tGreen Eyes\t', Theme1, '[Husker Du, Green Eyes, ]'
                """
        )
        void failureResultForSingleEmptyRequiredCell(String tsvSong, String missing, String rowContains) throws Exception {
            String header = "Artist\tSong Title\tTheme1";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result<Song> result = tsvSongParser.parseSong(tsvSong, ColumnMapperTest.createColumnMapper(header));

            assertThat(result)
                    .isFailure()
                    .failureMessages()
                    .containsExactly("Song is missing required %s. Row contains %s."
                                             .formatted(missing, rowContains));
        }

        @Test
        void twoFailureMessagesForTwoEmptyRequiredCells() throws Exception {
            String header = "Artist\tSong Title\tTheme1";
            String tsvSong = "\tGreen Eyes\t";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result<Song> result = tsvSongParser.parseSong(tsvSong, ColumnMapperTest.createColumnMapper(header));

            assertThat(result)
                    .isFailure()
                    .failureMessages()
                    .containsExactly("Song is missing required Artist. Row contains [, Green Eyes, ].",
                                     "Song is missing required Theme1. Row contains [, Green Eyes, ].");
        }

        @Test
        void successForRowWithRequiredColumns() throws Exception {
            String header = "Artist\tSong Title\tTheme1";
            String tsvSong = "Earth, Wind & Fire\tGratitude\tThank You";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result<Song> result = tsvSongParser.parseSong(tsvSong, ColumnMapperTest.createColumnMapper(header));

            assertThat(result)
                    .as("Song with required columns should have succeeded, but did not.")
                    .isSuccess()
                    .successValues()
                    .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "", "", List.of("Thank You")));
        }

        @Test
        void successForRowWithRequiredColumnsInDifferentOrder() throws Exception {
            String headerInDifferentOrder = "Song Title\tTheme1\tArtist";
            String tsvSong = "Gratitude\tThank You\tEarth, Wind & Fire";
            TsvSongParser tsvSongParser = new TsvSongParser();

            Result<Song> result = tsvSongParser.parseSong(tsvSong, ColumnMapperTest.createColumnMapper(headerInDifferentOrder));

            assertThat(result)
                    .as("Song with required columns should have succeeded, but did not.")
                    .isSuccess()
                    .successValues()
                    .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "", "", List.of("Thank You")));
        }

    }
}