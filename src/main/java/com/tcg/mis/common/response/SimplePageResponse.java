package com.tcg.mis.common.response;


import com.tcg.mis.common.page.PaginationAndOrderVO;

import java.util.Collections;
import java.util.List;

/**
 * Description: No footer. <br/>
 *
 * @author Eddie
 */
public class SimplePageResponse<T> extends PageResponse<T, Object> {
    public SimplePageResponse(List<T> list, PaginationAndOrderVO page) {
        super(list, page);
    }

    public SimplePageResponse(List<T> list, Integer total, Integer totalPages) {
        super(list, total, totalPages);
    }

    public static <T> SimplePageResponse<T> empty() {
        return new SimplePageResponse<>(Collections.<T>emptyList(), 0, 0);
    }
}
