package com.imagecompare.browser.gui;

import javax.swing.*;
import java.awt.*;

public class MainTabbedPanel extends JTabbedPane {

    private Color firstTabColor = new Color(127, 14, 235);
    private Color secondTabColor = new Color(127, 13, 224);

    public MainTabbedPanel() {
        super();
        Font font = new Font("Tahoma", Font.PLAIN, 14);
        this.setFont(font);
    }

    public void setColors() {
        //this.setForegroundAt(0, firstTabColor);
        //this.setForegroundAt(1, secondTabColor);
    }
}
