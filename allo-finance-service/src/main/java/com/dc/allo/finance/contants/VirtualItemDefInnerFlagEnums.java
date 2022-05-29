package com.dc.allo.finance.contants;

public enum VirtualItemDefInnerFlagEnums {

    NOT_ENOUGH_COST_DIMON(1L<<0,"道具不足是否直接扣金币"),

    ;

    VirtualItemDefInnerFlagEnums(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Long code;
    private String desc;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean isValid(long innerFlag,VirtualItemDefInnerFlagEnums enums) {
        if(enums==null) {
            return false;
        }
        return (innerFlag&enums.getCode())!=0L;
    }
}
