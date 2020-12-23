package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.Objects;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

public class SchemerString implements Expression {
    private final String value;

    public SchemerString(String value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        return context.lookupVariable(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SchemerString string = (SchemerString) o;
        return Objects.equals(value, string.value);
    }

    @Override public int hashCode() {
        return Objects.hash(value);
    }
}
