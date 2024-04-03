package com.songthematic.songthemes.application;

import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultAssert extends AbstractAssert<ResultAssert, Result> {

    public ResultAssert(Result result) {
        super(result, ResultAssert.class);
    }

    void isSuccess() {
        assertThat(actual.isSuccess())
                .isTrue();
    }

    public void isFailure() {
        assertThat(actual.isSuccess())
                .as("Should not have succeeded but it did")
                .isFalse();
    }
}
