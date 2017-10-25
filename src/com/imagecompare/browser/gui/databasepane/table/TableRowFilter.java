package com.imagecompare.browser.gui.databasepane.table;

import com.imagecompare.browser.gui.databasepane.FilterInput;

import javax.swing.*;

public class TableRowFilter extends RowFilter <Object, Object> {

    private FilterInput[] filterInputs;

    public TableRowFilter() {
    }

    public void setFilterInputs(FilterInput[] filterInputs) {
        this.filterInputs = filterInputs;
    }

    @Override
    public boolean include(Entry entry) {
        String[] entryValues = new String[filterInputs.length];
        for (int i = 0; i < filterInputs.length; i++) {
            entryValues[i] = (String) entry.getValue(i);
        }
        return (entryValues[1].toLowerCase().contains(filterInputs[1].getText())
                && (entryValues[3].toLowerCase().contains(filterInputs[3].getText()))
                && (entryValues[4].toLowerCase().contains(filterInputs[4].getText()))
                && (entryValues[5].toLowerCase().contains(filterInputs[5].getText()))
                && (entryValues[6].toLowerCase().contains(filterInputs[6].getText()))
                && (entryValues[7].toLowerCase().contains(filterInputs[7].getText())));
    }
}
