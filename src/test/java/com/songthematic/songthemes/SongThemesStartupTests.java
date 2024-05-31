package com.songthematic.songthemes;

import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({SongThemesStartup.class, TestStartupConfiguration.class})
@Tag("database")
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

// Ensure we have a database available, since Docker Compose won't run when we run tests
// So for tests above, we configure and use the Postgres container
@TestConfiguration(proxyBeanMethods = false)
class TestStartupConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"));
    }

}