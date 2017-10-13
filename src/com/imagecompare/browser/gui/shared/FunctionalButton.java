package com.imagecompare.browser.gui.shared;

import javax.swing.JButton;
import java.awt.*;


public class FunctionalButton extends JButton {

    public FunctionalButton(String title) {
        super(title);
        this.setBackground(new Color(22, 79, 111));
        this.setForeground(new Color(200,200, 200));
    }
}
