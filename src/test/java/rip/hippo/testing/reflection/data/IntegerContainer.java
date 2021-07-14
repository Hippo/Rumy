package rip.hippo.testing.reflection.data;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public final class IntegerContainer {

    private int data;

    public IntegerContainer() {
        this(691337);
    }

    public IntegerContainer(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
