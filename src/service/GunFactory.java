package service;

import weapon.Gun;

import java.util.LinkedHashMap;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:50
 **/
public class GunFactory {

    private static LinkedHashMap<String, Gun> gunMap;

    private static volatile GunFactory instance;

    private int gunIterator = 0;

    private GunFactory(){};

    public static GunFactory getInstance() {
        if (instance == null) {
            synchronized (GunFactory.class) {
                if(instance == null) {
                    instance = init();
                }
            }
        }
        return instance;
    }

    public static GunFactory init() {
        if (gunMap == null) {
            gunMap = new LinkedHashMap<>();
        }
        return new GunFactory();
    }

    public void buildGun(String name, double interval) {
        if (gunMap.get(name) == null) {
            gunMap.put(name, new Gun(name, interval));
        } else {
            Gun gun = gunMap.get(name);
            gun.setInterval(interval);
        }
    }

    public void buildGun(String name, Double[] recoil) {
        if (gunMap.get(name) == null) {
            gunMap.put(name, new Gun(name, recoil));
        } else {
            Gun gun = gunMap.get(name);
            gun.setRecoil(recoil);
        }
    }

    public Gun switchGun() {
        gunIterator++;
        if (gunIterator >= gunMap.size()) {
            gunIterator = 0;
        }
        String gunName = (String) (gunMap.keySet().toArray())[gunIterator];
        SapiService.getInstance().say(gunName);
        return gunMap.get(gunName);
    }

    public


}
