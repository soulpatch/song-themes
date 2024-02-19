package com.songthematic.songthemes;

import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

        String tsvSongs = """
                Lead Belly	The Bourgeois Blues				America	Politics	Protest		Rizzi
                Blue Oyster Cult	Don't Fear The Reaper	Agents of Fortune			Halloween	Death			Rizzi
                Beautiful South	Don't Fear The Reaper				Halloween	Death			Rizzi
                                
                """;
        songService.importSongs(tsvSongs);

        List<Song> foundSongs = songService.searchByTheme("Protest");
        assertThat(foundSongs)
                .hasSize(1);
    }
}
