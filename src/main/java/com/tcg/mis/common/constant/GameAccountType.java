package com.tcg.mis.common.constant;

public enum GameAccountType {
    KY(338),
    MG(343),
    IBC(331),
    ;

    private final Integer value;

    GameAccountType(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
