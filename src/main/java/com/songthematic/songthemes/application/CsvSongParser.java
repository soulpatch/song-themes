package com.songthematic.songthemes.application;

import com.opencsv.CSVParser;
import com.songthematic.songthemes.domain.Song;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CsvSongParser {

    public List<Song> parse(String csvSongs) {
        CSVParser csvParser = new CSVParser();
        try {
            String[] columns = csvParser.parseLine(csvSongs);
            Song song = new Song("", "", "", columns[1], List.of(columns[4]));
            return List.of(song);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
