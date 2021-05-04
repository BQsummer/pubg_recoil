package com.bqsummer;

import com.bqsummer.property.PropertiesFactory;
import com.bqsummer.service.GunService;
import com.bqsummer.service.WarnningService;
import com.sun.jna.Native;
import com.bqsummer.service.MouseService;

public class Application {

    public static void main(String[] args) {
        PropertiesFactory.getInstance();
        WarnningService.getInstance().start();
        GunService.getInstance().calcSensScale();
        MouseService.getInstance();
        Native.setProtected(true);
    }
}
