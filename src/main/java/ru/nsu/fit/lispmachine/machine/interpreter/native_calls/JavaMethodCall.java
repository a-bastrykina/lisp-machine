package ru.nsu.fit.lispmachine.machine.interpreter.native_calls;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeIdentifier;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;

public class JavaMethodCall extends NativeCall {

    private final SchemeIdentifier className;
    private final SchemeIdentifier methodName;
    private final List<Expression> schemeParameters;

    public JavaMethodCall(SchemeIdentifier className, SchemeIdentifier methodName, List<Expression> schemeParameters) {
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
            var method = Arrays.stream(clazz.getMethods()).filter(m -> m.getName().equals(methodName)).findFirst().orElse(null);
            if (method == null) {
                throw new IllegalArgumentException("Unknown method " + methodName + " in " + className);
            }
            var param_types = method.getParameterTypes();
            if (param_types.length != args.size()) {
                throw new IllegalArgumentException("Method takes " + param_types.length + " parameters, " + args.size() + " given");
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

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown class " + className);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
