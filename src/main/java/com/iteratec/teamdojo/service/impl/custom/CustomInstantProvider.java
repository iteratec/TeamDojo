package com.iteratec.teamdojo.service.impl.custom;

import java.time.Instant;

/**
 * Interface to provide instant
 * <p>
 * This is to encapsulate the behaviour with side effects to get current time to make this service testable. To use this provider simply use the default constant in your service:
 * </p>
 * <pre>
 *      {@code}
 *      public class MyServiceImpl extends MyService {
 *          &#064;NonNull
 *          &#064;Setter(AccessLevel.PROTECTED)
 *          private CustomInstantProvider time = CustomInstantProvider.DEFAULT;
 *      }
 *
 *          &#064;Override
 *          public ImageDTO save(final ImageDTO image) {
 *              final var updatedAt = time.now();
 *              // ....
 *          }
 *      {@code}
 * </pre>
 *
 * <p>The setter is necessary to swap out this default implementation with a mock implementation in unit tests:</p>
 *
 * <pre>
 *      {@code}
 *      &#064;Test
 *      void save() {
 *          // Create a stable instant we use in the whole test to avoid time shifts
 *          final var now = Instant.now();
 *          final var time = mock(CustomInstantProvider.class);
 *          when(time.now()).thenReturn(now);
 *          sut.setTime(time);
 *          // ...
 *      }
 *      {@code}
 * </pre>
 */
interface CustomInstantProvider {
    /**
     * Default implementation to use.
     */
    CustomInstantProvider DEFAULT = new CustomInstantProvider() {};

    /**
     * Provides the current time when invoking it.
     *
     * @return never {@code null}
     */
    default Instant now() {
        return Instant.now();
    }
}
