package com.songthematic.songthemes.application;

import com.songthematic.songthemes.application.port.SongRepository;
import org.jetbrains.annotations.NotNull;

public class InMemorySongServiceTest extends SongServiceTest {
    @Override
    @NotNull
    protected SongRepository songRepository() {
        return InMemorySongRepository.createEmpty();
    }
}
