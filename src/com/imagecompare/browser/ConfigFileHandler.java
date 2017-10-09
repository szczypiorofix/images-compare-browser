package com.imagecompare.browser;

import com.imagecompare.browser.system.Log;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

class ConfigFileHandler {

    private final static String CONFIG_FILENAME = "browser.cfg";
    private static File configFile = null;
    private static InputStream propStream;
    private static Properties prop = new Properties();
    private static String lastDatabase = "";

    private ConfigFileHandler() {}

    public static void init() {
        configFile = new File(CONFIG_FILENAME);
        if(configFile.exists() && !configFile.isDirectory()) {
            Log.put(false, Level.INFO, "Plik konfiguracji - OK.", ConfigFileHandler.class.getName());
        }
        else {
            Log.put(false, Level.INFO, "Brak pliku konfiguracji. Tworzenie nowego pliku z domyślnymi wartościami.", ConfigFileHandler.class.getName());
            try {
                PrintWriter writer = new PrintWriter(CONFIG_FILENAME, "UTF-8");

                // TRESC DOMYSLNEGO PLIKU KONFIGURACJI - TRZEBA TO PRZEROBIC I GDZIES PRZENIESC
                writer.println("LASTDB=");
                writer.close();
            } catch(IOException ioe) {
                Log.put(false, Level.INFO, "Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!", ConfigFileHandler.class.getName());
            }
        }

        try {
            propStream = new FileInputStream(configFile);
            prop.load(propStream);
            lastDatabase = prop.getProperty("LASTDB");
        } catch(IOException ioe) {
            System.out.println("Błąd odczytu z pliku konfiguracji !!!");
            Log.put(false, Level.INFO, "Błąd odczytu z pliku konfiguracji !!!", ConfigFileHandler.class.getName());
        }
    }

    public static void writeLastDatabase(String lastDatabase) {
        try {
            System.out.println("Last DB: "+lastDatabase);
            PrintWriter writer = new PrintWriter(CONFIG_FILENAME, "UTF-8");
            writer.println("LASTDB="+lastDatabase);
            writer.close();
        } catch(IOException ioe) {
            System.out.println("Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!");
            Log.put(false, Level.INFO, "Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!", ConfigFileHandler.class.getName());
        }
    }

    public static String getLastDatabase() {
        return lastDatabase;
    }
}
