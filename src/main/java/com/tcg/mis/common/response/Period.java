package com.tcg.mis.common.response;

import java.util.Date;

public final class Period {
    private final Date startDate;
    private final Date endDate;

    public Period(Date startDate, Date endDate) {
        this.startDate = (Date) startDate.clone();
        this.endDate = (Date) endDate.clone();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
