package com.songthematic.songthemes;

import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled("Ted to fill in workaround for this")
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
                Artist\tSong Title\tRelease Title\tRelease Type\tNotes\tTheme1\tTheme2\tTheme3\tTheme4\tContributor
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
