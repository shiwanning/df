package com.tcg.mis.to.request;

import com.tcg.mis.common.util.NamingUtils;

public class CamelPageAndSortTo extends PageAndSortTO {
    
    @Override
    public void setSortColumn(String sortColumn) {
        super.setSortColumn(NamingUtils.camelToUnderline(sortColumn));
    }
    
}
