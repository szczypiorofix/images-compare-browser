package com.imagecompare.editor;

import java.awt.*;

public class MainClass {

    private MainClass() {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainClass();
            }
        });
    }
}
