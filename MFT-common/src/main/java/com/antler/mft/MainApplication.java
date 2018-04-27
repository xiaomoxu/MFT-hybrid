package com.antler.mft;

import com.antler.mft.logging.Log;
import com.antler.mft.logging.LogFactory;

public class MainApplication {
    public static void main(String[] args) {
        LogFactory.useStdOutLogging();
        Log log = LogFactory.getLog(MainApplication.class);
        log.debug("Thank you! \n");
    }
}
