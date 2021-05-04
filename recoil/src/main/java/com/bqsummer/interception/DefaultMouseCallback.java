package com.bqsummer.interception;

import com.sun.jna.Callback;

public class DefaultMouseCallback implements Callback  {

    private final Interception interception;

    public DefaultMouseCallback(Interception interception) {
        this.interception = interception;
    }

    public int callback(int device) {
        int v = interception.interception_is_mouse(device);
        System.out.println("callback of mouse device " + device + " : " + v);
        return v;
    }
}
