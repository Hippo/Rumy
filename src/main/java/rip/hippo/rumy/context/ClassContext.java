package rip.hippo.rumy.context;

import rip.hippo.rumy.pattern.ParameterTypePatternMatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static rip.hippo.rumy.pattern.ParameterTypePatternMatcher.EMPTY;

/**
 * @author Hippo
 * @version 1.1.0, 7/13/21
 * @since 1.0.0
 */
public final class ClassContext {

    private final Class<?> parent;
    private final Object instance;

    private static final Map<String, Field> FIELD_CACHE = new HashMap<>();
    private static final Map<String, Method> METHOD_CACHE = new HashMap<>();

    public ClassContext(Class<?> parent) {
        this(parent, null);
    }

    public ClassContext(Class<?> parent, Object instance) {
        Objects.requireNonNull(parent);

        this.parent = parent;
        this.instance = instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        try {
            return (T) getField(name).get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String name, Object value) {
        try {
            Field field = getField(name);
            if (Modifier.isFinal(field.getModifiers())) {
                Field modifiers = Field.class.getDeclaredField("modifiers");
                modifiers.setAccessible(true);
                modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            }
            field.set(instance, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T invoke(String name) {
        return invoke(name, EMPTY);
    }


    @SuppressWarnings("unchecked")
    public <T> T invoke(String name, ParameterTypePatternMatcher parameterTypePatternMatcher) {
        try {
            return (T) getMethod(name, parameterTypePatternMatcher).invoke(instance, parameterTypePatternMatcher.getParameters());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public Field getField(String name) {
        return FIELD_CACHE.computeIfAbsent(name, ignored -> {
            Class<?> current = this.parent;
            while (current != null) {
                try {
                    Field field = current.getDeclaredField(name);
                    field.setAccessible(true);
                    return field;
                } catch (ReflectiveOperationException ignored1) {}
                current = current.getSuperclass();
            }
            throw new RuntimeException(String.format("Could not find field %s", name));
        });
    }

    public Method getMethod(String name, ParameterTypePatternMatcher parameterTypePatternMatcher) {
        return METHOD_CACHE.computeIfAbsent(generateUniqueName(parent, name, parameterTypePatternMatcher), ignored -> {
            Class<?> current = this.parent;
            while (current != null) {
                for (Method method : current.getDeclaredMethods()) {
                    if (method.getName().equals(name) && parameterTypePatternMatcher.matches(method)) {
                        return method;
                    }
                }
                current = current.getSuperclass();
            }
            throw new RuntimeException(String.format("Could not find matching method in %s", this.parent));
        });
    }

    public Class<?> getParent() {
        return parent;
    }

    public Object getInstance() {
        return instance;
    }

    private static String generateUniqueName(Class<?> parentClass, String name, ParameterTypePatternMatcher parameterTypePatternMatcher) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(parentClass.getName())
                .append('.')
                .append(name)
                .append('(');
        for (Object parameter : parameterTypePatternMatcher.getParameters()) {
            stringBuilder.append(parameter.getClass().getName());
        }
        return stringBuilder.append(')')
                .toString();
    }
}
