package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CsvSongParserTest {
    @Test
    void parseSongFromCsv() throws Exception {
        String csvSongs = "\"Artist\",\"SongTitle\",\"ReleaseTitle\",\"ReleaseType\",\"Theme1\"";

        // can use text blocks, but the last line has to be escaped to avoid a new line at the end
//        String csvSongs = """
//                          "Artist","SongTitle","ReleaseTitle","ReleaseType","Theme1"\
//                          """;

        CsvSongParser csvSongParser = new CsvSongParser();
        List<Song> songs = csvSongParser.parse(csvSongs);

        assertThat(songs)
                .containsExactly(SongFactory.createSong("SongTitle", "Theme1"));
    }
}