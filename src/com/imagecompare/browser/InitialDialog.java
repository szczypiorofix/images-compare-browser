package com.imagecompare.browser;

import com.sun.deploy.util.DialogListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

class InitialDialog extends JDialog {

    private boolean isFileChoosen = false;

    InitialDialog(JFrame root, String title, boolean modal, String databaseFilename) {
        super(root, title, modal);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(root);
        setResizable(false);

        JPanel mainPanel = new JPanel(null);

        JLabel titleLabel = new JLabel("Ostatnio wybrana baza danych:");
        titleLabel.setBounds(50, 10, 280, 30);

        if (databaseFilename.equals("")) {
            JTextField databaseFilenameInput = new JTextField();
            databaseFilenameInput.setBounds(10, 40, 270, 30);
            mainPanel.add(databaseFilenameInput);
            titleLabel.setText("Proszę wpisać nazwę dla nowej bazy danych:");
            titleLabel.setBounds(10, 10, 270, 30);

            JButton confirmButton = new JButton("Utwórz plik");
            confirmButton.setBounds(80, 80, 120, 30);
            confirmButton.addActionListener((ActionEvent e) -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Zapisz plik jako...");

                int userSelection = fileChooser.showSaveDialog(root);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    System.out.println("Zapisz plik jako: " + fileToSave.getAbsolutePath());
                    isFileChoosen = true;
                    this.setVisible(false);
                }
            });
            mainPanel.add(confirmButton);
        }
        else {
            JLabel databaseFilenameLabel = new JLabel(databaseFilename);
            databaseFilenameLabel.setBounds(100, 40, 150, 30);
            mainPanel.add(databaseFilenameLabel);
            JButton openThisDatabase = new JButton("Otwórz");
            openThisDatabase.setBounds(80, 80, 120, 30);
            openThisDatabase.addActionListener((ActionEvent e) -> {
                isFileChoosen = true;
                this.setVisible(false);
            });
            mainPanel.add(openThisDatabase);
        }

        JLabel orLabel = new JLabel("lub");
        orLabel.setBounds(130, 120, 30, 30);

        JButton chooseDBButton = new JButton("Wybierz inny plik bazy danych");
        chooseDBButton.setBounds(40, 170, 210, 30);
        chooseDBButton.addActionListener((ActionEvent e) -> {
            OpenFileDialog fc = new OpenFileDialog(OpenFileDialog.DATABASE_FILE);
            int returnVal = fc.showDialog(this, "Otwórz");
        });

        mainPanel.add(titleLabel);
        mainPanel.add(chooseDBButton);
        mainPanel.add(orLabel);

        this.add(mainPanel);
    }

    void showDialog(Boolean s) {
        this.setVisible(s);
    }

    Boolean isFileChoosen() {
        return isFileChoosen;
    }
}
