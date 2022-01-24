package com.iteratec.teamdojo.test.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class StaticInstantProviderTest {

    @Test
    void now_alwaysReturnsFixedTimeOnConsecutiveCalls() {
        final var now = Instant.now();
        final var sut = StaticInstantProvider.forFixedTime(now);

        assertAll(
            () -> assertThat(sut.now()).isEqualTo(now),
            () -> assertThat(sut.now()).isEqualTo(now),
            () -> assertThat(sut.now()).isEqualTo(now)
        );
    }
}
