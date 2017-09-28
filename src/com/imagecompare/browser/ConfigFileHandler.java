package com.imagecompare.browser;

import java.io.*;
import java.util.Properties;

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
        }
        else {
            System.out.println("Tworzenie domyślnego pliku konfiguracji.");
            try {
                PrintWriter writer = new PrintWriter(CONFIG_FILENAME, "UTF-8");
                writer.println("LASTDB=");
                writer.close();
            } catch(IOException ioe) {
                System.out.println("Błąd przy próbie utworzenia domyślnego pliku konfiguracji !!!");
            }
        }

        try {
            propStream = new FileInputStream(configFile);
            prop.load(propStream);
            lastDatabase = prop.getProperty("LASTDB");
        } catch(IOException ioe) {
            System.out.println("Błąd odczytu z pliku konfiguracji !!!");
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
        }
    }

    String getLastDatabase() {
        return lastDatabase;
    }
}