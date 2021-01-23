package ru.nsu.fit.lispmachine.machine.interpreter;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CondClause implements Expression {


    private final List<Expression> clauses;
    private final Expression elseClause;

    public CondClause(List<Expression> clauses, Expression elseClause) {
        this.clauses = clauses;
        this.elseClause = elseClause;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        var carName = new SchemeIdentifier("car");
        var cdrName = new SchemeIdentifier("cdr");
        for (Expression clause : clauses) {
            var predicate = new Application(carName, List.of(clause)).evaluate(context);
            var predRes = predicate.evaluate(context);
            if (predicate.evaluate(context).isTrue()) {
                var body = (SchemeList) new Application(cdrName, List.of(clause)).evaluate(context);
                Expression res = new SchemeBool(true);
                for (Expression value : body.getValues()) {
                    res = value.evaluate(context);
                }
                return res;
            }
        }
        if (elseClause != null) {
            return elseClause.evaluate(context);
        }
        return new SchemeList(new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CondClause that = (CondClause) o;
        return Objects.equals(clauses, that.clauses) && Objects.equals(elseClause, that.elseClause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clauses, elseClause);
    }
}
