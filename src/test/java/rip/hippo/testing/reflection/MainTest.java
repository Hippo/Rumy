package rip.hippo.testing.reflection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rip.hippo.reflection.context.ClassContext;
import rip.hippo.reflection.lookup.ClassLookup;
import rip.hippo.testing.reflection.data.IntegerContainer;
import rip.hippo.testing.reflection.data.StaticLogger;

import static rip.hippo.reflection.allocate.ObjectAllocator.*;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public final class MainTest {

    @Test
    public void staticLogTest() {
        ClassContext staticLogger = ClassLookup.lookup(StaticLogger.class);

        staticLogger.invoke("welcome");
        staticLogger.invoke("log", ofWeak("Hello, World!"));
    }

    @Test
    public void integerContainerTest() {
        ClassContext integerContainer = allocate(IntegerContainer.class, ofStrong(int.class).then(69));

        int data = integerContainer.get("data");
        assertEquals(69, data);

        integerContainer.set("data", 420);
        int data1 = integerContainer.get("data");
        assertEquals(420, data1);

        integerContainer.invoke("setData", ofStrong(int.class).then(1337));
        int getData = integerContainer.invoke("getData");
        assertEquals(1337, getData);
    }

    @Test
    public void defaultConstructorTest() {
        ClassContext integerContainer = allocate(IntegerContainer.class);
        int data = integerContainer.get("data");
        assertEquals(691337, data);
    }
}
