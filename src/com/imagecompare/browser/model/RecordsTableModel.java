package com.imagecompare.browser.model;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class RecordsTableModel extends AbstractTableModel {

    private String[][] data;
    private String columns[]={"ID", "Nazwa", "Plik", "Parametr 1", "Parametr 2", "Parametr 3", "Parametr 4"};

    public RecordsTableModel(ArrayList<ImageItem> imageItems) {
        String[][] d = new String[imageItems.size()][columns.length];
        for (int i = 0; i < imageItems.size(); i++) {
            d[i][0] = String.valueOf(imageItems.get(i).getId());
            d[i][1] = imageItems.get(i).getName();
            d[i][2] = imageItems.get(i).getFilename();
            d[i][3] = "bla";
            d[i][4] = "bla";
            d[i][5] = "bla";
            d[i][6] = "bla";
        }
        this.data = d;
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
