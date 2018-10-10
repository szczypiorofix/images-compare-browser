package com.imagecompare.browser;

import com.imagecompare.browser.gui.main.InitialDialog;
import com.imagecompare.browser.gui.main.MainWindow;
import com.imagecompare.browser.system.ConfigFileHandler;
import com.imagecompare.browser.system.Log;

import java.awt.*;
import java.util.logging.Level;


/**
 * Images-Compare-Browser app (a.k.a. AdminImages)
 *
 * Main class of the application.
 *
 * ##########################################################################
 * @author Piotr Wróblewski
 * @version 0.94
 * ##########################################################################
 */
public class MainClass {

    public static final String DATABASE_FILE_EXTENSION_DB = "db";
    public static final String DATABASE_FILE_EXTENSION_DB3 = "db3";
    public static final String DATABASE_FILE_EXTENSION_SQLITE = "sqlite";
    public static final String DATABASE_FILE_EXTENSION_SQLITE3 = "sqlite3";


    /**
     * Main class constructor.
     * @param args - String -  array of parameters e.g. "debug" or "-debug", used for running in debug mode.
     */
    private MainClass(String[] args) {
        Log.DEBUG_MODE = false;
        if (args.length > 0) {
            if (args[0].equals("-debug") || args[0].equals("debug")) {
                Log.DEBUG_MODE = true;
            }
        }


        Log.DEBUG_IN_CONSOLE = true;
        Log.put(false, Level.INFO, "Start aplikacji ImageCompareBrowser a.k.a. Admin Images.", this.getClass().getName());

        ConfigFileHandler.init();

        MainWindow mainWindow = new MainWindow(ConfigFileHandler.getAppWidth(),
                ConfigFileHandler.getAppHeight(),
                ConfigFileHandler.isAppMaximized());

        InitialDialog initialDialog = new InitialDialog(mainWindow,
                "Wybierz bazę danych",
                true,
                ConfigFileHandler.getLastDatabase());
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

    /**
     * This is the main class of this application and method "main" is where the application starts.
     * @param args String - array of arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainClass(args));
    }
}
