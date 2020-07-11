package com.bqsummer.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by hx on 2018-02-23.
 */
public interface DD extends Library {
    DD INSTANCE = (DD) Native.loadLibrary("dd74000x64.64", DD.class);

    public int DD_mov(int x, int y);

    public int DD_movR(int dx, int dy);

    public int DD_btn(int btn);

    public int DD_whl(int whl);

    public int DD_key(int ddcode, int flag);

    public int DD_str(String s);
}