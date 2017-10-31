package com.imagecompare.browser.gui.databasepane;


import com.imagecompare.browser.gui.databasepane.table.DatabaseTable;
import com.imagecompare.browser.gui.databasepane.table.TableRowFilter;
import com.imagecompare.browser.gui.imagepane.ImagePanelEast;
import com.imagecompare.browser.gui.shared.FunctionalButton;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.model.RecordsTableModel;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.util.ArrayList;


public class DatabasePanel extends JPanel {

    private FunctionalButton buttonAdd, buttonDelete, buttonEdit, buttonPrint;
    private AddEditRecordDialog addRecordDialog, editRecordDialog;
    private String databaseFilename;
    private DatabaseTable tableOfRecords;
    private JScrollPane recordsTableScrollPane;
    private JFrame frame;
    private JPanel mainPanel,buttonsPanel, buttonsPanelGrid, tableAndFiltersPanel;
    private GroupFilterInputs groupFilterInputs;
    private ImagePanelEast imagePanelEast = null;

    public DatabasePanel(JFrame frame, String databaseFilename) {
        super(new BorderLayout());
        this.databaseFilename = databaseFilename;
        this.frame = frame;
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setOpaque(true);
    }


    public void setImagePanelEast(ImagePanelEast imagePanelEast) {
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
        buttonAdd.addActionListener((ActionEvent e) -> addRecordDialog.showDialog(true));

        buttonEdit = new FunctionalButton("Edytuj");
        buttonEdit.addActionListener((ActionEvent e) -> editRecordDialog.showDialog(true));

        buttonDelete = new FunctionalButton("Usuń");
        buttonDelete.addActionListener((ActionEvent e) -> {
            String[] options = {"Tak", "Nie"};
            int n = JOptionPane.showOptionDialog(
                    frame,
                    "Usunąć pozycję " +tableOfRecords.getSelectedItem().getName() + "?",
                    "Usunąć pozycję?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            // 0=yes, 1=no
            if (n == 0) {
                tableOfRecords.removeRecord(tableOfRecords.getSelectedItem());
                refresh();
                imagePanelEast.setFilteredData(tableOfRecords);
                imagePanelEast.refresh(true);
            }
        });

        buttonPrint = new FunctionalButton("Drukuj");
        buttonPrint.addActionListener((ActionEvent e) -> {
            // DRUKOWANIE TABLICY
            try {
                boolean complete = tableOfRecords.print();
                if (complete) {
                    JOptionPane.showMessageDialog(this, "Drukowanie powiodło się", "Drukowanie przebiegło pomyślnie", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Drukowanie nie powiodło się", "Drukowanie zostało przerwane.", JOptionPane.WARNING_MESSAGE);
                }
            } catch (PrinterException pe) {
                JOptionPane.showMessageDialog(this, "Drukowanie nie powiodło się", "Błąd drukowania. Drukowanie nie powiodło się.", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonsPanelGrid.add(buttonAdd);
        buttonsPanelGrid.add(buttonEdit);
        buttonsPanelGrid.add(buttonDelete);
        buttonsPanelGrid.add(buttonPrint);

        buttonsPanel.add(buttonsPanelGrid);

        refresh();
        groupFilterInputs.setImagePanelEast(imagePanelEast);
    }

    public void refresh() {
        mainPanel.removeAll();
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);

        ArrayList<ImageItem> imageItems = new ArrayList<>();
        if (SQLiteConnector.connectToDatabase(this.databaseFilename)) {
            imageItems = SQLiteConnector.getResults();
        }

        RecordsTableModel recordsTableModel = new RecordsTableModel(imageItems);
        tableOfRecords = new DatabaseTable(recordsTableModel);

        TableRowFilter tableRowFilter = new TableRowFilter();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(recordsTableModel);

        recordsTableScrollPane = new JScrollPane(tableOfRecords);
        recordsTableScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        tableAndFiltersPanel = new JPanel(new BorderLayout());
        tableAndFiltersPanel.add(recordsTableScrollPane, BorderLayout.CENTER);

        // Panel grupujący filter inputy.
        groupFilterInputs = new GroupFilterInputs(frame, sorter, imagePanelEast, tableOfRecords);
        tableRowFilter.setFilterInputs(groupFilterInputs.getFilterInputs());
        groupFilterInputs.updateFilters();

        sorter.setRowFilter(tableRowFilter);
        tableOfRecords.setRowSorter(sorter);

        tableAndFiltersPanel.add(groupFilterInputs, BorderLayout.NORTH);
        groupFilterInputs.setSorter(sorter);

        mainPanel.add(tableAndFiltersPanel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    public DatabaseTable getTableOfRecords() {
        return tableOfRecords;
    }
}
