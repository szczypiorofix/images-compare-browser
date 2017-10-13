package com.imagecompare.browser.gui.databasepane.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class DatabaseTableCellRenderer extends DefaultTableCellRenderer{


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        JLabel c = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        //Object valueAt = table.getModel().getValueAt(row, col);
        if (row == 1) {
            c.setToolTipText("Filtrowanie wyników...");
        }

        return c;
    }
}