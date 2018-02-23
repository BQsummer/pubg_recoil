import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hx on 2018-02-23.
 */
public class MouseHookStruct extends Structure {

    public WinDef.POINT pt;
    public WinDef.HWND hwnd;
    public int wHitTestCode;
    public BaseTSD.ULONG_PTR dwExtraInfo;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[]{"pt", "hwnd", "wHitTestCode", "dwExtraInfo"});
    }
}
