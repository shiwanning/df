package com.tcg.mis.model.vo;

import java.util.List;

public class MerchantProductTo {

    private MerchantProductWalletTo mainWallet;
    private List<MerchantProductWalletTo> productWalletToList;

    public MerchantProductWalletTo getMainWallet() {
        return mainWallet;
    }

    public void setMainWallet(MerchantProductWalletTo mainWallet) {
        this.mainWallet = mainWallet;
    }

    public List<MerchantProductWalletTo> getProductWalletToList() {
        return productWalletToList;
    }

    public void setProductWalletToList(List<MerchantProductWalletTo> productWalletToList) {
        this.productWalletToList = productWalletToList;
    }
}
