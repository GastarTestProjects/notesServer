package ru.xe72.notes.utils;

import org.springframework.util.StringUtils;

/**
 * NullUtils
 */
public class NU {
    public static <T> T nvl(T obj, T objElse) {
        return obj == null ? objElse : obj;
    }

    public static String nvlOrEmpty(String obj, String objElse) {
        return StringUtils.isEmpty(obj) ? objElse : obj;
    }
}
