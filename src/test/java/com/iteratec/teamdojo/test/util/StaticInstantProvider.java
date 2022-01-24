package com.iteratec.teamdojo.test.util;

import com.iteratec.teamdojo.service.impl.custom.InstantProvider;
import java.time.Instant;
import lombok.NonNull;

/**
 * Implementation for tests which always returns a fixed instant
 */
public final class StaticInstantProvider implements InstantProvider {

    private final Instant now;

    /**
     * Use {@link #forFixedTime(Instant)} static factory} method
     * <p>
     * We do not want to expose to much of this type so we provide a factory method which returns the interface type.
     * </p>
     *
     * @param now not {@code null}
     */
    private StaticInstantProvider(final Instant now) {
        super();
        this.now = now;
    }

    /**
     * Factory to create new instances
     *
     * @param time {@code not null}
     * @return never {@code null}
     */
    public static InstantProvider forFixedTime(final @NonNull Instant time) {
        return new StaticInstantProvider(time);
    }

    @Override
    public Instant now() {
        return now;
    }
}
