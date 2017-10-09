package com.imagecompare.browser.gui;

import com.imagecompare.browser.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ImagePanelWest extends JPanel {

    private String chosenFileName = "";
    private String selectedDatabaseFilename = "";
    private MainWindow mainWindow;

    public ImagePanelWest(String selectedDatabaseFilename, MainWindow mainWindow) {
        super(new BorderLayout());
        this.mainWindow = mainWindow;
        this.selectedDatabaseFilename = selectedDatabaseFilename;
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel fileNamePanel = new JPanel(new BorderLayout());
        JLabel fileNameLabel = new JLabel("<brak pliku>");
        JButton chooseFileButton = new JButton("Dodaj zdjęcie");

        chooseFileButton.addActionListener((ActionEvent e) -> {
            OpenFileDialog fc = new OpenFileDialog(OpenFileDialog.IMAGE_FILE);
            int returnVal = fc.showDialog(this, "Otwórz");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                fileNameLabel.setText(selectedFile.getName());
                this.chosenFileName = selectedFile.getName();
            }
        });
        fileNamePanel.add(new JLabel("Wybrany plik:"), BorderLayout.NORTH);
        fileNamePanel.add(fileNameLabel, BorderLayout.CENTER);
        fileNamePanel.add(chooseFileButton, BorderLayout.SOUTH);

        JPanel inputMainPanel = new JPanel(new BorderLayout());

        inputMainPanel.add(fileNamePanel, BorderLayout.NORTH);

        JPanel inputGridPanel = new JPanel(new GridLayout(5, 4));
        inputGridPanel.setPreferredSize(new Dimension(180,130));
        inputGridPanel.setMinimumSize(new Dimension(180, 130));
        inputGridPanel.setMaximumSize(new Dimension(180, 130));
        inputGridPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JTextField textFieldName = new JTextField();
        JTextField textFieldParam1 = new JTextField();
        JTextField textFieldParam2 = new JTextField();
        JTextField textFieldParam3 = new JTextField();
        JTextField textFieldParam4 = new JTextField();

        inputGridPanel.add(new JLabel("Nazwa:"));
        inputGridPanel.add(textFieldName);
        inputGridPanel.add(new JLabel("Parametr 1:"));
        inputGridPanel.add(textFieldParam1);
        inputGridPanel.add(new JLabel("Parametr 2:"));
        inputGridPanel.add(textFieldParam2);
        inputGridPanel.add(new JLabel("Parametr 3:"));
        inputGridPanel.add(textFieldParam3);
        inputGridPanel.add(new JLabel("Parametr 4:"));
        inputGridPanel.add(textFieldParam4);

        inputMainPanel.add(inputGridPanel, BorderLayout.CENTER);

        JButton addToDatabase = new JButton("Dodaj");
        addToDatabase.addActionListener((ActionEvent e) -> {
            if ((!this.chosenFileName.equals(""))
                    && (!textFieldName.getText().equals(""))
                    && (!textFieldParam1.getText().equals(""))
                    && (!textFieldParam2.getText().equals(""))
                    && (!textFieldParam3.getText().equals(""))
                    && (!textFieldParam4.getText().equals(""))) {

                //SQLiteConnector connector = new SQLiteConnector(this.selectedDatabaseFilename);
                mainWindow.checkAndShowSqlConnection();
            }
            else {
                JOptionPane.showMessageDialog(null, "Proszę wypełnić wszystkie pola oraz dodać plik", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
            }
        });
        inputMainPanel.add(addToDatabase, BorderLayout.SOUTH);

        this.add(inputMainPanel, BorderLayout.NORTH);
    }
}