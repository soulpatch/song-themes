package com.songthematic.songthemes.application.port;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StubThemeFinder implements ThemeFinder {

    private final List<String> themes;

    public StubThemeFinder(String... themeArray) {
        themes = Arrays.asList(themeArray);
    }

    @Override
    public List<String> allUniqueThemesAlphabetically() {
        Collections.sort(themes);
        return themes;
    }

    @Override
    public List<String> startsWith(String themeQuery) {
        return themes.stream()
                     .filter(theme -> theme.startsWith(themeQuery))
                     .toList();
    }
}