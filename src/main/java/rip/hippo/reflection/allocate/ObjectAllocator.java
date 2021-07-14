package rip.hippo.reflection.allocate;

import rip.hippo.reflection.context.ClassContext;
import rip.hippo.reflection.pattern.ParameterTypePatternMatcher;
import rip.hippo.reflection.pattern.builder.StrongParameterTypePatternMatcherBuilder;

import java.lang.reflect.Constructor;

import static rip.hippo.reflection.pattern.ParameterTypePatternMatcher.EMPTY;

/**
 * @author Hippo
 * @version 1.0.0, 7/13/21
 * @since 1.0.0
 */
public enum ObjectAllocator {
    ;

    public static ClassContext allocate(String name) {
        return allocate(name, EMPTY);
    }

    public static ClassContext allocate(String name, ParameterTypePatternMatcher parameterTypePatternMatcher) {
        return allocate(forName(name), parameterTypePatternMatcher);
    }

    public static ClassContext allocate(Class<?> parentClass) {
        return allocate(parentClass, EMPTY);
    }

    public static ClassContext allocate(Class<?> parentClass, ParameterTypePatternMatcher parameterTypePatternMatcher) {
        try {
            Constructor<?> constructor = findConstructor(parentClass, parameterTypePatternMatcher);
            Object instance = constructor.newInstance(parameterTypePatternMatcher.getParameters());
            return  new ClassContext(parentClass, instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static ParameterTypePatternMatcher ofWeak(Object... parameters) {
        return ParameterTypePatternMatcher.ofWeak(parameters);
    }

    public static StrongParameterTypePatternMatcherBuilder ofStrong(Class<?>... parameterTypes) {
        return ParameterTypePatternMatcher.ofStrong(parameterTypes);
    }

    public static Constructor<?> findConstructor(Class<?> parentClass, ParameterTypePatternMatcher parameterTypePatternMatcher) {
        Class<?> current = parentClass;
        while (current != null) {
            for (Constructor<?> constructor : current.getDeclaredConstructors()) {
                if (parameterTypePatternMatcher.matches(constructor)) {
                    return constructor;
                }
            }
            current = current.getSuperclass();
        }
        throw new RuntimeException(String.format("Could not find matching constructor in %s", parentClass));
    }

    private static Class<?> forName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
