package com.imagecompare.browser;

import javax.swing.*;
import java.awt.*;

class AddEditRecordDialog extends JDialog {

    static final int ADD = 0;
    static final int EDIT = 1;
    private Font font;
    private JScrollPane scrollPane;
    private JPanel mainPanel;

    AddEditRecordDialog(JFrame panel, String name, int type) {
        super(panel, name, true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(panel);
        this.setLayout(new BorderLayout());
        mainPanel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(mainPanel);
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
        titleLabel.setBounds(40, 0, 400, 20);

        JPanel northPanel = new JPanel(null);

        JButton addButton = new JButton("Dodaj plik");
        addButton.setBounds(90, 20, 150, 30);

        northPanel.add(titleLabel);
        northPanel.add(addButton);
        northPanel.setPreferredSize(new Dimension(250, 80));
        mainPanel.add(northPanel, BorderLayout.NORTH);

        JPanel mainFieldsPanel = new JPanel(new GridLayout(4, 4));
        mainFieldsPanel.add(new JLabel("Nazwa pliku"));
        mainFieldsPanel.add(new JTextField());
        mainFieldsPanel.add(new JLabel("Nazwa pliku"));
        mainFieldsPanel.add(new JTextField());
        mainFieldsPanel.add(new JLabel("Nazwa pliku"));
        mainFieldsPanel.add(new JTextField());
        mainFieldsPanel.add(new JLabel("Nazwa pliku"));
        mainFieldsPanel.add(new JTextField());

        mainPanel.add(mainFieldsPanel, BorderLayout.CENTER);
    }

    private void showEditRecordDialog() {

    }

    void showDialog(Boolean s) {
        this.setVisible(s);
    }
}
