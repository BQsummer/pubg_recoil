package com.bqsummer.interception;

import com.sun.jna.Callback;

public class DefaultKeyboardCallback implements Callback {

    private final Interception interception;

    public DefaultKeyboardCallback(Interception interception) {
        this.interception = interception;
    }

    public int callback(int device) {
        int v = interception.interception_is_keyboard(device);
        System.out.println("callback of keyboard device " + device + " : " + v);
        return v;
    }
}
