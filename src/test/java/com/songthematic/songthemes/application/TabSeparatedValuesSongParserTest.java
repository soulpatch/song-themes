package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TabSeparatedValuesSongParserTest {
    @Test
    void parseSongFromTabSeparatedValues() throws Exception {
        // the last line has to be escaped to avoid a new line at the end
        String tsvSongs = "Earth, Wind & Fire	Gratitude				Thank You	Thanks	Gratitude		Rizzi";

        TsvSongParser tsvSongParser = new TsvSongParser();
        List<Song> songs = tsvSongParser.parse(tsvSongs);

        assertThat(songs)
                .extracting(Song::artist)
                .containsExactly("Earth, Wind & Fire");
    }
}