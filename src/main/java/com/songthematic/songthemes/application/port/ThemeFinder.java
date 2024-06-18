package com.songthematic.songthemes.application.port;

import java.util.List;

public interface ThemeFinder {
    List<String> allUniqueThemesAlphabetically();

    List<String> startsWithIgnoringCase(String themeQuery);
}
