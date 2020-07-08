package jna;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;

public interface LowLevelMouseProc extends HOOKPROC {

    WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, MouseHookStruct lParam);

}