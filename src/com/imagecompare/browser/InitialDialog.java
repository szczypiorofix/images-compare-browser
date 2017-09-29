package com.imagecompare.browser;

import com.imagecompare.browser.system.Log;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;

class InitialDialog extends JDialog {

    private boolean isFileChosen = false;
    private String databaseFileName;


    InitialDialog(JFrame root, String title, boolean modal, String databaseFilename) {
        super(root, title, modal);
        this.databaseFileName = databaseFilename;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(root);
        setResizable(false);

        JPanel mainPanel = new JPanel(null);

        JLabel titleLabel = new JLabel("Ostatnio wybrana baza danych:");
        titleLabel.setBounds(50, 10, 280, 30);

        if (databaseFilename.equals("")) {
            //JTextField databaseFilenameInput = new JTextField();
            //databaseFilenameInput.setBounds(10, 40, 270, 30);
            //mainPanel.add(databaseFilenameInput);
            titleLabel.setText("Wybierz bazę danych:");
            titleLabel.setBounds(70, 10, 270, 30);
        }
        else {
            System.out.println(databaseFilename);
            String dbfn = "";
            int maxFileNameLength = 25;
            if (maxFileNameLength < databaseFilename.length()) dbfn += "...";
            for (int i = databaseFilename.length(); i > 0; i--) {
                if (i < maxFileNameLength) {
                    dbfn += databaseFilename.charAt(databaseFilename.length() - i);
                }
            }
            Log.put(false, Level.INFO, "Załadowany plik bazy danych: "+databaseFilename);
            JLabel databaseFilenameLabel = new JLabel(dbfn);
            databaseFilenameLabel.setBounds(50, 40, 320, 30);
            mainPanel.add(databaseFilenameLabel);
            JButton openThisDatabase = new JButton("Otwórz");
            openThisDatabase.setBounds(80, 80, 120, 30);
            openThisDatabase.addActionListener((ActionEvent e) -> {
                isFileChosen = true;
                this.setVisible(false);
            });
            mainPanel.add(openThisDatabase);
        }

        JButton confirmButton = new JButton("Utwórz nowy plik bazy");
        confirmButton.setBounds(60, 140, 160, 30);
        confirmButton.addActionListener((ActionEvent e) -> {

            UIManager.put("FileChooser.saveButtonText", "Zapisz");
            UIManager.put("FileChooser.saveInLabelText", "Zapisz w ...");

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Pliki bazy danych ." +MainClass.DATABASE_FILE_EXTENSION, MainClass.DATABASE_FILE_EXTENSION);
            fileChooser.setDialogTitle("Zapisz");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int userSelection = fileChooser.showSaveDialog(root);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String fileName = fileToSave.getAbsolutePath();
                if (!fileName.endsWith("." +MainClass.DATABASE_FILE_EXTENSION)) {
                    fileName += "." +MainClass.DATABASE_FILE_EXTENSION;
                }
                fileName = fileName.replace("\\", "/");
                System.out.println("Zapisz plik jako: " + fileName);
                isFileChosen = true;
                ConfigFileHandler configFileHandler = new ConfigFileHandler();
                configFileHandler.writeLastDatabase(fileName);
                this.databaseFileName = fileName;
                this.setVisible(false);
            }
        });
        mainPanel.add(confirmButton);

        JButton chooseDBButton = new JButton("Otwórz plik bazy danych");
        chooseDBButton.setBounds(55, 190, 175, 30);
        chooseDBButton.addActionListener((ActionEvent e) -> {
            OpenFileDialog fc = new OpenFileDialog(OpenFileDialog.DATABASE_FILE);
            int returnVal = fc.showDialog(this, "Otwórz");
        });

        mainPanel.add(titleLabel);
        mainPanel.add(chooseDBButton);

        this.add(mainPanel);
    }

    void showDialog(Boolean s) {
        this.setVisible(s);
    }

    Boolean isFileChosen() {
        return isFileChosen;
    }

    String getDatabaseFilename() {
        return databaseFileName;
    }
}
