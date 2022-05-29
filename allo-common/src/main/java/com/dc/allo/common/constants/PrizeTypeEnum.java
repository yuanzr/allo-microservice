package com.dc.allo.common.constants;


/**
 * 功能描述: 开箱子，奖品类型
 * 详情：奖品除了实物，靓号，是有运营下发，其它是由程序自动下发
 */
public enum PrizeTypeEnum {

    coins(1, "萌币"),

    gift(2, "礼物"),

    driving(3, "座驾"),

    headwear(4, "头饰"),

    background(5, "背景"),

    material_object(6, "实物"),

    beautiful_account(7, "靓号"),

    whole_micro_gift(8, "全麦礼物"),

    random_pretty_number(9, "随机靓号"),

    key(10, "锤子"),

    greeting(11, "祝福语"),

    radish(12, "银币"),

    diamond(13, "钻石"),
    aristocratic(14, "贵族"),
    badge(15, "勋章"),
    banner(16, "banner冠名"),
    gift_named(17, "礼物冠名"),
    ramadans_item(18, "斋月碎片道具"),
    ;

    private int value;

    private String desc;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    PrizeTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
