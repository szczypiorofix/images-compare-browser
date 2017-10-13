package com.imagecompare.browser.gui.imagepane;

import com.imagecompare.browser.gui.shared.OpenFileDialog;
import com.imagecompare.browser.gui.main.MainWindow;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ImagePanelWest extends JPanel {

    private String chosenFileName = "";
    private String chosenFilePath = "";
    private String selectedDatabaseFilename = "";
    private JTextField textFieldName;
    private JTextField textFieldParam1;
    private JTextField textFieldParam2;
    private JTextField textFieldParam3;
    private JTextField textFieldParam4;
    private JTextField textFieldParam5;

    public ImagePanelWest(String selectedDatabaseFilename, MainWindow mainWindow, CentralImagePanel centralImagePanel) {
        super(new BorderLayout());
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
                this.chosenFilePath = selectedFile.getAbsolutePath();
                centralImagePanel.loadImage(this.chosenFilePath, this.chosenFilePath);
                textFieldName.setText("");
                textFieldParam1.setText("");
                textFieldParam2.setText("");
                textFieldParam3.setText("");
                textFieldParam4.setText("");
                textFieldParam5.setText("");
            }
        });
        fileNamePanel.add(new JLabel("Wybrany plik: "), BorderLayout.NORTH);
        fileNamePanel.add(fileNameLabel, BorderLayout.CENTER);
        fileNamePanel.add(chooseFileButton, BorderLayout.SOUTH);

        JPanel inputMainPanel = new JPanel(new BorderLayout());

        inputMainPanel.add(fileNamePanel, BorderLayout.NORTH);

        JPanel inputGridPanel = new JPanel(new GridLayout(6, 4));
        inputGridPanel.setPreferredSize(new Dimension(180,130));
        inputGridPanel.setMinimumSize(new Dimension(180, 130));
        inputGridPanel.setMaximumSize(new Dimension(180, 130));
        inputGridPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        textFieldName = new JTextField();
        textFieldParam1 = new JTextField();
        textFieldParam2 = new JTextField();
        textFieldParam3 = new JTextField();
        textFieldParam4 = new JTextField();
        textFieldParam5 = new JTextField();

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
        inputGridPanel.add(new JLabel("Parametr 5:"));
        inputGridPanel.add(textFieldParam5);

        inputMainPanel.add(inputGridPanel, BorderLayout.CENTER);

        JButton addToDatabaseButton = new JButton("Dodaj");
        addToDatabaseButton.addActionListener((ActionEvent e) -> {
            if ((!this.chosenFileName.equals(""))
                    && (!textFieldName.getText().equals(""))
                    && (!textFieldParam1.getText().equals(""))
                    && (!textFieldParam2.getText().equals(""))
                    && (!textFieldParam3.getText().equals(""))
                    && (!textFieldParam4.getText().equals(""))
                    && (!textFieldParam5.getText().equals(""))) {

                SQLiteConnector.insertDataToDatabase(new ImageItem(textFieldName.getText(),
                        this.chosenFilePath,
                        this.textFieldParam1.getText(),
                        this.textFieldParam2.getText(),
                        this.textFieldParam3.getText(),
                        this.textFieldParam4.getText(),
                        this.textFieldParam5.getText()));
                mainWindow.checkAndShowSqlConnection();
                mainWindow.refreshDatabasePanel();
                JOptionPane.showMessageDialog(null, "Pomyślnie dodano nowe dane do bazy!", "Udało się!", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Proszę wypełnić wszystkie pola oraz dodać plik", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
            }
        });
        inputMainPanel.add(addToDatabaseButton, BorderLayout.SOUTH);

        this.add(inputMainPanel, BorderLayout.NORTH);
    }
}
