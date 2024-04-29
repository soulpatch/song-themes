package com.songthematic.songthemes;

import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcAdapter;
import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcRepository;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.application.port.SongRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SongThemesStartup {

    public static void main(String[] args) {
        SpringApplication.run(SongThemesStartup.class, args);
    }

    @Bean
    public SongService songService(SongJdbcRepository songJdbcRepository) {
        SongRepository songJdbcAdapter = new SongJdbcAdapter(songJdbcRepository);
        return new SongService(songJdbcAdapter);
    }
}
