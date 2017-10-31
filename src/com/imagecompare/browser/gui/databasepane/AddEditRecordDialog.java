package com.imagecompare.browser.gui.databasepane;


import com.imagecompare.browser.MainClass;
import com.imagecompare.browser.gui.shared.OpenFileDialog;
import com.imagecompare.browser.system.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;


public class AddEditRecordDialog extends JDialog {

    static final int ADD = 0;
    static final int EDIT = 1;
    private Font font;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JPanel northPanel;
    private JLabel titleLabel;
    private JButton addButton;
    private JPanel mainFieldsPanel;
    private JButton buttonSubmit, buttonNext, buttonPrev;
    private JPanel buttonSubmitPanel;
    private JLabel fieldItemId;
    private JTextField fieldItemName, fieldItemParam1, fieldItemParam2, fieldItemParam3, fieldItemParam4, fieldItemParam5;

    public AddEditRecordDialog(JFrame panel, String name, int type) {
        super(panel, name, true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
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
        showCommonContent(true);
        titleLabel.setText("Dodawanie rekordu:");
    }

    private void showEditRecordDialog() {
        setSize(400, 280);
        showCommonContent(false);
        titleLabel.setText("Edycja rekordu:");
    }

    private void showCommonContent(boolean addFile) {
        titleLabel = new JLabel();
        titleLabel.setFont(font);

        northPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Dodaj plik");
        addButton.addActionListener((ActionEvent e) -> {
            OpenFileDialog fc = new OpenFileDialog(OpenFileDialog.IMAGE_FILE);
            int returnVal = fc.showDialog(this, "Otwórz");
        });

        northPanel.add(titleLabel, BorderLayout.NORTH);
        if (addFile) {
            northPanel.add(addButton, BorderLayout.SOUTH);
        }

        northPanel.setPreferredSize(new Dimension(250, 40));
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // INPUT FIELDS
        fieldItemId = new  JLabel();
        fieldItemName = new JTextField();
        fieldItemParam1 = new JTextField();
        fieldItemParam2 = new JTextField();
        fieldItemParam3 = new JTextField();
        fieldItemParam4 = new JTextField();
        fieldItemParam5 = new JTextField();

        JLabel idNameLabel = new JLabel();
        if (!addFile) {
            fieldItemId.setText("ID number");
            idNameLabel.setText("ID");
        }

        mainFieldsPanel = new JPanel(new GridLayout(7, 4));
        mainFieldsPanel.add(idNameLabel);
        mainFieldsPanel.add(fieldItemId);
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
        mainFieldsPanel.add(new JLabel("Parametr 5"));
        mainFieldsPanel.add(fieldItemParam5);

        buttonSubmitPanel = new JPanel(new FlowLayout());

        buttonSubmit = new JButton("Zapisz");
        buttonNext = new JButton();
        buttonPrev = new JButton();

        try {
            Image img = ImageIO.read(getClass().getResource("/arrow_right.png"));
            buttonNext.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            Log.put(false, Level.INFO, "Błąd podczas otwierania pliku zasobów: obrazek strzałki w prawo (arrow_right.png)", this.getClass().getName());
        }

        try {
            Image img = ImageIO.read(getClass().getResource("/arrow_left.png"));
            buttonPrev.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            Log.put(false, Level.INFO, "Błąd podczas otwierania pliku zasobów: obrazek strzałki w lewo (arrow_left.png)", this.getClass().getName());
        }

        if (!addFile) buttonSubmitPanel.add(buttonPrev);
        buttonSubmitPanel.add(buttonSubmit);
        if (!addFile) buttonSubmitPanel.add(buttonNext);

        mainPanel.add(buttonSubmitPanel, BorderLayout.SOUTH);
        mainPanel.add(mainFieldsPanel, BorderLayout.CENTER);
    }

    public void showDialog(Boolean s) {
        this.setVisible(s);
    }
}
