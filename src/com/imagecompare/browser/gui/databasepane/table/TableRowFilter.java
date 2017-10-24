package com.imagecompare.browser.gui.databasepane.table;

import com.imagecompare.browser.gui.databasepane.FilterInput;
import com.imagecompare.browser.model.RecordsTableModel;

import javax.swing.*;

public class TableRowFilter extends RowFilter <Object, Object> {

    private final RecordsTableModel recordsTableModel;
    private FilterInput[] filterInputs;

    public TableRowFilter(RecordsTableModel recordsTableModel, FilterInput[] filterInputs) {
        this.recordsTableModel = recordsTableModel;
        this.filterInputs = filterInputs;
    }

    @Override
    public boolean include(Entry entry) {
        String entryValue = (String) entry.getValue(1);

        String[] entryValues = new String[filterInputs.length];
        for (int i = 0; i < filterInputs.length; i++) {
            entryValues[i] = filterInputs[i].getText();
        }



        //return entryValue.toLowerCase().contains(filterText.toLowerCase());
        return false;
    }
}
