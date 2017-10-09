package com.imagecompare.browser.model;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class RecordsTableModel extends AbstractTableModel {

    private String[][] data;
    private String columns[]={"ID", "Nazwa", "Nazwa pliku", "Parametr 1", "Parametr 2", "Parametr 3", "Parametr 4"};

    public RecordsTableModel(String[][] data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns[columnIndex].getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
