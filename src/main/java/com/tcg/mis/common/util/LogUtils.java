package com.tcg.mis.common.util;

import org.slf4j.Logger;

import java.util.List;

public class LogUtils {
    
    private LogUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * 印出 log 
     */
    @SafeVarargs
    public static void autoLog(Logger log, List<String> prefix, List<? extends Object> ...logs) {
        for(int i = 0; i < logs.length ; i++) {
            for(Object singleLog : logs[i]) {
                log.info("{} {}",prefix.get(i), singleLog);
            }
        }
    }
}
