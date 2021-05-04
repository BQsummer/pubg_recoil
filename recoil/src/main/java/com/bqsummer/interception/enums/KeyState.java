package com.bqsummer.interception.enums;

public enum KeyState {

    DOWN(0X00),
    UP(0X01),
    E0(0X02),
    E1(0X04),
    TERMSRV_SETLED(0X08),
    TERMSRV_SHADOW(0X10),
    TERMSRV_VKPACKET(0X20);

    private final int value;

    public int getValue() {
        return value;
    }

    KeyState(int value) {
        this.value = value;
    }
}
