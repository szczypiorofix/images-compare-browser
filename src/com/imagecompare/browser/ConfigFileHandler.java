package com.imagecompare.browser;

import com.imagecompare.browser.system.Log;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

class ConfigFileHandler {

    private final String CONFIG_FILENAME = "browser.cfg";
    private File configFile = null;
    private InputStream propStream;
    private Properties prop = new Properties();
    private String lastDatabase = "";

    ConfigFileHandler() {
        init();
    }

    private void init() {
        configFile = new File(CONFIG_FILENAME);
        if(configFile.exists() && !configFile.isDirectory()) {
            System.out.println("Plik konfiguracji - OK.");
            Log.put(false, Level.INFO, "Plik konfiguracji - OK.", this.getClass().getName());
        }
        else {
            System.out.println("Tworzenie domyślnego pliku konfiguracji.");
            Log.put(false, Level.INFO, "Brak pliku konfiguracji. Tworzenie nowego pliku z domyślnymi wartościami.", this.getClass().getName());
            try {
                PrintWriter writer = new PrintWriter(CONFIG_FILENAME, "UTF-8");

                // TRESC DOMYSLNEGO PLIKU KONFIGURACJI - TRZEBA TO PRZEROBIC I GDZIES PRZENIESC
                writer.println("LASTDB=");
                writer.close();
            } catch(IOException ioe) {
                System.out.println("Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!");
                Log.put(false, Level.INFO, "Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!", this.getClass().getName());
            }
        }

        try {
            propStream = new FileInputStream(configFile);
            prop.load(propStream);
            lastDatabase = prop.getProperty("LASTDB");
        } catch(IOException ioe) {
            System.out.println("Błąd odczytu z pliku konfiguracji !!!");
            Log.put(false, Level.INFO, "Błąd odczytu z pliku konfiguracji !!!", this.getClass().getName());
        }
    }

    void writeLastDatabase(String lastDatabase) {
        this.lastDatabase = lastDatabase;
        try {
            System.out.println("Last DB: "+lastDatabase);
            PrintWriter writer = new PrintWriter(CONFIG_FILENAME, "UTF-8");
            writer.println("LASTDB="+lastDatabase);
            writer.close();
        } catch(IOException ioe) {
            System.out.println("Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!");
            Log.put(false, Level.INFO, "Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!", this.getClass().getName());
        }
    }

    String getLastDatabase() {
        return lastDatabase;
    }
}
