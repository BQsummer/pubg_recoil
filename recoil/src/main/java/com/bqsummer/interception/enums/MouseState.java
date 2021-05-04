package com.bqsummer.interception.enums;

public enum MouseState {
    LEFT_BUTTON_DOWN(0X001),
    LEFT_BUTTON_UP(0X002),
    RIGHT_BUTTON_DOWN(0X004),
    RIGHT_BUTTON_UP(0X008),
    MIDDLE_BUTTON_DOWN(0X010),
    MIDDLE_BUTTON_UP(0X020),
    EXTRA_BUTTON1_DOWN(0X040),
    EXTRA_BUTTON1_UP(0X080),
    EXTRA_BUTTON2_DOWN(0X100),
    EXTRA_BUTTON2_UP(0X200),
    SCROLL_VERTICAL(0X400),
    SCROLL_HORIZONTAL(0X800);

    private final int value;

    public int getValue() {
        return value;
    }

    MouseState(int value) {
        this.value = value;
    }
}
