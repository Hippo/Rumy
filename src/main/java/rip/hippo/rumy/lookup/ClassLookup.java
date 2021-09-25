package rip.hippo.rumy.lookup;

import rip.hippo.rumy.context.ClassContext;
import rip.hippo.rumy.pattern.ParameterTypePatternMatcher;
import rip.hippo.rumy.pattern.builder.StrongParameterTypePatternMatcherBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hippo
 * @version 1.1.0, 7/13/21
 * @since 1.0.0
 */
public enum ClassLookup {
    ;

    private static final Map<String, ClassContext> CLASS_CONTEXT_CACHE = new HashMap<>();


    public static ClassContext lookup(Class<?> parentClass) {
        return lookup(parentClass.getName());
    }

    public static ClassContext lookup(String name) {
        return CLASS_CONTEXT_CACHE.computeIfAbsent(name, ignored -> new ClassContext(forName(name), null));
    }

    public static ParameterTypePatternMatcher ofWeak(Object... parameters) {
        return ParameterTypePatternMatcher.ofWeak(parameters);
    }

    public static StrongParameterTypePatternMatcherBuilder ofStrong(Class<?>... parameterTypes) {
        return ParameterTypePatternMatcher.ofStrong(parameterTypes);
    }

    private static Class<?> forName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
