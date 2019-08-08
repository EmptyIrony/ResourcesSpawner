package me.cunzai.resourcespawner.utils;

public class StringUtil {
    public static String cf(String message) {
        return message.replaceAll("&", "§");
    }

    public static int stringToInt(String num) throws Exception {
        return Integer.parseInt(num);
    }
}
