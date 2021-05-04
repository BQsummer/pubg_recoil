package com.bqsummer.interception.enums;

public enum MouseFlags {

    MOVE_RELATIVE(0x000),
    MOVE_ABSOLUTE(0x001),
    VIRTUAL_DESKTOP(0x002),
    ATTRIBUTES_CHANGED(0x004),
    MOVE_WITHOUT_COALESCING(0x008),
    TERMINAL_SERVICES_SOURCE_SHADOW(0x100);

    private final int value;

    public int getValue() {
        return value;
    }

    MouseFlags(int value) {
        this.value = value;
    }
}
