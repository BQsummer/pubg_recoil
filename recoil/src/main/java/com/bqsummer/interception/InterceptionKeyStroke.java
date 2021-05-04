package com.bqsummer.interception;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class InterceptionKeyStroke extends Structure {

    public short code;
    public short state;
    public int information;
    // just space
    public short rolling;
    public int x;
    public int y;
    public short flags;

    public short getCode() {
        return code;
    }

    public short getState() {
        return state;
    }

    public int getInformation() {
        return information;
    }

    public short getRolling() {
        return rolling;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public void setState(short state) {
        this.state = state;
    }

    public void setInformation(int information) {
        this.information = information;
    }

    public void setRolling(short rolling) {
        this.rolling = rolling;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[] { "code", "state", "information", "rolling", "x", "y", "flags" });
    }

    public static InterceptionKeyStroke stateStroke(short state, short rolling) {
        InterceptionKeyStroke stroke = new InterceptionKeyStroke();
        stroke.setState(state);
        stroke.setRolling(rolling);
        return stroke;
    }

    public static InterceptionKeyStroke moveStroke(int x, int y, short mouseFlags) {
        InterceptionKeyStroke stroke = new InterceptionKeyStroke();
        stroke.setX(x);
        stroke.setY(y);
        stroke.setFlags(mouseFlags);
        return stroke;
    }

    @Override
    public String toString() {
        return "InterceptionKeyStroke{" +
                "code=" + code +
                ", state=" + state +
                ", information=" + information +
                ", rolling=" + rolling +
                ", x=" + x +
                ", y=" + y +
                ", flags=" + flags +
                '}';
    }
}
