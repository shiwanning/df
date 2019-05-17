package com.tcg.mis.common.util;

import java.util.regex.Pattern;

public class SqlUtils {

    /**
     * 回传可以用 like 馍糊查找的字串
     */
    public static String fuzzyString(String input) {
        String output = input == null ? "" : input;
        output = output.replaceAll(Pattern.quote("%"), "\\%");
        output = output.replaceAll(Pattern.quote("_"), "\\_");
        return "%" + output + "%";
    }
    
    /**
     * 回传可以用 like 馍糊查找的字串
     */
    public static String prefixFuzzyString(String input) {
        String output = input == null ? "" : input;
        output = output.replaceAll(Pattern.quote("%"), "\\%");
        output = output.replaceAll(Pattern.quote("_"), "\\_");
        return output + "%";
    }
    
}
