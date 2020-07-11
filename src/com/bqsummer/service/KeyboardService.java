package com.bqsummer.service;

import com.bqsummer.Application;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.bqsummer.constant.KeyboardConstants;

public class KeyboardService {

    private static volatile KeyboardService instance;

    private static Thread keyboardHookThread;

    public static WinUser.LowLevelKeyboardProc lpfn;

    private static boolean isHooked = false;

    private KeyboardService(){};

    public static KeyboardService getInstance() {
        if (instance == null) {
            synchronized (KeyboardService.class) {
                if(instance == null) {
                    instance = init();
                }
            }
        }
        return instance;
    }

    public static KeyboardService init() {
        return new KeyboardService();
    }

    public static void keyboardHookInit() {
        if (!Platform.isWindows()) {
            throw new UnsupportedOperationException("Not supported on this platform.");
        }
        lpfn = hookKeyboard();
        setKeyboardHook();
    }

    public static void setKeyboardHook() {
        keyboardHookThread = new Thread(() -> {
            try {
                if (!isHooked) {
                    Application.HHK = Application.USER32INST.SetWindowsHookEx(User32.WH_KEYBOARD_LL, lpfn,
                            Application.KERNEL32INST.GetModuleHandle(null), 0);
                    isHooked = true;
                    WinUser.MSG msg = new WinUser.MSG();
                    while ((Application.USER32INST.GetMessage(msg, null, 0, 0)) != 0) {
                        Application.USER32INST.TranslateMessage(msg);
                        Application.USER32INST.DispatchMessage(msg);
                        if (!isHooked) {
                            break;
                        }
                    }
                } else {
                    System.out.println("The Hook is already installed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        keyboardHookThread.start();
    }

    public static WinUser.LowLevelKeyboardProc hookKeyboard() {
        return (nCode, wParam, lParam) -> {
            if (nCode >= 0) {
                switch (wParam.intValue()) {
                    case KeyboardConstants.WM_KEYDOWN: {
                        if (lParam.vkCode == 0x56) {
                            GunService.getInstance().switchGun();
                        } else if (lParam.vkCode == 0x50) {
                            MouseService.switchOpen();
                        }
                        break;
                    }
                }
            }
            Pointer ptr = lParam.getPointer();
            long peer = Pointer.nativeValue(ptr);
            return User32.INSTANCE.CallNextHookEx(Application.HHK, nCode, wParam, new WinDef.LPARAM(peer));
        };
    }
}
