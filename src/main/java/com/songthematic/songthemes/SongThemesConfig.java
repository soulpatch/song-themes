package com.songthematic.songthemes;

import com.songthematic.songthemes.adapter.out.jdbc.JdbcSongRepository;
import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcAdapter;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.application.port.SongRepository;
import com.songthematic.songthemes.application.port.ThemeFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SongThemesConfig {

    @Bean
    public SongService songService(JdbcSongRepository jdbcSongRepository) {
        SongRepository songJdbcAdapter = new SongJdbcAdapter(jdbcSongRepository);
        return new SongService(songJdbcAdapter);
    }

    @Bean
    public ThemeFinder themeFinder() {
        return new ThemeFinder() {
            @Override
            public List<String> allThemes() {
                return List.of();
            }
        };
    }
}
