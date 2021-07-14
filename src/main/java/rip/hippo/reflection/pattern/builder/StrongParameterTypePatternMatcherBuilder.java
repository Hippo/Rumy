package rip.hippo.reflection.pattern.builder;

import rip.hippo.reflection.pattern.impl.StrongParameterTypePatternMatcher;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public final class StrongParameterTypePatternMatcherBuilder {

    private final Class<?>[] parameterTypes;

    public StrongParameterTypePatternMatcherBuilder(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public StrongParameterTypePatternMatcher then(Object... parameters) {
        return new StrongParameterTypePatternMatcher(parameterTypes, parameters);
    }
}
