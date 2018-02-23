import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

interface LowLevelMouseProc extends HOOKPROC {
    LRESULT callback(int nCode, WPARAM wParam, MouseHookStruct lParam);
}

public final class MouseHook {

    public final User32 USER32INST;
    public final Kernel32 KERNEL32INST;
    public static LowLevelMouseProc mouseHookDown;
    public static LowLevelMouseProc mouseHookUp;
    public static LowLevelKeyboardProc lpfn;
    public HHOOK hhk;
    public Thread thrdDown;
    public Thread thrdUp;
    public Thread thrdKey;
    public boolean threadFinish = true;
    public boolean isHooked = false;
    public boolean isHooked2 = false;
    public static final int WM_MOUSEMOVE = 512;
    public static final int WM_LBUTTONDOWN = 513;
    public static final int WM_LBUTTONUP = 514;
    public static final int WM_RBUTTONDOWN = 516;
    public static final int WM_RBUTTONUP = 517;
    public static final int WM_MBUTTONDOWN = 519;
    public static final int WM_MBUTTONUP = 520;
    public static final int WM_MOUSEWHEEL = 522;
    public static final int WM_LBUTTONDBLCLK = 515;
    public static final int WM_KEYDOWN = 256;

    /**
     * 左键是否被按下
     */
    static boolean isDown = false;

    static boolean start = false;

    static double target_sensitivity = 50;
    static double scope_sensitivity = 50;
    static double scope4x_sensitivity = 50;

    static double target_scale;
    static double scope_scale;
    static double scope4x_scale;

    static String sayContent = "";

    /**
     * 枪械  0为akm  1为m4a1  2为scar
     */
    static int weapon = 0;

    static double interval_ratio = 0.95;
    static double random_seed = 0.1;

    static ActiveXComponent sap;
    static Dispatch sapo;
    /**
     * 射击间隔
     */
    static double weapon_speed = 100;

    static double shoot_duration = 0;

    static int none = 0;

    static boolean sapiEnable = false;

    /**
     * 0为瞄准,1为4倍
     */
    static int mode = 0;

    static double[][] akm = {{23.7, 23.7, 23.7, 23.7, 23.7, 23.7, 23.7, 23.7, 23.7, 23.7, 23.7, 28, 28, 28, 28, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7, 29.7},
            {66.7, 66.7, 66.7, 66.7, 66.7, 66.7, 66.7, 66.7, 66.7, 66.7, 66.7, 123.3, 123.3, 123.3, 123.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3, 93.3},
            {100}};

    static double[][] m4 = {{26.7, 26.7, 26.7, 26.7, 26.7, 26.7, 37, 37, 37, 37, 37, 31, 31, 31, 31, 31, 32, 32, 32, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35},
            {86.7, 86.7, 86.7, 86.7, 86.7, 86.7, 86.7, 150, 150, 150, 150, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7},
            {86}};

    static double[][] scar = {{22.3, 22.3, 22.3, 22.3, 22.3, 22.3, 22.3, 29.3, 29.3, 29.3, 29.3, 26.7, 26.7, 26.7, 26.7, 26.7, 26.7, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26},
            {86.7, 86.7, 86.7, 86.7, 86.7, 86.7, 86.7, 150, 150, 150, 150, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7, 96.7},
            {96}};

    static double[][][] recoil_table = {akm, m4, scar};

    /**
     * 灵敏度转换,默认都为50
     *
     * @param unconvertedSens
     * @return
     */
    public static double convert_sens(double unconvertedSens) {
        return 0.002 * Math.pow(10, unconvertedSens / 50);
    }

    public static double calc_sens_scale(double sensitivity) {
        return convert_sens(sensitivity) / convert_sens(50);
    }

    public static void init() {
        try {
            sap = new ActiveXComponent("Sapi.SpVoice");
            sapiEnable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sapiEnable) {
            sapo = sap.getObject();
        }
        target_scale = calc_sens_scale(target_sensitivity);
        scope_scale = calc_sens_scale(scope_sensitivity);
        scope4x_scale = calc_sens_scale(scope4x_sensitivity);
    }

    public MouseHook() {
        if (!Platform.isWindows()) {
            throw new UnsupportedOperationException("Not supported on this platform.");
        }
        USER32INST = User32.INSTANCE;
        KERNEL32INST = Kernel32.INSTANCE;
        mouseHookDown = hookTheMouseDown();
        mouseHookUp = hookTheMouseUp();
        lpfn = hookKeyboard();
        Native.setProtected(true);
    }


    public void unsetMouseHook() {
        threadFinish = true;
        if (thrdDown.isAlive()) {
            thrdDown.interrupt();
            thrdDown = null;
        }
        isHooked = false;
    }

    public boolean isIsHooked() {
        return isHooked;
    }

    /**
     * 监听左键按下
     */
    public void setMouseHookDown() {
        thrdDown = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isHooked) {
                        hhk = USER32INST.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHookDown,
                                KERNEL32INST.GetModuleHandle(null), 0);
                        isHooked = true;
                        MSG msg = new MSG();
                        while ((USER32INST.GetMessage(msg, null, 0, 0)) != 0) {
                            USER32INST.TranslateMessage(msg);
                            USER32INST.DispatchMessage(msg);
                            System.out.print(isHooked);
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
            }
        });
        threadFinish = false;
        thrdDown.start();
    }

    /**
     * 监听左键松开
     */
    public void setMouseHookUp() {
        thrdUp = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isHooked) {
                        hhk = USER32INST.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHookUp,
                                KERNEL32INST.GetModuleHandle(null), 0);
                        isHooked = true;
                        MSG msg = new MSG();
                        while ((USER32INST.GetMessage(msg, null, 0, 0)) != 0) {
                            USER32INST.TranslateMessage(msg);
                            USER32INST.DispatchMessage(msg);
                            System.out.print(isHooked);
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
            }
        });
        threadFinish = false;
        thrdUp.start();
    }

    /**
     * 监听键盘按键
     */
    public void setKeyboardHook() {
        thrdKey = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isHooked2) {
                        hhk = USER32INST.SetWindowsHookEx(User32.WH_KEYBOARD_LL, lpfn,
                                KERNEL32INST.GetModuleHandle(null), 0);
                        isHooked = true;
                        MSG msg = new MSG();
                        while ((USER32INST.GetMessage(msg, null, 0, 0)) != 0) {
                            USER32INST.TranslateMessage(msg);
                            USER32INST.DispatchMessage(msg);
                            System.out.print(isHooked);
                            if (!isHooked2) {
                                break;
                            }
                        }
                    } else {
                        System.out.println("The Hook is already installed.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        threadFinish = false;
        thrdKey.start();
    }

    /**
     * 覆写左键按下的回调函数
     *
     * @return
     */
    public LowLevelMouseProc hookTheMouseDown() {
        return new LowLevelMouseProc() {
            @Override
            public LRESULT callback(int nCode, WPARAM wParam, MouseHookStruct info) {
                if (nCode >= 0) {
                    switch (wParam.intValue()) {
                        case MouseHook.WM_LBUTTONDOWN: {
                            isDown = true;
                        }
                        case MouseHook.WM_RBUTTONDOWN: // Right click
                            break;
                        case MouseHook.WM_MBUTTONDOWN: {
                        }
                        case MouseHook.WM_LBUTTONUP:
                            break;
                        case MouseHook.WM_MOUSEMOVE:
                            break;
                        case MouseHook.WM_MOUSEWHEEL: // Scrolling by wheel
                            break;
                        default:
                            break;
                    }
                    if (threadFinish) {
                        USER32INST.PostQuitMessage(0);
                    }
                }
                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return USER32INST.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            }
        };
    }

    /**
     * 覆写左键松开的回调函数
     *
     * @return
     */
    public LowLevelMouseProc hookTheMouseUp() {
        return new LowLevelMouseProc() {
            @Override
            public LRESULT callback(int nCode, WPARAM wParam, MouseHookStruct info) {
                if (nCode >= 0) {
                    switch (wParam.intValue()) {
                        case MouseHook.WM_LBUTTONDOWN:
                            break;
                        case MouseHook.WM_RBUTTONDOWN: // Right click
                            break;
                        case MouseHook.WM_MBUTTONDOWN: // Middle click
                            break;
                        case MouseHook.WM_LBUTTONUP:
                            isDown = false;
                        case MouseHook.WM_MOUSEMOVE:
                            break;
                        case MouseHook.WM_MOUSEWHEEL: // Scrolling by wheel
                            break;
                        default:
                            break;
                    }
                    if (threadFinish) {
                        USER32INST.PostQuitMessage(0);
                    }
                }
                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return USER32INST.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            }
        };
    }

    /**
     * 左键按下的鼠标移动
     */
    public static class MouseMoveDown extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i > -1; ) {
                    Thread.sleep(1);
                    if (isDown && start) {
                        double[] para = recoil_value(weapon, shoot_duration);
                        int move = new Double(para[1]).intValue();
                        int time = new Double(para[0]).intValue();
                        System.out.println("time = " + time + ", move = " + move);
                        DD.INSTANCE.DD_movR(0, move);
                        Thread.sleep(time);
                        shoot_duration = shoot_duration + para[0];
                    } else {
                        shoot_duration = 0;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static class Say extends Thread {
        @Override
        public void run() {
            if (sapiEnable) {
                try {
                    sap.setProperty("Volume", new Variant(100));
                    sap.setProperty("Rate", new Variant(2));
                    Dispatch.call(sapo, "Speak", new Variant(sayContent));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 鼠标按下的回调函数
     *
     * @return
     */
    public LowLevelKeyboardProc hookKeyboard() {
        return new LowLevelKeyboardProc() {
            @Override
            public LRESULT callback(int nCode, WPARAM wParam, User32.KBDLLHOOKSTRUCT lParam) {
                if (nCode >= 0) {
                    switch (wParam.intValue()) {
                        case MouseHook.WM_KEYDOWN: {
                            if (lParam.vkCode == 0x56) {
                                if (weapon == 0) {
                                    System.out.println("switch to 1");
                                    sayContent = "m4";
                                    Say say = new Say();
                                    say.start();
                                    weapon = 1;
                                } else if (weapon == 1) {
                                    sayContent = "scar";
                                    Say say = new Say();
                                    say.start();
                                    System.out.println("switch to 2");
                                    weapon = 2;
                                } else if (weapon == 2) {
                                    sayContent = "a k";
                                    Say say = new Say();
                                    say.start();
                                    System.out.println("switch to 0");
                                    weapon = 0;
                                }
                            } else if (lParam.vkCode == 0x4E) {
                                if (mode == 0) {
                                    sayContent = "4倍";
                                    Say say = new Say();
                                    say.start();
                                    System.out.println("switch to 4x");
                                    mode = 1;
                                } else if (mode == 1) {
                                    sayContent = "基本";
                                    Say say = new Say();
                                    say.start();
                                    System.out.println("switch to basicp");
                                    mode = 0;
                                }
                            } else if (lParam.vkCode == 0x50) {
                                if (start == false) {
                                    start = true;
                                } else {
                                    start = false;
                                }
                            }
                            break;
                        }
                    }
                }
                Pointer ptr = lParam.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            }
        };
    }

    public static double[] recoil_value(int weapon, double duration) {
        double d = (Math.floor(duration / 100)) + 1;
        int step = (new Double(d)).intValue();
        if (step >= 40)
            step = 39;
        double weapon_recoil = recoil_table[weapon][mode][step];
        double coefficient = interval_ratio * (1 + random_seed * Math.random());
        weapon_speed = recoil_table[weapon][2][0];
        double weapon_intervals = Math.floor(coefficient * weapon_speed);
        double recoil_recovery = weapon_recoil * weapon_intervals / 100;
        if (mode == 0) {
            recoil_recovery = recoil_recovery / scope_scale;
        } else if (mode == 1) {
            recoil_recovery = recoil_recovery / scope4x_scale;
        }
        double[] ret = new double[]{weapon_intervals, recoil_recovery};
        //System.out.println("weapon_intervals = " + weapon_intervals + "recoil_recovery" + recoil_recovery);
        return ret;
    }

    public static void main(String[] args) throws Exception {

        File f = new File("aa.txt");
        f.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);

        init();
        MouseHook hooker = new MouseHook();
        hooker.setMouseHookDown();
        hooker.setMouseHookUp();
        hooker.setKeyboardHook();
        Thread thread = new MouseMoveDown();
        thread.start();

    }
}