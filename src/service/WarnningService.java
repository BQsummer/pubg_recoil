package service;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-09 19:09
 **/
public class WarnningService {

    private static volatile WarnningService instance;

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

    public class WarningThread extends Thread {
        @Override
        public void run() {

        }
    }

}
