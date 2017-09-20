package com.imagecompare.browser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

final class MainWindow extends JFrame {

    private JPanel mainPanel;
    private BorderLayout borderLayout;
    private JSplitPane splitPane;

    MainWindow() {
        super("Browser");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);

        JPanel sd1 = new JPanel(new BorderLayout());
        JLabel lb1 = new JLabel("Hello");
        sd1.add(lb1);

        JPanel sd2 = new JPanel(new BorderLayout());
        JLabel lb2 = new JLabel("Hello");
        sd2.add(lb2);

        mainPanel = new JPanel(new BorderLayout());
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                sd1, sd2);
        splitPane.setResizeWeight(0.5);
        //splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(6);

        Image myImage = null;
        try {
            myImage = ImageIO.read(getClass().getResource("/spaceship.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myImage = myImage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon myImageIcon = new ImageIcon(myImage);
        JLabel l1 = new JLabel(myImageIcon);
        sd2.add(l1);

        mainPanel.add(splitPane);
        this.add(mainPanel);
    }

    void showWindow(Boolean s) {
        this.setVisible(s);
    }
}
