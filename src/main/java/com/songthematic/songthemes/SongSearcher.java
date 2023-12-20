package com.songthematic.songthemes;

import java.util.Collections;
import java.util.List;

public class SongSearcher {
    public List<String> byTheme(String theme) {
        if (theme.equalsIgnoreCase("new years")) {
            return List.of("auld lang syne");
        }
        return Collections.emptyList();
    }
}
