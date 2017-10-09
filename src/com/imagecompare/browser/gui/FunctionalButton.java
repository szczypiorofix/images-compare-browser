package com.imagecompare.browser.gui;

import javax.swing.JButton;
import java.awt.*;


class FunctionalButton extends JButton {

    FunctionalButton(String title) {
        super(title);
        this.setBackground(new Color(22, 79, 111));
        this.setForeground(new Color(200,200, 200));
    }
}
