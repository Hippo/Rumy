package rip.hippo.rumy.pattern.impl;

import rip.hippo.rumy.pattern.ParameterTypePatternMatcher;

import java.lang.reflect.Executable;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public final class EmptyParameterTypePatternMatcher implements ParameterTypePatternMatcher {

    private static final Object[] EMPTY = new Object[0];

    @Override
    public boolean matches(Executable executable) {
        return executable.getParameterCount() == 0;
    }

    @Override
    public Object[] getParameters() {
        return EMPTY;
    }
}
