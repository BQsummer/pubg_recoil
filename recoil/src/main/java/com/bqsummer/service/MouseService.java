package com.bqsummer.service;

import com.bqsummer.collection.Pair;
import com.bqsummer.interception.InterceptionHolder;
import com.bqsummer.interception.InterceptionKeyStroke;
import com.bqsummer.interception.MouseHook;
import com.bqsummer.interception.enums.KeyCode;

public class MouseService {

    private static volatile MouseService instance;

    private static boolean isOpen = false;

    public static MouseDownThread mouseDownThread;

    private static MouseMoveThread mouseMoveThread;

    private static boolean mouseDown = false;

    private static MouseHook mouseHook;

    private MouseService(){};

    public static MouseService getInstance() {
        if (instance == null) {
            synchronized (MouseService.class) {
                instance = init();
            }
        }
        return instance;
    }

    public static MouseService init() {
        mouseHook = new MouseHook();
        mouseMoveThread = new MouseMoveThread();
        mouseMoveThread.setName("mouseMoveThread");
        mouseMoveThread.start();
        mouseDownThread = new MouseDownThread();
        mouseDownThread.setName("mouseDownThread");
        mouseDownThread.start();
        return new MouseService();
    }

    public static class MouseMoveThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("MouseMoveThread start");
                while (true) {
                    while (isOpen() && mouseDown) {
                        // System.out.println("loop");
                        Pair<Integer, Integer> recoilAndInterval = GunService.getInstance().getGunRecoilAndInterval();
                        System.out.println("get interval and recoil: " + recoilAndInterval.getKey() + ", " + recoilAndInterval.getValue());
                        mouseHook.moveCursorBy(0, recoilAndInterval.getValue());
                        Thread.sleep(recoilAndInterval.getKey());
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class MouseDownThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("MouseDownThread start");
                InterceptionKeyStroke stroke = new InterceptionKeyStroke();
                int device;
                while(InterceptionHolder.getInterception().interception_receive(InterceptionHolder.getContext(), device = InterceptionHolder.getInterception().interception_wait(InterceptionHolder.getContext()), stroke, 1) > 0) {
                    if(InterceptionHolder.getInterception().interception_is_mouse(device) > 0) {
                        //System.out.println("mouse: " + stroke);
                        InterceptionHolder.getInterception().interception_send(InterceptionHolder.getContext(), device, stroke, 1);
                        if (stroke.getCode() == 1) {
                            mouseDown = true;
                        } else if (stroke.getCode() == 2) {
                            mouseDown = false;
                            GunService.getInstance().clearGunRecoil();
                        }
                    }
                    if(InterceptionHolder.getInterception().interception_is_keyboard(device) > 0) {
                        //System.out.println("keyboard :" + stroke);
                        InterceptionHolder.getInterception().interception_send(InterceptionHolder.getContext(), device, stroke, 1);
                        if (stroke.getState() == 1) {
                            if (stroke.getCode() == KeyCode.V.getValue()) {
                                GunService.getInstance().switchGun();
                            } else if (stroke.getCode() == KeyCode.P.getValue()) {
                                MouseService.switchOpen();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setMouseDown(boolean mouseDown) {
        MouseService.mouseDown = mouseDown;
    }

    public static boolean isOpen() {
        return isOpen;
    }

    public static void switchOpen() {
        MouseService.isOpen = (!MouseService.isOpen);
    }
}
