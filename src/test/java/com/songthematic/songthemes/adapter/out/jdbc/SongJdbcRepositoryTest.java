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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest(properties = {
        "spring.test.database.replace=NONE"
})
@Testcontainers(disabledWithoutDocker = true)
@Tag("database")
class SongJdbcRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    SongJdbcRepository songJdbcRepository;

    @Test
    void canReadAndWriteDbo() throws Exception {
        SongDbo songDbo = new SongDbo("Yellowman", "Donate Money", "Fantastic Yellowman", "", List.of("Money", "Donate"));

        SongDbo savedDbo = songJdbcRepository.save(songDbo);

        assertThat(savedDbo.getId())
                .isNotNull();

        savedDbo.setSongTitle("Donate Money!");
        songJdbcRepository.save(songDbo);

        Optional<SongDbo> foundDbo = songJdbcRepository.findById(savedDbo.getId());

        assertThat(foundDbo)
                .isPresent()
                .get()
                .extracting(SongDbo::getSongTitle)
                .isEqualTo("Donate Money!");
    }

    @Test
    void findByThemeWorksRegardlessOfCase() throws Exception {
        SongDbo songDbo = new SongDbo("Yellowman", "Donate Money", "Fantastic Yellowman", "", List.of("Money", "Donate"));
        SongDbo songDbo2 = new SongDbo("Mojo Nixon", "Where the Hell's My Money?", "Frenzy", "", List.of("Money"));
        SongDbo songDbo3 = new SongDbo("Peggy Lee", "My Heart Belongs To Daddy", "The Best Of Peggy Lee 1952-1956", "", List.of("Daddy"));

        songJdbcRepository.save(songDbo);
        songJdbcRepository.save(songDbo2);
        songJdbcRepository.save(songDbo3);

        List<SongDbo> foundSongs = songJdbcRepository.findByThemeIgnoreCase("money");

        assertThat(foundSongs)
                .hasSize(2)
                .extracting(SongDbo::getSongTitle)
                .containsExactlyInAnyOrder("Donate Money", "Where the Hell's My Money?");
    }
}