package service;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:29
 **/
public class SapiService {

    private static volatile SapiService instance;

    private static ActiveXComponent sap;

    private static Dispatch sapo;

    private SapiService(){};

    public static SapiService getInstance() {
        if (instance == null) {
            synchronized (SapiService.class) {
                instance = init();
            }
        }
        return instance;
    }

    private static SapiService init() {
        SapiService sapiService = new SapiService();
        try {
            sap = new ActiveXComponent("Sapi.SpVoice");
            sapo = sap.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sapiService;
    }

    public void say(String content) {

    }

    public class SapiThread extends Thread {
        @Override
        public void run() {
                try {
                    sap.setProperty("Volume", new Variant(100));
                    sap.setProperty("Rate", new Variant(2));
                    Dispatch.call(sapo, "Speak", new Variant(sayContent));
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }
}
