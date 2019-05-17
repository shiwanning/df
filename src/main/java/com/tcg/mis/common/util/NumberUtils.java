package com.tcg.mis.common.util;

public class NumberUtils {
    
    private NumberUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    public static int[] byteArrayToIntArray(byte[] array){
        int[] result = new int[array.length];
        int i=0;
        for (Byte b : array) {
            result[i] = b.intValue();
            i++;
        }
        return result;
    }
}
