package rip.hippo.reflection.pattern.impl;

import rip.hippo.reflection.pattern.ParameterTypePatternMatcher;

import java.lang.reflect.Executable;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public final class StrongParameterTypePatternMatcher implements ParameterTypePatternMatcher {

    private final Class<?>[] parameterTypes;
    private final Object[] parameters;

    public StrongParameterTypePatternMatcher(Class<?>[] parameterTypes, Object[] parameters) {
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    @Override
    public boolean matches(Executable executable) {
        Class<?>[] parameterTypes = executable.getParameterTypes();
        if (this.parameterTypes.length != parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!this.parameterTypes[i].equals(parameterTypes[i])) {
                return false;
            }
        }
        return true;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }
}
