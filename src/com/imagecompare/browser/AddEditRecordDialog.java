package com.imagecompare.browser;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.WindowConstants;

import java.awt.*;
import java.awt.event.ActionEvent;


class AddEditRecordDialog extends JDialog {

    static final int ADD = 0;
    static final int EDIT = 1;
    private Font font;
    private JScrollPane scrollPane;
    private JPanel mainPanel;

    AddEditRecordDialog(JFrame panel, String name, int type) {
        super(panel, name, true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(400, 280);
        this.setLocationRelativeTo(panel);
        this.setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        font = new Font("Tahoma", Font.PLAIN, 16);
        if (type == ADD) {
            showAddRecordDialog();
        }
        else {
            showEditRecordDialog();
        }
        this.add(scrollPane);
    }

    private void showAddRecordDialog() {
        JLabel titleLabel = new JLabel("Dodawanie nowej pozycji do bazy danych");
        titleLabel.setFont(font);

        JPanel northPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Dodaj plik");
        addButton.addActionListener((ActionEvent e) -> {
            OpenFileDialog fc = new OpenFileDialog();
            int returnVal = fc.showDialog(this, "Otwórz");
        });

        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(addButton, BorderLayout.SOUTH);
        northPanel.setPreferredSize(new Dimension(250, 80));
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // INPUT FIELDS
        JTextField fieldItemName = new JTextField();
        JTextField fieldItemParam1 = new JTextField();
        JTextField fieldItemParam2 = new JTextField();
        JTextField fieldItemParam3 = new JTextField();
        JTextField fieldItemParam4 = new JTextField();

        JPanel mainFieldsPanel = new JPanel(new GridLayout(5, 4));
        mainFieldsPanel.add(new JLabel("Nazwa"));
        mainFieldsPanel.add(fieldItemName);
        mainFieldsPanel.add(new JLabel("Parametr 1"));
        mainFieldsPanel.add(fieldItemParam1);
        mainFieldsPanel.add(new JLabel("Parametr 2"));
        mainFieldsPanel.add(fieldItemParam2);
        mainFieldsPanel.add(new JLabel("Parametr 3"));
        mainFieldsPanel.add(fieldItemParam3);
        mainFieldsPanel.add(new JLabel("Parametr 4"));
        mainFieldsPanel.add(fieldItemParam4);

        JPanel buttonSubmitPanel = new JPanel(new FlowLayout());
        JButton buttonSubmit = new JButton("Zapisz");
        buttonSubmitPanel.add(buttonSubmit);

        mainPanel.add(buttonSubmitPanel, BorderLayout.SOUTH);
        mainPanel.add(mainFieldsPanel, BorderLayout.CENTER);
    }

    private void showEditRecordDialog() {

    }

    void showDialog(Boolean s) {
        this.setVisible(s);
    }
}
