package rip.hippo.reflection.pattern;

import rip.hippo.reflection.pattern.builder.StrongParameterTypePatternMatcherBuilder;
import rip.hippo.reflection.pattern.impl.EmptyParameterTypePatternMatcher;
import rip.hippo.reflection.pattern.impl.WeakParameterTypePatternMatcher;

import java.lang.reflect.Executable;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public interface ParameterTypePatternMatcher {

    ParameterTypePatternMatcher EMPTY = new EmptyParameterTypePatternMatcher();

    boolean matches(Executable executable);

    Object[] getParameters();

    static ParameterTypePatternMatcher ofWeak(Object... parameters) {
        return new WeakParameterTypePatternMatcher(parameters);
    }

    static StrongParameterTypePatternMatcherBuilder ofStrong(Class<?>... parameterTypes) {
        return new StrongParameterTypePatternMatcherBuilder(parameterTypes);
    }
}
