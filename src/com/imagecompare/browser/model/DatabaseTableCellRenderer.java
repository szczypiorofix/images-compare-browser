package com.imagecompare.browser.model;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class DatabaseTableCellRenderer extends DefaultTableCellRenderer{


    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {

        Component c = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, col);
        Object valueAt = table.getModel().getValueAt(row, col);
        String s = "";
        if (valueAt != null) {
            s = valueAt.toString();
        }

        if (s.equalsIgnoreCase("hoho")) {
            c.setForeground(Color.YELLOW);
            c.setBackground(Color.gray);
        } else {
            c.setForeground(Color.black);
            c.setBackground(Color.WHITE);
        }

        return c;
    }
}
