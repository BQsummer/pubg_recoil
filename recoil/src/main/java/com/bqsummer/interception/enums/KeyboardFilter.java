package com.bqsummer.interception.enums;

public enum KeyboardFilter {

    NONE(0X00),
    ALL(0XFF),
    KEY_DOWN(0X01),
    KEY_UP(0X02),
    KEY_E0(0X04),
    KEY_E1(0X08),
    KEY_TERMSRV_SETLED(0X10),
    KEY_TERMSRV_SHADOW(0X20),
    KEY_TERMSRV_VKPACKET(0X40);

    private final int value;

    public int getValue() {
        return value;
    }

    KeyboardFilter(int value) {
        this.value = value;
    }
}
