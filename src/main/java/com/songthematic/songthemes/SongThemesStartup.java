package com.songthematic.songthemes;

import com.songthematic.songthemes.domain.Song;
import com.songthematic.songthemes.domain.SongSearcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SongThemesStartup {

    public static void main(String[] args) {
        SpringApplication.run(SongThemesStartup.class, args);
    }

    @Bean
    public SongSearcher songSearcher() {
        String theme = "new years";
        return SongSearcher.createSongSearcher(new Song("artist", "songTitle", "releaseTitle", "auld lang syne", List.of(theme)),
                                               new Song("artist", "songTitle", "releaseTitle", "New Year's Eve In A Haunted House", List.of(theme)));
    }
}
