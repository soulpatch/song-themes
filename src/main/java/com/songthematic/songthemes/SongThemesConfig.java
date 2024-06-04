package com.songthematic.songthemes;

import com.songthematic.songthemes.adapter.out.jdbc.JdbcSongRepository;
import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcAdapter;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.application.port.SongRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SongThemesConfig {

    @Bean
    public SongService songService(JdbcSongRepository jdbcSongRepository) {
        SongRepository songJdbcAdapter = new SongJdbcAdapter(jdbcSongRepository);
        return new SongService(songJdbcAdapter);
    }
}
