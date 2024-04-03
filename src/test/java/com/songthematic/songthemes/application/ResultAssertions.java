package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;

public class ResultAssertions extends Assertions {
    @NotNull
    public static ResultAssert assertThat(Result<Song> result) {
        return new ResultAssert(result);
    }
}
