package com.songthematic.songthemes.adapter.out.jdbc;

import com.songthematic.songthemes.application.SongServiceTestBase;
import com.songthematic.songthemes.application.port.SongRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJdbcTest(properties = {
        "spring.test.database.replace=NONE"
})
@Testcontainers(disabledWithoutDocker = true)
@Tag("database")
public class JdbcSongServiceTest extends SongServiceTestBase {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    SongJdbcRepository songJdbcRepository;

    @BeforeEach
    void beforeEach() {
        songJdbcRepository.deleteAll();
    }

    @Override
    protected @NotNull SongRepository songRepository() {
        return new SongJdbcAdapter(songJdbcRepository);
    }
}
