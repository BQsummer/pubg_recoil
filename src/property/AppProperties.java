package property;

import weapon.Gun;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:42
 **/
public class AppProperties {

    private static volatile AppProperties instance;

    /**
     * 开镜后坐力设置
     */
    private int sensitivityTarget;

    /**
     * 提示音音量
     */
    private int sapiVolumn;

    /**
     * 提示音语速
     */
    private int sapiRate;

    private AppProperties(){};

    public static AppProperties getInstance() {
        if (instance == null) {
            synchronized (AppProperties.class) {
                if(instance == null) {
                    instance = init();
                }
            }
        }
        return instance;
    }

    private static AppProperties init() {
        return new AppProperties();
    }

    public int getSensitivityTarget() {
        return sensitivityTarget;
    }

    public void setSensitivityTarget(int sensitivityTarget) {
        this.sensitivityTarget = sensitivityTarget;
    }

    public int getSapiVolumn() {
        return sapiVolumn;
    }

    public void setSapiVolumn(int sapiVolumn) {
        this.sapiVolumn = sapiVolumn;
    }

    public int getSapiRate() {
        return sapiRate;
    }

    public void setSapiRate(int sapiRate) {
        this.sapiRate = sapiRate;
    }
}
