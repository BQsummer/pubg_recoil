package com.bqsummer.service;

import com.bqsummer.constant.Constants;
import com.bqsummer.property.AppProperties;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-09 19:09
 **/
public class WarnningService {

    private static volatile WarnningService instance;

    private boolean isRunning = true;

    private static Object monitor = new Object();

    private WarnningService(){};

    public static WarnningService getInstance() {
        if (instance == null) {
            synchronized (WarnningService.class) {
                if(instance == null) {
                    instance = init();
                }
            }
        }
        return instance;
    }

    public static WarnningService init() {
        return new WarnningService();
    }

    public void start() {
        new WarningThread().start();
    }

    public class WarningThread extends Thread {
        @Override
        public void run() {
            if (AppProperties.getInstance().getWarningSwitch() == 1) {
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.scheduleAtFixedRate(() -> {
                    String str;
                    try {
                        SapiService.getInstance().say(buildWarningWord());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 0, AppProperties.getInstance().getWarningInterval(), TimeUnit.SECONDS);
            }
        }
    }

    public void setRunning(boolean status) {
        isRunning = status;
    }

    public String buildWarningWord() {
        return GunService.getInstance().getCurrentGunName() + (MouseService.isOpen() ? Constants.WORD_SWITCH_OPEN : Constants.WORD_SWITCH_CLOSED);
    }

}
