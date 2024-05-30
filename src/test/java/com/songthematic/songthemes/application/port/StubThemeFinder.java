package com.songthematic.songthemes.application.port;

import java.util.List;

public class StubThemeFinder implements ThemeFinder {
    public StubThemeFinder() {
    }

    @Override
    public List<String> allThemes() {
        return List.of("Halloween", "New Years");
    }
}