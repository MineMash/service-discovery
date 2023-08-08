package dev.minemesh.servicediscovery.common.util;

public final class Precheck {

    private Precheck() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> T checkNotNull(String argumentName, T obj) {
        if (obj != null) return obj;

        throw new IllegalArgumentException("Argument '%s' must not be null.".formatted(argumentName));
    }

    public static void checkCondition(String description, boolean condition) {
        if (condition) return;

        throw new IllegalStateException("Condition '%s' is false.".formatted(description));
    }

}
