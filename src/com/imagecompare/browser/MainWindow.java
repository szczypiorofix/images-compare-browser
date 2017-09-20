package com.imagecompare.browser;

import javax.swing.*;

class MainWindow extends JFrame {

    MainWindow(String name) {
        super(name);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
    }
}
