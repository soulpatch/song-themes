package com.songthematic.songthemes;

import com.songthematic.songthemes.application.SongService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("io")
class SongThemesStartupTests {

    @Autowired
    SongService songService;

    @Test
    void contextLoads() {
        // looks empty, but tests autowiring
    }

    @Test
    void endToEndImportedSongsAreFound() throws Exception {
        // spring boot does the setup

        String tsvSongs = "DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\t\tDontCareContributor";
        songService.importSongs(tsvSongs);

        assertThat(songService.searchByTheme("Thank You"))
                .hasSize(1);
    }
}
