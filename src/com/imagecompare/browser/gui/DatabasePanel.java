package com.imagecompare.browser.gui;


import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.model.RecordsTableModel;
import com.imagecompare.browser.system.SQLiteConnector;
import com.imagecompare.browser.table.DatabaseTableCellRenderer;
import com.imagecompare.browser.table.GroupableTableHeader;
import com.imagecompare.browser.table.TableRowFilter;
import javafx.scene.control.TableRow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class DatabasePanel extends JPanel {

    private FunctionalButton buttonAdd, buttonDelete, buttonEdit;
    private AddEditRecordDialog addRecordDialog, editRecordDialog;
    private String databaseFilename;
    private JTable tableOfRecords;
    private JScrollPane recordsTableScrollPane;
    private JPanel mainPanel,buttonsPanel, buttonsPanelGrid;

    public DatabasePanel(JFrame frame, String databaseFilename) {
        super(new BorderLayout());
        this.databaseFilename = databaseFilename;
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setOpaque(true);
        JLabel titlePane = new JLabel("Baza danych zdjęć");
        titlePane.setHorizontalAlignment(JLabel.CENTER);
        titlePane.setFont(new Font("Tahoma", Font.BOLD, 18));
        this.add(titlePane, BorderLayout.NORTH);

        mainPanel = new JPanel(new BorderLayout());

        buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanelGrid = new JPanel(new FlowLayout());

        addRecordDialog = new AddEditRecordDialog(frame, "Dodaj pozycję", AddEditRecordDialog.ADD);
        editRecordDialog = new AddEditRecordDialog(frame, "Edytuj pozycję", AddEditRecordDialog.EDIT);

        buttonAdd = new FunctionalButton("Dodaj");
        buttonAdd.addActionListener((ActionEvent e) -> {
            addRecordDialog.showDialog(true);
        });

        buttonEdit = new FunctionalButton("Edytuj");
        buttonEdit.addActionListener((ActionEvent e) -> {
            editRecordDialog.showDialog(true);
        });

        buttonDelete = new FunctionalButton("Usuń");
        buttonDelete.addActionListener((ActionEvent e) -> {
            String[] options = {"Tak", "Nie"};
            int n = JOptionPane.showOptionDialog(
                    frame,
                    "Usunąć pozycję?",
                    "Usunąć pozycję?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            System.out.println(n);
            // n = 0 : yes, n = 1 : no
        });

        buttonsPanelGrid.add(buttonAdd);
        buttonsPanelGrid.add(buttonEdit);
        buttonsPanelGrid.add(buttonDelete);

        buttonsPanel.add(buttonsPanelGrid);

        refresh();
    }

    public void refresh() {

        mainPanel.removeAll();
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);

        ArrayList<ImageItem> imageItems = new ArrayList<>();
        if (SQLiteConnector.connectToDatabase(this.databaseFilename)) {
            imageItems = SQLiteConnector.getResults();
        }

        RecordsTableModel recordsTableModel = new RecordsTableModel(imageItems);
        tableOfRecords = new JTable(recordsTableModel);// {
            //protected JTableHeader createDefaultTableHeader() {
           //     return new GroupableTableHeader(columnModel);
           // }
       // };

        //DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DatabaseTableCellRenderer centerRenderer = new DatabaseTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableOfRecords.setDefaultRenderer(String.class, centerRenderer);

        // SINGLE CELL SELECTION
        tableOfRecords.setCellSelectionEnabled(true);
        ListSelectionModel select = tableOfRecords.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableOfRecords.setFillsViewportHeight(true);
        tableOfRecords.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tableOfRecords.setFillsViewportHeight(true);


        TableRowFilter tableRowFilter = new TableRowFilter(recordsTableModel, "");

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(recordsTableModel);
        sorter.setRowFilter(tableRowFilter);
        recordsTableModel.setTableSorter(sorter);
        tableOfRecords.setRowSorter(sorter);


        /*TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = tableOfRecords.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setPreferredWidth(100); //third column is bigger
            } else {
                column.setPreferredWidth(50);
            }
        }*/
        recordsTableScrollPane = new JScrollPane(tableOfRecords);
        recordsTableScrollPane.getVerticalScrollBar().setUnitIncrement(15);

        mainPanel.add(recordsTableScrollPane, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
    }
}
