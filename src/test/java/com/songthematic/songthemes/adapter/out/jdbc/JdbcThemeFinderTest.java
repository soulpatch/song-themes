package com.songthematic.songthemes.adapter.out.jdbc;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest(properties = {
        "spring.test.database.replace=NONE"
})
@Testcontainers(disabledWithoutDocker = true)
@Tag("database")
class JdbcThemeFinderTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    JdbcSongRepository jdbcSongRepository;

    @Autowired
    JdbcThemeFinder jdbcThemeFinder;

    @Test
    void allThemesReturnedByFindAllForSingleSongWithMultipleThemes() throws Exception {
        SongDbo songDbo = new SongDbo("Yellowman", "Donate Money", "Fantastic Yellowman", "", List.of("Money", "Donate"));
        SongDbo songDbo2 = new SongDbo("Mojo Nixon", "Where the Hell's My Money?", "Frenzy", "", List.of("Money"));
        SongDbo songDbo3 = new SongDbo("Peggy Lee", "My Heart Belongs To Daddy", "The Best Of Peggy Lee 1952-1956", "", List.of("Daddy", "Heart"));
        jdbcSongRepository.save(songDbo);
        jdbcSongRepository.save(songDbo2);
        jdbcSongRepository.save(songDbo3);

        List<String> themes = jdbcThemeFinder.allThemes();

        assertThat(themes)
                .containsExactly("Daddy", "Donate", "Heart", "Money");
    }
}