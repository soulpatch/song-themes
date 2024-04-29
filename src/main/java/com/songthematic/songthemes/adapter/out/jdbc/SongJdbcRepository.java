package com.songthematic.songthemes.adapter.out.jdbc;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongJdbcRepository extends ListCrudRepository<SongDbo, Long> {
    @Query("SELECT * FROM songs WHERE :theme ILIKE ANY(themes)")
    List<SongDbo> findByThemeIgnoreCase(@Param("theme") String theme);
}
