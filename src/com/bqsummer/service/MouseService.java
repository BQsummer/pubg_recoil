package com.bqsummer.service;

import com.bqsummer.Application;
import com.bqsummer.collection.Pair;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.bqsummer.constant.KeyboardConstants;
import com.bqsummer.jna.DD;
import com.bqsummer.jna.LowLevelMouseProc;

public class MouseService {

    private static volatile MouseService instance;

    private static LowLevelMouseProc mouseHookDown;

    private static LowLevelMouseProc mouseHookUp;

    private static boolean isHooked = false;

    private static boolean isOpen = false;

    public static Thread mouseDownHookThread;

    public static Thread mouseUpHookThread;

    private static MouseMoveDownThread mouseMoveDownThread;

    private static boolean mouseDown = false;

    private MouseService(){};

    public static MouseService getInstance() {
        if (instance == null) {
            synchronized (MouseService.class) {
                instance = init();
            }
        }
        return instance;
    }

    public static MouseService init() {
        mouseMoveDownThread = new MouseMoveDownThread();
        return new MouseService();
    }

    public static class MouseMoveDownThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    while (isOpen() && mouseDown) {
                        System.out.println("loop");
                        Pair<Integer, Integer> recoilAndInterval = GunService.getInstance().getGunRecoilAndInterval();
                        System.out.println("get interval and recoil: " + recoilAndInterval.getKey() + ", " + recoilAndInterval.getValue());
                        DD.INSTANCE.DD_movR(0, recoilAndInterval.getValue());
                        Thread.sleep(recoilAndInterval.getKey());
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void mouseHookInit() {
        if (!Platform.isWindows()) {
            throw new UnsupportedOperationException("Not supported on this platform.");
        }
        mouseHookDown = hookTheMouseDown();
        mouseHookUp = hookTheMouseUp();

        setMouseHookDown();
        setMouseHookUp();

        mouseMoveDownThread.start();
    }

    public static void setMouseHookDown() {
         mouseDownHookThread= new Thread(() -> {
             try {
                 if (!isHooked) {
                     Application.HHK = Application.USER32INST.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHookDown,
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
                     System.out.println("mouse down hook success");
                 } else {
                     System.out.println("The Hook is already installed.");
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }
         });
        mouseDownHookThread.start();
    }

    /**
     * 监听左键松开
     */
    public static void setMouseHookUp() {
        mouseUpHookThread = new Thread(() -> {
            try {
                if (!isHooked) {
                    Application.HHK = Application.USER32INST.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHookUp,
                            Application.KERNEL32INST.GetModuleHandle(null), 0);
                    isHooked = true;
                    WinUser.MSG msg = new WinUser.MSG();
                    while ((Application.USER32INST.GetMessage(msg, null, 0, 0)) != 0) {
                        Application.USER32INST.TranslateMessage(msg);
                        Application.USER32INST.DispatchMessage(msg);
                        System.out.print(isHooked);
                        if (!isHooked) {
                            break;
                        }
                        System.out.println("mouse up hook success");
                    }
                } else {
                    System.out.println("The Hook is already installed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mouseUpHookThread.start();
    }

    public static LowLevelMouseProc hookTheMouseDown() {
        return (nCode, wParam, info) -> {
            if (nCode >= 0) {
                switch (wParam.intValue()) {
                    case KeyboardConstants.WM_LBUTTONDOWN: {
                        if (mouseMoveDownThread != null) {
                            setMouseDown(true);
                        }
                    }
//                        case KeyboardConstants.WM_RBUTTONDOWN: // Right click
//                            break;
//                        case KeyboardConstants.WM_MBUTTONDOWN:
//                            break;
//                        case KeyboardConstants.WM_LBUTTONUP:
//                            break;
//                        case KeyboardConstants.WM_MOUSEMOVE:
//                            break;
//                        case KeyboardConstants.WM_MOUSEWHEEL: // Scrolling by wheel
//                            break;
                    default:
                        break;
                }
            }
            Pointer ptr = info.getPointer();
            long peer = Pointer.nativeValue(ptr);
            return Application.USER32INST.CallNextHookEx(Application.HHK, nCode, wParam, new WinDef.LPARAM(peer));
        };
    }

    /**
     * 覆写左键松开的回调函数
     *
     * @return
     */
    public static LowLevelMouseProc hookTheMouseUp(){
        return (nCode, wParam, info) -> {
            if (nCode >= 0) {
                switch (wParam.intValue()) {
                    case KeyboardConstants.WM_LBUTTONUP:
                        if (mouseMoveDownThread != null) {
                            setMouseDown(false);
                            GunService.getInstance().clearGunRecoil();
                        }
                }
//                    if (isOpen) {
//                        USER32INST.PostQuitMessage(0);
//                    }
            }
            Pointer ptr = info.getPointer();
            long peer = Pointer.nativeValue(ptr);
            return Application.USER32INST.CallNextHookEx(Application.HHK, nCode, wParam, new WinDef.LPARAM(peer));
        };
    }

    public static boolean isMouseDown() {
        return mouseDown;
    }

    public static void setMouseDown(boolean mouseDown) {
        MouseService.mouseDown = mouseDown;
    }

    public static boolean isOpen() {
        return isOpen;
    }

    public static void switchOpen() {
        MouseService.isOpen = (!MouseService.isOpen);
    }
}
