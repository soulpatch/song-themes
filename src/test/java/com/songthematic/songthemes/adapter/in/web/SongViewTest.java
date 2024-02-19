package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.SongFactory;
import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongViewTest {

    @Test
    void convertsFoundSongsToSongViews() throws Exception {
        List<Song> songs = List.of(SongFactory.createSong("This Will Be Our Year", "new years"),
                                   SongFactory.createSong("Funky New Year", "new years"));
        List<SongView> songViews = SongView.from(songs);

        assertThat(songViews)
                .containsExactly(new SongView("This Will Be Our Year"),
                                 new SongView("Funky New Year"));
    }
}