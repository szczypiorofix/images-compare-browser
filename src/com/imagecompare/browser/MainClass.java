package com.imagecompare.browser;

import java.awt.*;

public class MainClass {

    private MainWindow mainWindow;
    public static final String DATABASE_FILE_EXTENSION = "bdb";

    private MainClass() {

        ConfigFileHandler configFileHandler = new ConfigFileHandler();

        System.out.println(configFileHandler.getLastDatabase());
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
