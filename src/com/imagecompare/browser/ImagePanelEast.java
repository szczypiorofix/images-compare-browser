package com.imagecompare.browser;

import javax.swing.*;
import java.awt.*;

class ImagePanelEast extends JPanel {

    ImagePanelEast() {
        super(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("Lista obrazków"), BorderLayout.NORTH);

        this.add(mainPanel, BorderLayout.CENTER);
    }
}
