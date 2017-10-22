package com.imagecompare.browser.gui.databasepane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class FilterInput extends JTextField implements FocusListener {

    private String placeholder;
    private String value;
    private JFrame frame;

    // TODO Kliknięcie (może wstawić tutaj FocusListener) powoduje że jeśli value jest != od placeholder to powoduje czyszczenie pola.

    public FilterInput(JFrame frame, String placeholder) {
        super(placeholder);
        this.frame = frame;
        this.placeholder = placeholder;
        this.value = placeholder;
        this.addFocusListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(frame.getWidth() / 8, 20);
    }


    @Override
    public void focusGained(FocusEvent e) {
        if (value.equals(placeholder)) {
            setText("");
        }
        System.out.println("Focus gained: " +this.value);
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (!this.getText().equals(value)) {
            value = getText();
        }

        //if (value.equals(placeholder)) {
        //    setText(placeholder);
        //}

        System.out.println("Focus lost: " +this.value);
    }
}
