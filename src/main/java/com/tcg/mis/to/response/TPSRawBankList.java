package com.tcg.mis.to.response;

import java.io.Serializable;
import java.util.List;

public class TPSRawBankList implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7710840647033502207L;
    
    private boolean status;
    private String errorDesc;
    private List<TPSRawBank> data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public List<TPSRawBank> getData() {
        return data;
    }

    public void setData(List<TPSRawBank> data) {
        this.data = data;
    }
}
