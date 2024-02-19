package com.songthematic.songthemes;

import com.songthematic.songthemes.application.SongRepository;
import com.songthematic.songthemes.application.SongService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SongThemesStartup {

    public static void main(String[] args) {
        SpringApplication.run(SongThemesStartup.class, args);
    }

    @Bean
    public SongService songService() {
        return new SongService(SongRepository.createEmpty());
    }
}
