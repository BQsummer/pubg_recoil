package com.bqsummer;

import com.bqsummer.property.PropertiesFactory;
import com.bqsummer.service.GunService;
import com.bqsummer.service.WarnningService;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.bqsummer.service.KeyboardService;
import com.bqsummer.service.MouseService;

public class Application {

    public static User32 USER32INST = User32.INSTANCE;

    public static Kernel32 KERNEL32INST = Kernel32.INSTANCE;

    public static WinUser.HHOOK HHK;

    public static void main(String[] args) {
        PropertiesFactory.getInstance();
        WarnningService.getInstance().start();
        MouseService.getInstance().mouseHookInit();
        GunService.getInstance().calcSensScale();
        KeyboardService.keyboardHookInit();
        Native.setProtected(true);
    }
}
