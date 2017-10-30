package com.imagecompare.browser.gui.databasepane.table;

import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.model.RecordsTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DatabaseTable extends JTable implements MouseListener{

    private RecordsTableModel recordsTableModel;
    private int selectedRow = 0;

    public DatabaseTable(RecordsTableModel recordsTableModel) {
        super(recordsTableModel);
        this.recordsTableModel = recordsTableModel;

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
        //repaint();

    }

    @Override
    public int getSelectedRow() {
        return selectedRow;
    }

    public ImageItem getSelectedItem() {
        return new ImageItem(Integer.valueOf(getValueAt(selectedRow, 0).toString()), getValueAt(selectedRow, 1).toString(), getValueAt(selectedRow, 2).toString(), getValueAt(selectedRow, 1).toString(), getValueAt(selectedRow, 1).toString(), getValueAt(selectedRow, 1).toString(), getValueAt(selectedRow, 1).toString(), getValueAt(selectedRow, 1).toString());
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
