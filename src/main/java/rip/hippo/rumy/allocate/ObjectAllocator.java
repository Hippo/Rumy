package rip.hippo.rumy.allocate;

import rip.hippo.rumy.context.ClassContext;
import rip.hippo.rumy.pattern.ParameterTypePatternMatcher;

import java.lang.reflect.Constructor;

import static rip.hippo.rumy.pattern.ParameterTypePatternMatcher.EMPTY;

/**
 * @author Hippo
 * @version 1.1.1, 7/13/21
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

    public static ClassContext fromAllocated(Object allocated) {
        return new ClassContext(allocated.getClass(), allocated);
    }

    public static ClassContext fromAllocated(Class<?> parentClass, Object allocated) {
        return new ClassContext(parentClass, allocated);
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
