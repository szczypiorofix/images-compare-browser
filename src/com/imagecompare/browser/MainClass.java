package com.imagecompare.browser;

import java.awt.*;

public class MainClass {

    private MainWindow mainWindow;

    private MainClass() {
        mainWindow = new MainWindow("Browser");
        mainWindow.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainClass();
            }
        });
    }
}
