package uz.pdp.utils;

import java.util.UUID;

/**
 * @author Narzullayev Husan, пт 14:45. 26.11.2021
 */
public class BaseUtils {

    public static String generateUniqueID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String processMessage(String format, Object... args) {
        return String.format(format, args);
    }
}
