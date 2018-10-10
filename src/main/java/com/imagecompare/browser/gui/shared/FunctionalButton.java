package com.imagecompare.browser.gui.shared;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class FunctionalButton extends JButton implements MouseListener {

    private final int BORDER_THICKNESS = 3;
    private final int BUTTON_PADDING = 4;
    private final Color BORDER_COLOR = new Color(100, 120, 120);
    private final Color BORDER_COLOR_FOCUS = new Color(70, 90, 90);
    private final Color FONT_COLOR = new Color(22, 79, 111);
    private final Color FONT_BACKGROUND_COLOR = new Color(200,200, 200);


    public FunctionalButton() {
        super();
    }

    public FunctionalButton(String title) {
        super(title);

        /*this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        setOpaque(false);*/

        /*this.setBackground(FONT_BACKGROUND_COLOR);
        this.setForeground(FONT_COLOR);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, BORDER_THICKNESS),
                BorderFactory.createLineBorder(FONT_BACKGROUND_COLOR, BUTTON_PADDING)));
        this.addMouseListener(this);*/
    }


    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR_FOCUS, BORDER_THICKNESS),
                BorderFactory.createLineBorder(FONT_BACKGROUND_COLOR, BUTTON_PADDING)));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, BORDER_THICKNESS),
                BorderFactory.createLineBorder(FONT_BACKGROUND_COLOR, BUTTON_PADDING)));
    }
}
