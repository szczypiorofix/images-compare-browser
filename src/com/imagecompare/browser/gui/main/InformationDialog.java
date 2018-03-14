package com.imagecompare.browser.gui.main;

import javax.swing.*;
import java.awt.*;


public class InformationDialog extends JDialog {

    public InformationDialog(JFrame frame, String title, Boolean isModal) {
        super(frame, title, isModal);
        createGUI(frame);
    }

    private void createGUI(JFrame frame) {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(250, 180);
        this.setResizable(false);
        this.setLocationRelativeTo(frame);
        this.setLayout(null);
        this.setUndecorated(false);

        JButton bOK = new JButton("OK");
        bOK.addActionListener(e -> this.dispose());
        bOK.setBounds(80, 110, 90, 30);

        JLabel titleLabel = new JLabel(MainWindow.frameTitleName);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        titleLabel.setBounds(75, 0, 240, 50);

        JLabel versionContentLabel = new JLabel("ver. 0.94");
        versionContentLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        versionContentLabel.setBounds(100, 30, 50, 50);

        JLabel copyrightContentLabel = new JLabel("Copyright \u00a9 Wróblewski Piotr 2018");
        copyrightContentLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        copyrightContentLabel.setSize(new Dimension(250, 100));
        copyrightContentLabel.setBounds(20, 55, 220, 50);

        this.add(titleLabel);
        this.add(versionContentLabel);
        this.add(copyrightContentLabel);
        this.add(bOK);
    }
}
