package com.bqsummer.interception.enums;

public enum MouseFilter {

    NONE(0X0000),
    ALL(0XFFFF),
    LEFT_BUTTON_DOWN(MouseState.LEFT_BUTTON_DOWN.getValue()),
    LEFT_BUTTON_UP(MouseState.LEFT_BUTTON_UP.getValue()),
    RIGHT_BUTTON_DOWN(MouseState.RIGHT_BUTTON_DOWN.getValue()),
    RIGHT_BUTTON_UP(MouseState.RIGHT_BUTTON_UP.getValue()),
    MIDDL_EBUTTON_UP(MouseState.MIDDLE_BUTTON_UP.getValue()),
    EXTRA_BUTTON1_DOWN(MouseState.EXTRA_BUTTON1_DOWN.getValue()),
    EXTRA_BUTTON1_UP(MouseState.EXTRA_BUTTON1_UP.getValue()),
    EXTRA_BUTTON2_DOWN(MouseState.EXTRA_BUTTON2_DOWN.getValue()),
    EXTRA_BUTTON2_UP(MouseState.EXTRA_BUTTON2_UP.getValue()),
    SCROLL_VERTICAL(MouseState.SCROLL_VERTICAL.getValue()),
    SCROLL_HORIZONTAL(MouseState.SCROLL_HORIZONTAL.getValue()),
    MOVE(0X1000);

    private final int value;

    public int getValue() {
        return value;
    }

    MouseFilter(int value) {
        this.value = value;
    }
}
