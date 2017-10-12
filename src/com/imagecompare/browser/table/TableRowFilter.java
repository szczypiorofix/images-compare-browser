package com.imagecompare.browser.table;

import com.imagecompare.browser.model.RecordsTableModel;

import javax.swing.*;

public class TableRowFilter extends RowFilter <Object, Object>{

    private RecordsTableModel recordsTableModel;
    private String filterText = "";

    public TableRowFilter(RecordsTableModel recordsTableModel, String filterText) {
        this.recordsTableModel = recordsTableModel;
        this.filterText = filterText;
    }

    @Override
    public boolean include(Entry entry) {
        String population = (String) entry.getValue(1);
        if (!population.equals("")) return true;
        return population.equals(filterText);
    }
}
