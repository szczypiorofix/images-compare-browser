package com.imagecompare.browser.gui;


import com.imagecompare.browser.model.DatabaseTableCellRenderer;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.model.RecordsTableModel;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
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

        tableOfRecords = new JTable(new RecordsTableModel(imageItems));

        //DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DatabaseTableCellRenderer centerRenderer = new DatabaseTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableOfRecords.setDefaultRenderer(String.class, centerRenderer);

        tableOfRecords.setCellSelectionEnabled(true);
        ListSelectionModel select = tableOfRecords.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        recordsTableScrollPane = new JScrollPane(tableOfRecords);
        recordsTableScrollPane.getVerticalScrollBar().setUnitIncrement(15);

        mainPanel.add(recordsTableScrollPane, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
    }
}
