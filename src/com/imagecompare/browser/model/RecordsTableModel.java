package com.imagecompare.browser.model;

import com.imagecompare.browser.gui.databasepane.table.TableRowFilter;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;

public class RecordsTableModel extends AbstractTableModel {

    private final Object[][] data;
    private final String columns[] = {"ID", "Nazwa", "Plik", "Parametr 1", "Parametr 2", "Parametr 3", "Parametr 4", "Parametr 5"};
    private String filterValue = "";
    private TableRowSorter tableRowSorter;

    public RecordsTableModel(ArrayList<ImageItem> imageItems) {

        data = new Object[imageItems.size()][columns.length];
        for (int i = 0; i < imageItems.size(); i++) {
                data[i][0] = String.valueOf(imageItems.get(i).getId());
                data[i][1] = imageItems.get(i).getName();
                data[i][2] = imageItems.get(i).getFilename();
                data[i][3] = imageItems.get(i).getParam1();
                data[i][4] = imageItems.get(i).getParam2();
                data[i][5] = imageItems.get(i).getParam3();
                data[i][6] = imageItems.get(i).getParam4();
                data[i][7] = imageItems.get(i).getParam5();

        }
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
        //return false;
        // EDYTOWALNE POLA
        //if (rowIndex > 0)
        return true;
        //else return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        System.out.println("Zmieniona wartość w: " +rowIndex +" : " +columnIndex);
        System.out.println("Nowa wartość: " +aValue);

        filterValue = aValue.toString();
        TableRowFilter tableRowFilter = new TableRowFilter(this, "");
        tableRowSorter.setRowFilter(tableRowFilter);
        this.data[rowIndex][columnIndex] = aValue.toString();

        // TUTAJ MOŻNA BEDZIE AKTUALIZOWAC DANE W BAZIE
    }

    public void setTableSorter(TableRowSorter tableRowSorter) {
        this.tableRowSorter = tableRowSorter;
    }

    public String getFilterValue() {
        return filterValue;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
}
