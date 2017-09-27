package com.imagecompare.browser;

import javax.swing.*;
import java.awt.*;


class InformationDialog extends JDialog {

    InformationDialog(JFrame frame, String title, Boolean isModal) {
        super(frame, title, isModal);
        createGUI(frame);
    }

    private void createGUI(JFrame frame) {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(250, 170);
        this.setResizable(false);
        this.setLocationRelativeTo(frame);
        this.setLayout(null);
        this.setUndecorated(false);

        JButton bOK = new JButton("OK");
        bOK.addActionListener(e -> this.dispose());
        bOK.setBounds(80, 100, 90, 30);

        JLabel titleLabel = new JLabel("Images Compare Browser");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        titleLabel.setBounds(20, 0, 240, 50);

        JLabel versionContentLabel = new JLabel("ver. 0.5");
        versionContentLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        versionContentLabel.setBounds(100, 35, 50, 50);

        JLabel copyrightContentLabel = new JLabel("Copyright \u00a9 Wróblewski Piotr 2017");
        copyrightContentLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        copyrightContentLabel.setSize(new Dimension(250, 100));
        copyrightContentLabel.setBounds(20, 55, 220, 50);

        this.add(titleLabel);
        this.add(versionContentLabel);
        this.add(copyrightContentLabel);
        this.add(bOK);
    }
}
