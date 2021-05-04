package com.bqsummer.interception;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class MouseStroke extends Structure {

    public short state;
    public short flags;
    public short rolling;
    public int x;
    public int y;
    public int information;

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public short getRolling() {
        return rolling;
    }

    public void setRolling(short rolling) {
        this.rolling = rolling;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getInformation() {
        return information;
    }

    public void setInformation(int information) {
        this.information = information;
    }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList( "state", "flags", "rolling", "x", "y", "information");
    }

    public static MouseStroke stateStroke(short state, short rolling) {
        MouseStroke stroke = new MouseStroke();
        stroke.setState(state);
        stroke.setRolling(rolling);
        return stroke;
    }

    public static MouseStroke moveStroke(int x, int y, short mouseFlags) {
        MouseStroke stroke = new MouseStroke();
        stroke.setX(x);
        stroke.setY(y);
        stroke.setFlags(mouseFlags);
        return stroke;
    }
}
