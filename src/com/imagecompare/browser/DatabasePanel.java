package com.imagecompare.browser;


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


class DatabasePanel extends JPanel {

    private FunctionalButton buttonAdd, buttonDelete, buttonEdit;
    private AddEditRecordDialog addRecordDialog, editRecordDialog;

    DatabasePanel(JFrame frame) {
        super(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel titlePane = new JLabel("Baza danych zdjęć");
        titlePane.setHorizontalAlignment(JLabel.CENTER);
        titlePane.setFont(new Font("Tahoma", Font.BOLD, 18));
        this.add(titlePane, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JPanel buttonsPanelGrid = new JPanel(new FlowLayout());

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

        mainPanel.add(buttonsPanel, BorderLayout.NORTH);

/*        String data[][]={
                {"1", "plik1.jpg", "670000", "123", "456", "789", "0"},
                {"2", "plik2.jpg", "780000", "123", "456", "789", "0"},
                {"3", "plik3.jpg", "780000", "123", "456", "789", "0"},
                {"4", "plik4.jpg", "780000", "123", "456", "789", "0"},
                {"5", "plik5.jpg", "780000", "123", "456", "789", "0"},
                {"6", "plik6.jpg", "780000", "123", "456", "789", "0"},
                {"7", "plik7.jpg", "780000", "123", "456", "789", "0"},
                {"8", "plik8.jpg", "780000", "123", "456", "789", "0"},
                {"9", "plik9.jpg", "780000", "123", "456", "789", "0"},
                {"10", "plik10.jpg", "700000", "123", "456", "789", "0"}
        };*/

        String data[][] = new String[0][0];

        SQLiteConnector.getResults();

        final JTable recordsTable = new JTable(new RecordsTableModel(data));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        recordsTable.setDefaultRenderer(String.class, centerRenderer);

        recordsTable.setCellSelectionEnabled(true);
        ListSelectionModel select= recordsTable.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane recordsTableScrollPane = new JScrollPane(recordsTable);
        recordsTableScrollPane.getVerticalScrollBar().setUnitIncrement(15);

        mainPanel.add(recordsTableScrollPane, BorderLayout.CENTER);


        this.add(mainPanel, BorderLayout.CENTER);
    }
}
