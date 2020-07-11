package com.bqsummer.service;

import com.bqsummer.collection.Pair;
import com.bqsummer.property.AppProperties;
import com.bqsummer.weapon.Gun;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:50
 **/
public class GunService {

    private static LinkedHashMap<String, Gun> gunMap;

    private static volatile GunService instance;

    private static double scopeScale;

    private int gunIterator = 0;

    private int recoilIterator = 0;

    private int duration = 0;

    private GunService(){};

    public static GunService getInstance() {
        if (instance == null) {
            synchronized (GunService.class) {
                if(instance == null) {
                    instance = init();
                }
            }
        }
        return instance;
    }

    public static GunService init() {
        if (gunMap == null) {
            gunMap = new LinkedHashMap<>();
        }
        return new GunService();
    }

    public void calcSensScale() {
        scopeScale = calc_sens_scale(AppProperties.getInstance().getSensitivityTarget());
    }

    public void buildGun(String name, double interval) {
        System.out.println("build gun success, gun name = " + name + ", interval = " +interval);
        if (gunMap.get(name) == null) {
            gunMap.put(name, new Gun(name, interval));
        } else {
            Gun gun = gunMap.get(name);
            gun.setInterval(interval);
        }
    }

    public void buildGun(String name, Double[] recoil) {
        System.out.println("build gun success, gun name = " + name + ", recoil = " + Arrays.toString(recoil));
        if (gunMap.get(name) == null) {
            gunMap.put(name, new Gun(name, recoil));
        } else {
            Gun gun = gunMap.get(name);
            gun.setRecoil(recoil);
        }
    }

    public void switchGun() {
        gunIterator++;
        if (gunIterator >= gunMap.size()) {
            gunIterator = 0;
        }
        String gunName = (String) (gunMap.keySet().toArray())[gunIterator];
        SapiService.getInstance().say(gunName);
        System.out.println("switch gun to " + gunName);
    }

    public String getCurrentGunName() {
        return (String) (gunMap.keySet().toArray())[gunIterator];
    }

    public Gun getCurrentGun() {
        return gunMap.get((String) (gunMap.keySet().toArray())[gunIterator]);
    }

    public Pair<Integer, Integer> getGunRecoilAndInterval() {
        double d = (Math.floor(duration / 100)) + 1;
        int step = (new Double(d)).intValue();
        if (step >= 40)
            step = 39;
        System.out.println("step " + step);
        double weaponRecoil = getCurrentGunRecoil(step);
        System.out.println("ori recoil " + weaponRecoil);
        double coefficient = AppProperties.getInstance().getRecoilIntervalRatio() * (1 + AppProperties.getInstance().getRecoilRandomSeed() * Math.random());
        double weaponSpeed = getCurrentGunIntervals();
        double weaponIntervals = Math.floor(coefficient * weaponSpeed);
        double recoilRecovery = weaponRecoil * (weaponIntervals / 100);
        recoilRecovery = recoilRecovery / scopeScale;
        System.out.println("final recoil " + recoilRecovery);
        duration += weaponIntervals;
        return new Pair<>(Double.valueOf(weaponIntervals).intValue(), Double.valueOf(recoilRecovery).intValue());
    }

    public void clearGunRecoil() {
        recoilIterator = 0;
        duration = 0;
    }

    private static double convert_sens(double unconvertedSens) {
        return 0.002 * Math.pow(10, unconvertedSens / 50);
    }

    private static double calc_sens_scale(double sensitivity) {
        return convert_sens(sensitivity) / convert_sens(50);
    }

    private double getCurrentGunRecoil(int step) {
        Double[] recoils = getCurrentGun().getRecoil();
        if (step >= recoils.length) {
            return recoils[recoils.length - 1];
        } else {
            return recoils[step];
        }
    }

    private double getCurrentGunIntervals() {
        return getCurrentGun().getInterval();
    }

}
