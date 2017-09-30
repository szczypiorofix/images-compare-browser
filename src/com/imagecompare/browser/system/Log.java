package com.imagecompare.browser.system;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    public static boolean DEBUG_MODE = false;
    private static Logger logger = null;
    private static FileHandler fileHandler = null;

    private Log() {}

    private static Logger instance() {
        System.out.println("Creating new Logger ...");
        logger = Logger.getLogger(Log.class.getName());

        try {
            fileHandler = new FileHandler("app.log", true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        //LOGGER.setUseParentHandlers(false); // WYSWIETLANIE LOGÓW W KONSOLI

        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(Level.INFO);
        logger.addHandler(fileHandler);
        put(false, Level.INFO, "Uruchomienie gry w trybie DEBUG. Logger załadowany.", "Log (Logger)");
        return logger;
    }

    public static void put(boolean critical, Level level, String msg, String c) {
        if (logger == null) logger = instance();
        if (DEBUG_MODE)
        {
            logger.log(level, c +": " +msg);
            if (critical) {
                logger.log(Level.WARNING, "Zamykanie programu z błędem.");
                System.exit(-1);
            }
        }
    }

}
