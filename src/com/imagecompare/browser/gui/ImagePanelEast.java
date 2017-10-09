package com.imagecompare.browser.gui;

import javax.swing.*;
import java.awt.*;

public class ImagePanelEast extends JPanel {

    public ImagePanelEast() {
        super(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("Lista obrazków"), BorderLayout.NORTH);

        this.add(mainPanel, BorderLayout.CENTER);
    }
}
