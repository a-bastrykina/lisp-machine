package ru.nsu.fit.lispmachine.machine.interpreter.native_calls;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeString;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JavaMethodCall extends NativeCall {

    private final SchemeString className;
    private final SchemeString methodName;
    private final List<Expression> schemeParameters;

    public JavaMethodCall(SchemeString className, SchemeString methodName, List<Expression> schemeParameters) {
        this.className = className;
        this.methodName = methodName;
        this.schemeParameters = schemeParameters;
    }

    @Override
    public Expression evaluate(ExecutionContext context) {
        var args = schemeParameters.stream().map(e -> e.evaluate(context)).collect(Collectors.toList());
        var className = this.className.toString();
        var methodName = this.methodName.toString();
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            var method = Arrays.stream(clazz.getMethods()).filter(m -> m.getName().equals(methodName)).findFirst()
                    .orElse(null);
            if (method == null) {
                throw new IllegalArgumentException("Unknown method " + methodName + " in " + className);
            }
            var param_types = method.getParameterTypes();
            if (param_types.length != args.size()) {
                throw new IllegalArgumentException(
                        "Method takes " + param_types.length + " parameters, " + args.size() + " given");
            }

            var casted = new ArrayList<>();
            for (int i = 0; i < param_types.length; i++) {
                var value = args.get(i).castTo(param_types[i].getName());
                casted.add(value);
            }

            var returned_value = method.invoke(null, casted.toArray());
            var return_value_type = method.getReturnType().toString();
            //todo more types
            if (return_value_type.equals("double")) {
                return new SchemeNumber((Number) returned_value);
            }
            return new SchemeString(returned_value == null? "'()" : returned_value.toString() );

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown class " + className);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JavaMethodCall that = (JavaMethodCall) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(methodName, that.methodName) &&
                Objects.equals(schemeParameters, that.schemeParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, methodName, schemeParameters);
    }
}
