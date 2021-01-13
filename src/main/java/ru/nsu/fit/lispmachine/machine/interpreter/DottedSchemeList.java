package ru.nsu.fit.lispmachine.machine.interpreter;

import java.util.List;
import java.util.stream.Collectors;

public class DottedSchemeList extends SchemeList {

    public DottedSchemeList(List<Expression> values) {
        super(values);
    }

    @Override
    public String toString() {
        var values = getValues();
        var last = values.get(values.size() - 1);
        StringBuilder res = new StringBuilder("(");
        for (int i = 0; i < values.size() - 1; i++) {
            res.append(values.get(i)).append(" ");
        }
        res.append(". ").append(last).append(")");
        return res.toString();
    }
}
