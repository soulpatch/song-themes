package com.songthematic.songthemes.application.port;

import java.util.List;

public class ThemeFinder {
    public ThemeFinder() {
    }

    public List<String> findAll() {
        return List.of("Halloween", "New Years");
    }
}