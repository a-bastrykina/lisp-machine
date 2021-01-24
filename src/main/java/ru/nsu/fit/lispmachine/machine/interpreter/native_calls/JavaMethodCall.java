package ru.nsu.fit.lispmachine.machine.interpreter.native_calls;

import ru.nsu.fit.lispmachine.exceptions.CompatibilityException;
import ru.nsu.fit.lispmachine.machine.execution_context.ExecutionContext;
import ru.nsu.fit.lispmachine.machine.interpreter.Expression;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeNumber;
import ru.nsu.fit.lispmachine.machine.interpreter.SchemeString;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A class to represent Java Method call expression.
 * Example in Scheme:
 * (java-call "java.lang.Double" "sum" 5 10)
 */
public class JavaMethodCall extends NativeCall {
    private final SchemeString className;
    private final SchemeString methodName;
    private final List<Expression> schemeParameters;
    private final HashMap<String, Function<Object, Expression>> typeMap = new HashMap<>();

    /**
     * @param className
     * @param methodName
     * @param schemeParameters method parameters
     */
    public JavaMethodCall(SchemeString className, SchemeString methodName, List<Expression> schemeParameters) {
        this.className = className;
        this.methodName = methodName;
        this.schemeParameters = schemeParameters;

        typeMap.put("double", e -> new SchemeNumber((Number) e));
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

            var returnedValue = method.invoke(null, casted.toArray());
            var returnValueType = method.getReturnType().toString();
            return typeMap.getOrDefault(returnValueType, e -> new SchemeString(e == null ? "'()" : e.toString())).apply(returnedValue);

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown class " + className);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new CompatibilityException(e.getCause());
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
