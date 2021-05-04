package com.bqsummer.interception;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class KeyBoardStroke extends Structure {

    public short code;
    public short state;
    public int information;

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("code", "state", "information");
    }

}