package com.tcg.mis.to.response;

import java.util.List;

public class PgBanksTo extends BanksTo{
    private List<TPSRawBank> data;

    public List<TPSRawBank> getData() {
        return data;
    }

    public void setData(List<TPSRawBank> data) {
        this.data = data;
    }
}
