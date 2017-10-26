package com.imagecompare.browser.gui.databasepane;


import com.imagecompare.browser.gui.databasepane.table.TableRowFilter;
import com.imagecompare.browser.gui.shared.FunctionalButton;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.model.RecordsTableModel;
import com.imagecompare.browser.system.SQLiteConnector;
import com.imagecompare.browser.gui.databasepane.table.DatabaseTableCellRenderer;

import javax.swing.*;
import javax.swing.border.Border;
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
    private JTable tableOfRecords;
    private JScrollPane recordsTableScrollPane;
    private JFrame frame;
    private JPanel mainPanel,buttonsPanel, buttonsPanelGrid, tableAndFiltersPanel;

    public DatabasePanel(JFrame frame, String databaseFilename) {
        super(new BorderLayout());
        this.databaseFilename = databaseFilename;
        this.frame = frame;
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
        buttonAdd.addActionListener((ActionEvent e) -> addRecordDialog.showDialog(true));

        buttonEdit = new FunctionalButton("Edytuj");
        buttonEdit.addActionListener((ActionEvent e) -> editRecordDialog.showDialog(true));

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

        buttonPrint = new FunctionalButton("Drukuj");
        buttonPrint.addActionListener((ActionEvent e) -> {
            // DRUKOWANIE TABLICY
            try {
                boolean complete = tableOfRecords.print();
                if (complete) {
                    /* show a success message  */
                } else {
                    /*show a message indicating that printing was cancelled */
                }
            } catch (PrinterException pe) {
                /* Printing failed, report to the user */
            }
        });

        buttonsPanelGrid.add(buttonAdd);
        buttonsPanelGrid.add(buttonEdit);
        buttonsPanelGrid.add(buttonDelete);
        buttonsPanelGrid.add(buttonPrint);

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
        //};

        // TODO Dodać pasek postępu przy wczytywaniu danych z bazy w momencie ładowania programu
        // TODO Dodać wstępną opcję drukowania wyników z bazy - o ile się da

        //DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DatabaseTableCellRenderer centerRenderer = new DatabaseTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        //tableOfRecords.setDefaultRenderer(String.class, centerRenderer);

        // SINGLE CELL SELECTION
        tableOfRecords.setCellSelectionEnabled(true);
        ListSelectionModel select = tableOfRecords.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableOfRecords.setFillsViewportHeight(true);
        tableOfRecords.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tableOfRecords.setFillsViewportHeight(true);



        TableRowFilter tableRowFilter = new TableRowFilter();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(recordsTableModel);

        recordsTableScrollPane = new JScrollPane(tableOfRecords);
        recordsTableScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        tableAndFiltersPanel = new JPanel(new BorderLayout());
        tableAndFiltersPanel.add(recordsTableScrollPane, BorderLayout.CENTER);

        // Panel grupujący filter inputy.
        GroupFilterInputs groupFilterInputs = new GroupFilterInputs(frame, sorter);
        tableRowFilter.setFilterInputs(groupFilterInputs.getFilterInputs());
        groupFilterInputs.updateFilters();

        sorter.setRowFilter(tableRowFilter);
        tableOfRecords.setRowSorter(sorter);

        tableAndFiltersPanel.add(groupFilterInputs, BorderLayout.NORTH);
        groupFilterInputs.setSorter(sorter);


        mainPanel.add(tableAndFiltersPanel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    public JTable getTableOfRecords() {
        return tableOfRecords;
    }
}
