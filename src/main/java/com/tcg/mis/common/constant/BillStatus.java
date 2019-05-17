package com.tcg.mis.common.constant;

/**
 * com.tcg.mis.common.constant
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/15 14:33
 */
public enum BillStatus {
    WAITE_AUDIT(0),
    WAITE_SEND(1),
    UNPAID(2),
    PARTIAL_PAID(3),
    ALL_PAID(4),
    OVERDUE(5),
    OBSOLETE(6),
    BAD_DEBT(7)
    ;


    private Integer code;

    BillStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
