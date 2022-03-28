package uz.pdp.utils;

import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Color.BLUE;

/**
 * @author Narzullayev Husan, пт 14:50. 26.11.2021
 */
public class Print {
    public static void print(Object obj) {
        print(BLUE, obj);
    }

    public static void print(String color, Object obj) {
        System.out.print(color + obj + RESET);
    }

    public static void println(Object obj) {
        println(BLUE, obj);
    }

    public static void println(String color, Object obj) {
        System.out.println(color + obj + RESET);
    }
}
