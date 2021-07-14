package rip.hippo.testing.rumy.data;

/**
 * @author Hippo
 * @version 1.0.0, 7/14/21
 * @since 1.0.0
 */
public enum StaticLogger {
    ;


    public static void welcome() {
        log("Cool welcome message!");
    }

    public static void log(String message) {
        System.out.println(message);
    }
}
