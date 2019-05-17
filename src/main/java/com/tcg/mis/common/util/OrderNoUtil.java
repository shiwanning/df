package com.tcg.mis.common.util;

import java.util.Date;

public class OrderNoUtil {

    private static String MIS = "MIS";

    public static String getOrderNo(OrderTypeEnum type){
       return  MIS + type.toString() + new Date().getTime();
    }

    public enum OrderTypeEnum {
        PG_RECHARGE("RT"),
        MT_RECHARGE("RM"),
        TP_WITHDRAW("WT"),
        MERCHANT_PRODUCT_RECHARGE("PC"),
        MERCHANT_PRODUCT_TRANSFER("PT"),
        TCG("TCG"),
        ;

        private final String type;

        OrderTypeEnum(String type) {
            this.type = type;
        }

        @Override
        public String toString(){
            return this.type;
        }
    }

}
