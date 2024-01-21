package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongServiceTest {

    @Test
    void multipleSongsAddedAreFoundByTheirTheme() throws Exception {
        SongService songService = new SongService();

        songService.addSong(SongFactory.createSong("This Will Be Our Year", "new years"));
        songService.addSong(SongFactory.createSong("Funky New Year", "new years"));

        List<Song> songsFound = songService.searchByTheme("new years");
        assertThat(songsFound)
                .containsExactly(SongFactory.createSong("This Will Be Our Year", "new years"),
                                 SongFactory.createSong("Funky New Year", "new years"));
    }

    @Test
    void savedSongsLoadedOnStartup() throws Exception {
        List<Song> songList = new ArrayList<>();
        songList.add(SongFactory.createSong("Baby's on Fire", "Fire"));

        SongRepository songRepository = SongRepository.create(songList);
        SongService songService = new SongService(songRepository);

        assertThat(songService.searchByTheme("fire"))
                .containsExactly(SongFactory.createSong("Baby's on Fire", "Fire"));
    }

    @Test
    void addedSongsAreSavedToRepository() throws Exception {
        List<Song> songList = new ArrayList<>();
        songList.add(SongFactory.createSong("Baby's on Fire", "Fire"));
        SongRepository songRepository = SongRepository.create(songList);
        SongService songService = new SongService(songRepository);

        songService.addSong(SongFactory.createSong("Smokestack Lightning", "Fire"));

        assertThat(songRepository.allSongs())
                .hasSize(2);
    }

    @Test
    void bulkAddSongUsingTsvFormat() throws Exception {
        String row = "Artist\tSongTitle\tReleaseTitle\tReleaseType\tSkippedNotes\tTheme1\t\t\tContributor";
        SongRepository songRepository = SongRepository.createEmpty();
        SongService songService = new SongService(songRepository);

        songService.importSongs(row);

        assertThat(songRepository.allSongs())
                .containsExactly(SongFactory.createSong("Artist","SongTitle","ReleaseTitle","ReleaseType","Theme1"));
    }

}
