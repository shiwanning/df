package com.tcg.mis.common.mybatis.dialect;

public class DialectFactory {
    
    private static OracleDialect oracleDialect = new OracleDialect();
    
    private DialectFactory() {
        throw new IllegalStateException("Utility class");
    }
    
    public static Dialect createDialect(String dialect) {
        if (dialect != null && "oracle".equalsIgnoreCase(dialect.trim())) {
            return oracleDialect;
        }
        throw new IllegalArgumentException("----- not set dialect");
    }
}
