package com.imagecompare.browser.gui.main;

import com.imagecompare.browser.MainClass;
import com.imagecompare.browser.gui.shared.OpenFileDialog;
import com.imagecompare.browser.system.ConfigFileHandler;
import com.imagecompare.browser.system.Log;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class InitialDialog extends JDialog {

    private boolean isFileChosen = false;
    private String databaseFilename;


    public InitialDialog(JFrame root, String title, boolean modal, String databaseFilename) {
        super(root, title, modal);
        this.databaseFilename = databaseFilename;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(root);
        setResizable(false);

        JPanel mainPanel = new JPanel(null);

        JLabel titleLabel = new JLabel("Ostatnio wybrana baza danych:");
        titleLabel.setBounds(50, 10, 280, 30);

        if (this.databaseFilename.equals("")) {
            titleLabel.setText("Wybierz bazę danych:");
            titleLabel.setBounds(70, 10, 270, 30);
        }
        else {
            StringBuilder dbfn = new StringBuilder();
            int maxFileNameLength = 25;
            if (maxFileNameLength < databaseFilename.length()) dbfn.append("...");
            for (int i = databaseFilename.length(); i > 0; i--) {
                if (i < maxFileNameLength) {
                    dbfn.append(databaseFilename.charAt(databaseFilename.length() - i));
                }
            }
            Log.put(false, Level.INFO, "Ostatnio wybierany plik bazy danych: "+databaseFilename, this.getClass().getName());
            JLabel databaseFilenameLabel = new JLabel(dbfn.toString());
            databaseFilenameLabel.setBounds(50, 40, 320, 30);
            mainPanel.add(databaseFilenameLabel);
            JButton openThisDatabase = new JButton("Otwórz");
            openThisDatabase.setBounds(80, 80, 120, 30);
            openThisDatabase.addActionListener((ActionEvent e) -> {
                File dbFile = new File(databaseFilename);
                if (dbFile.exists() && !dbFile.isDirectory()) {
                    isFileChosen = true;
                    Log.put(false, Level.INFO, "Załadowany plik bazy danych: "+databaseFilename, this.getClass().getName());
                    this.setVisible(false);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Brak pliku bazy danych!: "+databaseFilename, "Brak pliku bazy danych!", JOptionPane.ERROR_MESSAGE);
                }
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
                    "Pliki bazy danych " +
                            "*." + MainClass.DATABASE_FILE_EXTENSION_DB +
                            ", *." +MainClass.DATABASE_FILE_EXTENSION_SQLITE +
                            ", *." +MainClass.DATABASE_FILE_EXTENSION_SQLITE3 +
                            ", *." +MainClass.DATABASE_FILE_EXTENSION_DB3,
                    MainClass.DATABASE_FILE_EXTENSION_DB,
                    MainClass.DATABASE_FILE_EXTENSION_SQLITE,
                    MainClass.DATABASE_FILE_EXTENSION_SQLITE3,
                    MainClass.DATABASE_FILE_EXTENSION_DB3);

            fileChooser.setDialogTitle("Zapisz");
            fileChooser.setFileFilter(filter);
            //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            try {
                fileChooser.setCurrentDirectory(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
            } catch (URISyntaxException urie) {
                urie.printStackTrace();
                Log.put(false, Level.WARNING, "Błąd pobierania aktualnej ścieżki folderów: "+urie.getMessage(), this.getClass().getName());
            }
            fileChooser.setAcceptAllFileFilterUsed(false);
            int userSelection = fileChooser.showSaveDialog(root);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String fileName = fileToSave.getAbsolutePath();

                // DOMYSLNE ROZSZERZENIE *.db przy braku pozostałych rozszerzeń
                if (!fileName.endsWith("." +MainClass.DATABASE_FILE_EXTENSION_DB)
                        && !fileName.endsWith("." +MainClass.DATABASE_FILE_EXTENSION_SQLITE)
                        && !fileName.endsWith("." +MainClass.DATABASE_FILE_EXTENSION_SQLITE3)
                        && !fileName.endsWith("." +MainClass.DATABASE_FILE_EXTENSION_DB3)) {
                    fileName += "." +MainClass.DATABASE_FILE_EXTENSION_DB;
                }
                fileName = fileName.replace("\\", "/");
                isFileChosen = true;

                ConfigFileHandler.writeLastDatabase(fileName);
                this.databaseFilename = fileName;

                if (!SQLiteConnector.createNewDatabaseFile(this.databaseFilename)) {
                    Log.put(false, Level.WARNING, "Błąd tworzenia pliku bazy danych : "+databaseFilename, this.getClass().getName());
                    JOptionPane.showMessageDialog(this, "Błąd tworzenia pliku bazy danych", "Błąd tworzenia pliku bazy danych", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                this.setVisible(false);
            }
        });
        mainPanel.add(confirmButton);

        JButton chooseDBButton = new JButton("Otwórz plik bazy danych");
        chooseDBButton.setBounds(55, 190, 175, 30);
        chooseDBButton.addActionListener((ActionEvent e) -> {
            OpenFileDialog fc = new OpenFileDialog(OpenFileDialog.DATABASE_FILE);
            int returnVal = fc.showDialog(this, "Otwórz");
            if (returnVal == 0) {
                String fileName = fc.getSelectedFile().getAbsolutePath();
                fileName = fileName.replace("\\", "/");
                ConfigFileHandler.writeLastDatabase(fileName);

                this.databaseFilename = fileName;
                this.isFileChosen = true;
                this.setVisible(false);
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(chooseDBButton);

        this.add(mainPanel);
    }

    public void showDialog(Boolean s) {
        this.setVisible(s);
    }

    public Boolean isFileChosen() {
        return isFileChosen;
    }

    public String getDatabaseFilename() {
        return databaseFilename;
    }
}
