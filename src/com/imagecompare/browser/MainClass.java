package com.imagecompare.browser;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;


public class MainClass {

    private final String CONFIG_FILENAME = "browser.cfg";
    private File configFile = null;
    private InputStream propStream;
    private Properties prop = new Properties();
    private MainWindow mainWindow;
    private String lastDatabase = "";


    private MainClass() {

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

        mainWindow = new MainWindow();

        InitialDialog initialDialog = new InitialDialog(mainWindow, "Wybierz bazę danych", true, lastDatabase);
        initialDialog.showDialog(true);

        mainWindow.setAutoRequestFocus(true);
        if (initialDialog.isFileChoosen()) {
            mainWindow.showWindow(true);
        }
        else {
            initialDialog.dispose();
            mainWindow.dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(MainClass::new);
    }

}
