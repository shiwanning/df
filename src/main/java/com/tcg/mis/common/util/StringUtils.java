package com.tcg.mis.common.util;

public class StringUtils {

    public static String capitalizeFirstLetter(String param) {
        if (param == null || "".equals(param.trim())) {
            return param;
        }
        StringBuilder sb = new StringBuilder(param.length());
        sb.append(Character.toUpperCase(param.charAt(0))).append(param.substring(1, param.length()));
        return sb.toString();
    }
}
