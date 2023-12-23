package com.songthematic.songthemes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SongThemesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongThemesApplication.class, args);
    }

    @Bean
    public SongSearcher songSearcher() {
        return SongSearcher.createSongSearcher(new Song("new years", "auld lang syne"));
    }
}
