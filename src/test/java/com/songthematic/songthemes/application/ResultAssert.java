package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.CollectionAssert;
import org.assertj.core.internal.Booleans;

public class ResultAssert extends AbstractAssert<ResultAssert, Result<Song>> {

    public ResultAssert(Result result) {
        super(result, ResultAssert.class);
    }

    public ResultAssert isSuccess() {
        isNotNull();

        Booleans.instance().assertEqual(info, actual.isSuccess(), true);

        return myself;
    }

    /**
     * WARNING: currently overrides any as() calls
     */
    public ResultAssert isFailure() {
        isNotNull();

        describedAs("Should not have succeeded but it did");
        Booleans.instance().assertEqual(info, actual.isSuccess(), false);

        return myself;
    }

    public CollectionAssert<Song> values() {
        return new CollectionAssert<>(actual.values());
    }
}
