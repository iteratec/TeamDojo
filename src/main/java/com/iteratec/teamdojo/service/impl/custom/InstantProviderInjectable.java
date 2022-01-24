package com.iteratec.teamdojo.service.impl.custom;

import lombok.NonNull;

/**
 * Implementations provide an injection point for {@link InstantProvider instant providers}
 */
public interface InstantProviderInjectable {
    /**
     * Injection point for instant provider
     * <p>
     * This is necessary because time is a side effect and we need to mock out the default implementation for tests.
     * </p>
     *
     * @param time not {@code null}
     */
    void setTime(InstantProvider time);
}
