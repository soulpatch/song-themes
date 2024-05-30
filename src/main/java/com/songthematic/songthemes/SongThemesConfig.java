package com.songthematic.songthemes;

import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcAdapter;
import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcRepository;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.application.port.SongRepository;
import com.songthematic.songthemes.application.port.ThemeFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SongThemesConfig {

    @Bean
    public SongService songService(SongJdbcRepository songJdbcRepository) {
        SongRepository songJdbcAdapter = new SongJdbcAdapter(songJdbcRepository);
        return new SongService(songJdbcAdapter);
    }

    @Bean
    public ThemeFinder themeFinder() {
        return new ThemeFinder();
    }
}
