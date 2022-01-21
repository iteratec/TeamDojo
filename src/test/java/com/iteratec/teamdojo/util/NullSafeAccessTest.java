package com.iteratec.teamdojo.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NullSafeAccessTest {

    @Test
    // We explicitly test for that.
    @SuppressWarnings("UnnecessaryBoxing")
    void getIntegerNullSafe() {
        assertAll(
            () -> assertThat(NullSafeAccess.get((Integer) null)).isEqualTo(0),
            () -> assertThat(NullSafeAccess.get(Integer.valueOf(42))).isEqualTo(42)
        );
    }

    @Test
    // We explicitly test for that.
    @SuppressWarnings("UnnecessaryBoxing")
    void getDoubleNullSafe() {
        assertAll(
            () -> assertThat(NullSafeAccess.get((Double) null)).isEqualTo(0),
            () -> assertThat(NullSafeAccess.get(Double.valueOf(42.23))).isEqualTo(42.23)
        );
    }
}
