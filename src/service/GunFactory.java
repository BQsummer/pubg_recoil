package service;

import property.AppProperties;
import property.PropertiesFactory;
import weapon.Gun;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:50
 **/
public class GunFactory {

    private static Map<String, Gun> gunMap;

    private static volatile GunFactory instance;

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
            gunMap = new HashMap<>();
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

    public void buildGun(String name, double[] recoil) {
        if (gunMap.get(name) == null) {
            gunMap.put(name, new Gun(name, recoil));
        } else {
            Gun gun = gunMap.get(name);
            gun.setRecoil(recoil);
        }
    }

}
