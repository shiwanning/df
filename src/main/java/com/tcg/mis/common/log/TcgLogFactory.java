package com.tcg.mis.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Logger factory <br/>
 *
 * @author Eddie
 */
public class TcgLogFactory {
    public TcgLogFactory() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz.getName());
    }

    public static Logger getLogger(String logName) {
        return LoggerFactory.getLogger(logName);
    }
}
