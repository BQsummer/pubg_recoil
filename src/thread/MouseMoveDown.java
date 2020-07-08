package thread;

import jna.DD;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:17
 **/
public class MouseMoveDown extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i > -1; ) {
                Thread.sleep(1);
                if (isDown && start) {
                    double[] para = recoil_value(weapon, shoot_duration);
                    int move = new Double(para[1]).intValue();
                    int time = new Double(para[0]).intValue();
                    System.out.println("time = " + time + ", move = " + move);
                    DD.INSTANCE.DD_movR(0, move);
                    Thread.sleep(time);
                    shoot_duration = shoot_duration + para[0];
                } else {
                    shoot_duration = 0;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

