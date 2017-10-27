package com.imagecompare.browser.gui.databasepane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class FilterInput extends JTextField implements FocusListener, DocumentListener {

    private String placeholder;
    private boolean valueChanged = false;
    private boolean active = false;
    private JFrame frame;
    private GroupFilterInputs groupFilterInputs;

    public FilterInput(JFrame frame, String placeholder, GroupFilterInputs groupFilterInputs) {
        super();
        this.frame = frame;
        this.placeholder = placeholder;
        this.addFocusListener(this);
        this.getDocument().addDocumentListener(this);
        this.groupFilterInputs = groupFilterInputs;
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(frame.getWidth() / this.getParent().getComponents().length, 20);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!active && !valueChanged) {
            Color c = g.getColor();
            g.setColor(new Color(140, 140, 140));
            g.drawString(placeholder, 4, 15);
            g.setColor(c);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        active = true;
        this.repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        active = false;
        valueChanged = !getText().equals("");
        this.repaint();
    }

    public void updateFilter() {
        this.groupFilterInputs.updateFilters();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateFilter();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateFilter();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
