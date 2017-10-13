package com.imagecompare.browser.system;


import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigFileHandler {


    public static final String DEFAULT_CONFIG_FILE_DATABASE = "";
    public static final int DEFAULT_CONFIG_FILE_WIDTH = 1000;
    public static final int DEFAULT_CONFIG_FILE_HEIGHT = 600;
    public static final String DEFAULT_CONFIG_FILE_WINDOW_MAXIMIZED = "false";

    private static final String CONFIG_FILENAME = "config.ini";

    private static final String PROPERTY_NAME_LAST_DATABASE_FILENAME = "lastdb";
    private static final String PROPERTY_NAME_APP_WINDOW_WIDTH = "app_window_width";
    private static final String PROPERTY_NAME_APP_WINDOW_HEIGHT = "app_window_height";
    private static final String PROPERTY_NAME_APP_WINDOW_IS_MAXIMIZED = "app_window_maximized";

    private static File configFile = null;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static final Properties prop = new Properties();
    private static String lastDatabase = "";
    private static String appMaximized = "false";
    private static int appWidth = 0;
    private static int appHeight = 0;


    private ConfigFileHandler() {}

    public static void init() {
        configFile = new File(CONFIG_FILENAME);
        if(configFile.exists() && !configFile.isDirectory()) {
            Log.put(false, Level.INFO, "Plik konfiguracji - OK.", ConfigFileHandler.class.getName());
            try {
                inputStream = new FileInputStream(configFile);
                prop.load(inputStream);
                ConfigFileHandler.lastDatabase = prop.getProperty(PROPERTY_NAME_LAST_DATABASE_FILENAME);
                ConfigFileHandler.appWidth = Integer.valueOf(prop.getProperty(PROPERTY_NAME_APP_WINDOW_WIDTH));
                ConfigFileHandler.appHeight = Integer.valueOf(prop.getProperty(PROPERTY_NAME_APP_WINDOW_HEIGHT));
                ConfigFileHandler.appMaximized = prop.getProperty(PROPERTY_NAME_APP_WINDOW_IS_MAXIMIZED);
            } catch(IOException ioe) {
                Log.put(false, Level.INFO, "Błąd odczytu z pliku konfiguracji !!!", ConfigFileHandler.class.getName());
            }
        }
        else {
            Log.put(false, Level.INFO, "Brak pliku konfiguracji. Tworzenie nowego pliku z domyślnymi wartościami.", ConfigFileHandler.class.getName());
            try {
                outputStream = new FileOutputStream(configFile);
                prop.setProperty(PROPERTY_NAME_LAST_DATABASE_FILENAME, DEFAULT_CONFIG_FILE_DATABASE);
                prop.setProperty(PROPERTY_NAME_APP_WINDOW_WIDTH, String.valueOf(DEFAULT_CONFIG_FILE_WIDTH));
                prop.setProperty(PROPERTY_NAME_APP_WINDOW_HEIGHT, String.valueOf(DEFAULT_CONFIG_FILE_HEIGHT));
                prop.setProperty(PROPERTY_NAME_APP_WINDOW_IS_MAXIMIZED, DEFAULT_CONFIG_FILE_WINDOW_MAXIMIZED);
                prop.store(outputStream, "Ustawienia aplikacji");
            } catch(IOException ioe) {
                Log.put(false, Level.INFO, "Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!", ConfigFileHandler.class.getName());
            }
            finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch(IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
            ConfigFileHandler.lastDatabase = DEFAULT_CONFIG_FILE_DATABASE;
            ConfigFileHandler.appWidth = DEFAULT_CONFIG_FILE_WIDTH;
            ConfigFileHandler.appHeight = DEFAULT_CONFIG_FILE_HEIGHT;
            ConfigFileHandler.appMaximized = DEFAULT_CONFIG_FILE_WINDOW_MAXIMIZED;
        }
    }

    public static void writeLastDatabase(String lastDatabase) {
        try {
            Log.put(false, Level.INFO, "Wpisanie do pliku konfiguracji ścieżki ostatnio używanej bazy danych: " +lastDatabase, ConfigFileHandler.class.getName());
            outputStream = new FileOutputStream(configFile);
            prop.setProperty(PROPERTY_NAME_LAST_DATABASE_FILENAME, lastDatabase);
            prop.store(outputStream, "Ustawienia aplikacji");

        } catch(IOException ioe) {
            Log.put(false, Level.INFO, "Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!", ConfigFileHandler.class.getName());
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        ConfigFileHandler.lastDatabase = lastDatabase;
    }

    public static String getLastDatabase() {
        return lastDatabase;
    }

    public static int getAppWidth() {
        return appWidth;
    }

    public static int getAppHeight() {
        return appHeight;
    }

    public static boolean isAppMaximized() {
        return (appMaximized.equalsIgnoreCase("true"));
    }

    public static void saveWindowSizeToIniFile(int appWidth, int appHeight, boolean maximized) {
        ConfigFileHandler.appWidth = appWidth;
        ConfigFileHandler.appHeight = appHeight;
        if (maximized) {
            ConfigFileHandler.appMaximized = "true";
        } else {
            ConfigFileHandler.appMaximized = "false";
        }
        try {
            Log.put(false, Level.INFO, "Wpisanie do pliku konfiguracji ostatniej wielkości okna aplikacji: " +lastDatabase, ConfigFileHandler.class.getName());
            outputStream = new FileOutputStream(configFile);
            prop.setProperty(PROPERTY_NAME_APP_WINDOW_WIDTH, String.valueOf(appWidth));
            prop.setProperty(PROPERTY_NAME_APP_WINDOW_HEIGHT, String.valueOf(appHeight));
            prop.setProperty(PROPERTY_NAME_APP_WINDOW_IS_MAXIMIZED, ConfigFileHandler.appMaximized);
            prop.store(outputStream, "Ustawienia aplikacji");
        } catch(IOException ioe) {
            Log.put(false, Level.INFO, "Błąd przy próbie wpisania do pliku konfiguracji ostatniej wielkości okna aplikacji !!!", ConfigFileHandler.class.getName());
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
