package com.iteratec.teamdojo.util;

/**
 * This class provides static helper methods to safely access boxed types
 * <p>
 * What's the problem? If you have a method with a parameter of type {@code Integer} insteadof{@code int} you must
 * expect that this value may be {@code null} or you will have null pointer exceptions which crash your application.
 * But you will not clutter up all your code with noisy null checks. Best option is to use primitive types instead of the
 * boxed types, but sometimes this in not feasible. Here in our case because it would lead into changes in generated code.
 * But we can use the static methods here for convenience:
 * </p>
 *
 * <pre>
 *     {@code}
 *     final Integer foo = null;
 *
 *     // ...
 *
 *     // Does not npe like:
 *     final int bar = NullSafeAccess.get(foo) * 42;
 *
 *     // Will npe:
 *     final baz = foo * 42;
 *     {@code}
 * </pre>
 */
public final class NullSafeAccess {

    /**
     * Pure static utility class.
     */
    private NullSafeAccess() {
        super();
        throw new UnsupportedOperationException("This class is not intended to be instantiated via reflection!");
    }

    public static int get(final Integer in) {
        if (in == null) {
            return 0;
        }

        return in;
    }

    public static double get(final Double in) {
        if (in == null) {
            return 0;
        }

        return in;
    }
}
