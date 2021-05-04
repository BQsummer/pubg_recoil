package com.bqsummer.interception;

import com.bqsummer.interception.enums.KeyboardFilter;
import com.bqsummer.interception.enums.MouseFilter;
import com.sun.jna.Pointer;

public class InterceptionHolder {
    static Interception lib;
    static Pointer context;

    static {
        System.out.println("Interception holder init start");
        lib = Interception.interception;
        context = lib.interception_create_context();
        lib.interception_set_filter(context, new DefaultKeyboardCallback(lib), (short) (KeyboardFilter.KEY_DOWN.getValue() | KeyboardFilter.KEY_UP.getValue()));
        lib.interception_set_filter(context, new DefaultMouseCallback(lib), (short) (MouseFilter.LEFT_BUTTON_DOWN.getValue() | MouseFilter.LEFT_BUTTON_UP.getValue()));
        System.out.println("Interception holder init end");
    }

    public static Interception getInterception() {
        return lib;
    }

    public static Pointer getContext() {
        return context;
    }

    public static void destroy() {
        lib.interception_destroy_context(context);
    }

    public static void raiseProcessPriority() {
        Kernel32 k32 = Kernel32.k32;
        boolean ok = k32.SetPriorityClass(k32.GetCurrentProcess(), Kernel32.HIGH_PRIORITY_CLASS);
        System.out.println("raise_process_priority:" + ok);
    }

}
