import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hx on 2018-02-23.
 */
public class Point extends Structure {

    public NativeLong x;
    public NativeLong y;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("x", "y");
    }
}
