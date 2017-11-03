package com.imagecompare.browser.gui.databasepane.table;

import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.model.RecordsTableModel;
import com.imagecompare.browser.system.Log;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;

public class DatabaseTable extends JTable implements MouseListener{

    private int selectedRow = 0;

    public DatabaseTable(RecordsTableModel recordsTableModel) {
        super(recordsTableModel);

        // SINGLE CELL SELECTION
        /*setCellSelectionEnabled(true);
        ListSelectionModel select = this.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);*/

        setPreferredScrollableViewportSize(new Dimension(500, 70));
        setFillsViewportHeight(true);

        setRowHeight(18);
        addMouseListener(this);
    }

    public void removeRecord(ImageItem imageItem) {
        SQLiteConnector.removeItemFromDatabase(imageItem);
        Log.put(false, Level.INFO, "Usunięto rekord z bazy danych: "+imageItem.getName(), this.getClass().getName());
    }

    @Override
    public int getSelectedRow() {
        return selectedRow;
    }


    public ImageItem getSelectedItem() {
        return new ImageItem(
                Integer.valueOf(getValueAt(selectedRow, 0).toString()),
                getValueAt(selectedRow, 1).toString(),
                getValueAt(selectedRow, 2).toString(),
                getValueAt(selectedRow, 3).toString(),
                getValueAt(selectedRow, 4).toString(),
                getValueAt(selectedRow, 5).toString(),
                getValueAt(selectedRow, 6).toString(),
                getValueAt(selectedRow, 7).toString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        selectedRow = rowAtPoint(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
