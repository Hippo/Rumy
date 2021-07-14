package rip.hippo.reflection.pattern.impl;

import rip.hippo.reflection.pattern.ParameterTypePatternMatcher;

import java.lang.reflect.Executable;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public final class WeakParameterTypePatternMatcher implements ParameterTypePatternMatcher {

    private final Object[] parameters;

    public WeakParameterTypePatternMatcher(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean matches(Executable executable) {
        Class<?>[] parameterTypes = executable.getParameterTypes();
        if (parameterTypes.length != parameters.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameters[i].getClass().equals(parameterTypes[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }
}
