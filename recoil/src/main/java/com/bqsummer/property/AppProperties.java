package com.bqsummer.property;

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
    private int sapiVolume;

    /**
     * 提示音语速
     */
    private int sapiRate;

    private int warningSwitch = 0;

    private int warningInterval = 10;

    private double recoilIntervalRatio = 0.99;

    private double recoilRandomSeed = 0.1;

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

    public int getSapiVolume() {
        return sapiVolume;
    }

    public int getSapiRate() {
        return sapiRate;
    }

    public int getWarningSwitch() {
        return warningSwitch;
    }

    public int getWarningInterval() {
        return warningInterval;
    }

    public double getRecoilIntervalRatio() {
        return recoilIntervalRatio;
    }

    public double getRecoilRandomSeed() {
        return recoilRandomSeed;
    }
}
