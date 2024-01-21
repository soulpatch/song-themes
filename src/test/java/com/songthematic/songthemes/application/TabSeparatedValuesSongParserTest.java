package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TabSeparatedValuesSongParserTest {
    @Test
    void parseSongFromTabSeparatedValues() throws Exception {
        // Artist	Song Title	Release Title	Release Type	Notes	Theme1	Theme2	Theme3	Theme4	Contributor
        String tsvSongs = "Earth, Wind & Fire	Gratitude	ReleaseTitle	ReleaseType	SkippedNotes	Thank You	Thanks	Gratitude	Theme4	Rizzi";

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvSongs);

        assertThat(songs)
                .containsExactly(new Song("Earth, Wind & Fire", "Gratitude", "ReleaseTitle", "ReleaseType", List.of("Thank You", "Thanks", "Gratitude", "Theme4")));
    }

    @Test
    void parseSongWithOnlyOneThemeHasOneTheme() throws Exception {
        String tsvSongs = "DontCareArtist	DontCareSongTitle	DontCareReleaseTitle	DontCareReleaseType	SkippedNotes	Thank You				DontCareContributor";

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvSongs);

        assertThat(songs)
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
    }

    @Test
    void stopAddingThemesWhenHitFirstBlankTheme() throws Exception {
        String tsvSongs = "DontCareArtist	DontCareSongTitle	DontCareReleaseTitle	DontCareReleaseType	SkippedNotes	Thank You			IgnoredTheme	DontCareContributor";

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvSongs);

        assertThat(songs)
                .containsExactly(new Song("DontCareArtist", "DontCareSongTitle", "DontCareReleaseTitle", "DontCareReleaseType", List.of("Thank You")));
    }

    @Test
    void parseMultipleSongs() throws Exception {


    }
}