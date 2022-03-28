package uz.pdp.utils;

import java.util.Scanner;

import static uz.pdp.utils.Print.print;
/**
 * @author Narzullayev Husan, пт 14:48. 26.11.2021
 */
public class Input {
    public static final Scanner SCANNER_NUM = new Scanner(System.in);
    public static final Scanner SCANNER_STR = new Scanner(System.in);

    public static Double getNum() {
        return getNum("");
    }

    public static Double getNum(String str) {
        print(str);
        return SCANNER_NUM.nextDouble();
    }

    public static String getStr() {
        return getStr("");
    }

    public static String getStr(String str) {
        print(str);
        return SCANNER_STR.nextLine();
    }
}
