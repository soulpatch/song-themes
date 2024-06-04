package com.songthematic.songthemes.adapter.out.jdbc;

import com.songthematic.songthemes.application.port.ThemeFinder;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JdbcThemeFinder extends CrudRepository<SongDbo, Long>, ThemeFinder {

    @Query("SELECT DISTINCT unnest(themes) AS theme FROM songs ORDER BY theme ASC")
    List<String> allThemes();
}
