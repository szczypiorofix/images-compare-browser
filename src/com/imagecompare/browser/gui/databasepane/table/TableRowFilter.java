package com.imagecompare.browser.gui.databasepane.table;

import com.imagecompare.browser.model.RecordsTableModel;

import javax.swing.*;

public class TableRowFilter extends RowFilter <Object, Object>{

    private final RecordsTableModel recordsTableModel;
    private String filterText = "";

    public TableRowFilter(RecordsTableModel recordsTableModel, String filterText) {
        this.recordsTableModel = recordsTableModel;
        this.filterText = filterText;
    }

    @Override
    public boolean include(Entry entry) {
        String entryValue = (String) entry.getValue(0);
        return !entryValue.equals("") || entryValue.equals(this.filterText);
    }
}
