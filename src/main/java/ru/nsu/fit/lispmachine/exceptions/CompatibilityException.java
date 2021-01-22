package ru.nsu.fit.lispmachine.exceptions;

public class CompatibilityException extends RuntimeException {
    private final Throwable rootCase;

    public CompatibilityException(Throwable rootCase) {

        this.rootCase = rootCase;
    }

    public Throwable getRootCase() {
        return rootCase;
    }
}
