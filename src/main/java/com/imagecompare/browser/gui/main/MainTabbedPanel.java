package com.imagecompare.browser.gui.main;

import javax.swing.*;
import java.awt.*;

public class MainTabbedPanel extends JTabbedPane {

    private Color firstTabColor = new Color(85, 14, 235);
    private Color secondTabColor = new Color(90, 13, 224);

    public MainTabbedPanel() {
        super();
        this.setFont(new Font("Tahoma", Font.PLAIN, 14));
    }

    public void setColors() {
        //this.setForegroundAt(0, firstTabColor);
        //this.setForegroundAt(1, secondTabColor);
    }
}
