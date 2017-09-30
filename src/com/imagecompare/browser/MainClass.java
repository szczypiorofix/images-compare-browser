package com.imagecompare.browser;

import com.imagecompare.browser.system.Log;

import java.awt.*;
import java.util.logging.Level;

public class MainClass {

    private MainWindow mainWindow;
    public static final String DATABASE_FILE_EXTENSION = "bdb";

    private MainClass() {

        Log.DEBUG_MODE = true;
        Log.put(false, Level.INFO, "Application started.", this.getClass().getName());

        ConfigFileHandler configFileHandler = new ConfigFileHandler();

        //System.out.println(configFileHandler.getLastDatabase());
        mainWindow = new MainWindow();

        InitialDialog initialDialog = new InitialDialog(mainWindow, "Wybierz bazę danych", true, configFileHandler.getLastDatabase());
        initialDialog.showDialog(true);

        mainWindow.setAutoRequestFocus(true);
        if (initialDialog.isFileChosen()) {
            mainWindow.showWindow(true, initialDialog.getDatabaseFilename());
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
