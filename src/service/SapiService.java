package service;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import property.AppProperties;

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
        new SapiThread(content).start();
    }

    public class SapiThread extends Thread {

        public SapiThread(String sayContent) {
            this.sayContent = sayContent;
        }

        private String sayContent;

        @Override
        public void run() {
            try {
                sap.setProperty("Volume", AppProperties.getInstance().getSapiVolumn());
                sap.setProperty("Rate", AppProperties.getInstance().getSapiRate());
                Dispatch.call(sapo, "Speak", new Variant(sayContent));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
