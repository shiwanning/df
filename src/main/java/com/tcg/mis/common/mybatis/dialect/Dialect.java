package com.tcg.mis.common.mybatis.dialect;


import com.tcg.mis.common.page.PaginationVO;

public interface Dialect {
    String changeToPageSql(String originalSql, PaginationVO page);
}
