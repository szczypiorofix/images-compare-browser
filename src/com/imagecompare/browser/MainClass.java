package com.imagecompare.browser;

import java.awt.*;

public class MainClass {

    private MainWindow mainWindow;

    private MainClass() {
        mainWindow = new MainWindow();
        mainWindow.showWindow(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(MainClass::new);
    }
}
