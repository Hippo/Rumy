package rip.hippo.reflection.lookup;

import rip.hippo.reflection.context.ClassContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hippo
 * @version 1.0.0, 7/13/21
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

    private static Class<?> forName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
