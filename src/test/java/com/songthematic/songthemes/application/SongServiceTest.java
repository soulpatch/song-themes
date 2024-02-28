package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongServiceTest {

    @Test
    void multipleSongsAddedAreFoundByTheirTheme() throws Exception {
        SongService songService = SongService.createNull();

        songService.addSong(SongFactory.createSong("This Will Be Our Year", List.of("new years", "2023", "OneMoreTheme")));
        songService.addSong(SongFactory.createSong("Funky New Year", "new years"));

        List<Song> songsFound = songService.searchByTheme("new years");
        assertThat(songsFound)
                .containsExactly(SongFactory.createSong("This Will Be Our Year", List.of("new years", "2023", "OneMoreTheme")),
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
        String row = """
                Artist\tSongTitle\tReleaseTitle\tReleaseType\tSkippedNotes\tTheme1\t\t\tContributor
                Artist2\tSongTitle2\tReleaseTitle2\tReleaseType2\tSkippedNotes2\tTheme12\t\t\tContributor2
                """;
        SongRepository songRepository = SongRepository.createEmpty();
        SongService songService = new SongService(songRepository);

        Result result = songService.importSongs(row);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(songRepository.allSongs())
                .containsExactlyInAnyOrder(SongFactory.createSong("Artist", "SongTitle", "ReleaseTitle", "ReleaseType", "Theme1"),
                                           SongFactory.createSong("Artist2", "SongTitle2", "ReleaseTitle2", "ReleaseType2", "Theme12"));
    }

    @Test
    void bulkAddSongFails() throws Exception {

        // importSongs() will return a Result
    }
}
