package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.junit.jupiter.api.Disabled;
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
    @Disabled("Re-enable when header rows are handled")
    void bulkAddSongUsingTsvFormat() throws Exception {
        String row = """
                Artist\tSong Title\tRelease Title\tRelease Type\tNotes\tTheme1\tTheme2\tTheme3\tTheme4\tContributor
                Screaming Tribesmen\tDate with a Vampyre\t\t\tSingle\tHalloween\tVampires\t\t\tRizzi
                Unnatural Axe\tThey Saved Hitler's Brain\tIs Gonna Kick Your Ass\t\t\tHalloween\t\t\t\tRizzi
                """;
        SongRepository songRepository = SongRepository.createEmpty();
        SongService songService = new SongService(songRepository);

        Result result = songService.importSongs(row);

        assertThat(result.isSuccess())
                .isTrue();
        assertThat(songRepository.allSongs())
                .containsExactlyInAnyOrder(SongFactory.createSong("Screaming Tribesmen", "Date with a Vampyre", "", "", "Halloween", "Vampires"),
                                           SongFactory.createSong("Unnatural Axe", "They Saved Hitler's Brain", "Is Gonna Kick Your Ass", "", "Halloween"));
    }

    @Test
    void bulkAddSongFails() throws Exception {
        String tsvTwoMalformedSongs = """
                Artist\tSongTitle
                Artist2\tSongTitle2\tReleaseTitle
                """;
        SongRepository songRepository = SongRepository.createEmpty();
        SongService songService = new SongService(songRepository);

        Result result = songService.importSongs(tsvTwoMalformedSongs);

        assertThat(result.isSuccess())
                .isFalse();
    }
}
