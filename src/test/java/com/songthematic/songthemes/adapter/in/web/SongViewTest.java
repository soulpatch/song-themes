package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongViewTest {

    @Test
    void convertsFoundSongsToSongViews() throws Exception {

        List<Song> songs = List.of(new Song("Flesh Eaters", "Digging My Grave", "A Minute To Pray A Second To Die", "", List.of("Halloween", "Death", "Graveyards")),
                                   new Song("Revillos", "Jack The Ripper", "From The Freezer", "", List.of("Halloween")));
        List<SongView> songViews = SongView.from(songs);

        assertThat(songViews)
                .containsExactly(new SongView("Flesh Eaters", "Digging My Grave", "A Minute To Pray A Second To Die", "", "[Halloween, Death, Graveyards]"),
                                 new SongView("Revillos", "Jack The Ripper", "From The Freezer", "", "[Halloween]"));
    }
}