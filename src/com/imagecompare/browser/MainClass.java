package com.imagecompare.browser;

import com.imagecompare.browser.gui.InitialDialog;
import com.imagecompare.browser.system.ConfigFileHandler;
import com.imagecompare.browser.system.Log;

import java.awt.*;
import java.util.logging.Level;

public class MainClass {

    private MainWindow mainWindow;
    public static final String DATABASE_FILE_EXTENSION_DB = "db";
    public static final String DATABASE_FILE_EXTENSION_DB3 = "db3";
    public static final String DATABASE_FILE_EXTENSION_SQLITE = "sqlite";
    public static final String DATABASE_FILE_EXTENSION_SQLITE3 = "sqlite3";

    private MainClass(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-debug") || args[0].equals("debug")) {
                Log.DEBUG_MODE = true;
            }
        }
        else {
            Log.DEBUG_MODE = false;
        }

        Log.put(false, Level.INFO, "Start aplikacji.", this.getClass().getName());

        ConfigFileHandler.init();

        //System.out.println(configFileHandler.getLastDatabase());
        mainWindow = new MainWindow();

        InitialDialog initialDialog = new InitialDialog(mainWindow, "Wybierz bazę danych", true, ConfigFileHandler.getLastDatabase());
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
        EventQueue.invokeLater(() -> new MainClass(args));
    }

}
