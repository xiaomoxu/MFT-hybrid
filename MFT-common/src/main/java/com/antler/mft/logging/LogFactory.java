package com.antler.mft.logging;

import java.lang.reflect.Constructor;

public final class LogFactory {

    /**
     * Marker to be used by logging implementations that support markers
     */
    public static final String MARKER = "MFT";

    private static Constructor<? extends Log> logConstructor;

    static {
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useLog4JLogging();
            }
        });
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useStdOutLogging();
            }
        });
    }

    private LogFactory() {
        // disable construction
    }

    public static Log getLog(Class<?> aClass) {
        return getLog(aClass.getName());
    }

    public static Log getLog(String logger) {
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new LogException("Error creating logger for logger " + logger + ".  Cause: " + t, t);
        }
    }

    public static synchronized void useCustomLogging(Class<? extends Log> clazz) {
        setImplementation(clazz);
    }


    public static synchronized void useLog4JLogging() {
        setImplementation(com.antler.mft.logging.Log4jImpl.class);
    }


    public static synchronized void useStdOutLogging() {
        setImplementation(com.antler.mft.logging.StdOutImpl.class);
    }

//    public static synchronized void useNoLogging() {
//        setImplementation(org.apache.ibatis.logging.nologging.NoLoggingImpl.class);
//    }

    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            try {
                runnable.run();
            } catch (Throwable t) {
                // ignore
            }
        }
    }

    private static void setImplementation(Class<? extends Log> implClass) {
        try {
            Constructor<? extends Log> candidate = implClass.getConstructor(String.class);
            Log log = candidate.newInstance(LogFactory.class.getName());
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + implClass + "' adapter.");
            }
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t, t);
        }
    }
}
