package com.imagecompare.browser.model;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class RecordsTableModel extends AbstractTableModel {

    private String[][] data;
    private String columns[] = {"ID", "Nazwa", "Plik", "Parametr 1", "Parametr 2", "Parametr 3", "Parametr 4", "Parametr 5"};

    public RecordsTableModel(ArrayList<ImageItem> imageItems) {

        // Multiple HeadTable cells ???
        // Dodanie pola w celu wpisywania fraz do wyszukiwania

        data = new String[imageItems.size()][columns.length];
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
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        System.out.println("Zmieniona wartość w: " +rowIndex +" : " +columnIndex);
        System.out.println("Nowa wartość: " +aValue);

        // TUTAJ MOŻNA BEDZIE AKTUALIZOWAC DANE W BAZIE
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
}
