package com.imagecompare.browser.gui.databasepane;


import com.imagecompare.browser.gui.databasepane.table.DatabaseTable;
import com.imagecompare.browser.gui.imagepane.ImagePanelEast;
import com.imagecompare.browser.gui.shared.OpenFileDialog;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.system.Log;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
    private JLabel fieldItemId, addedFileName;
    private JTextField fieldItemName, fieldItemParam1, fieldItemParam2, fieldItemParam3, fieldItemParam4, fieldItemParam5;
    private DatabaseTable tableOfRecords;
    private int selectedRow = 0;
    private ImageItem currentItem;
    private ImageItem[] tempItems;
    private int type;
    private String chosenFilePath = "";
    private ImagePanelEast imagePanelEast;
    private DatabasePanel databasePanel;


    public AddEditRecordDialog(JFrame panel, String name, int type, ImagePanelEast imagePanelEast, DatabasePanel databasePanel) {
        super(panel, name, true);
        this.type = type;
        this.imagePanelEast = imagePanelEast;
        this.databasePanel = databasePanel;
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // SIZE WHEN DIALOG IS ADD DIALOG
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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean allChangesSaved = true;
                for (int i = 0; i < tempItems.length; i++) {
                    if (tempItems[i].isChanged()) allChangesSaved = false;
                }
                if (!allChangesSaved) {
                    JOptionPane.showMessageDialog(null, "Niezapisane zmiany", "Czy zapisać zmiany?", JOptionPane.INFORMATION_MESSAGE);
                }
                dispose();
            }
        });
    }

    public void setData(DatabaseTable tableOfRecords) {
        this.tableOfRecords = tableOfRecords;
        currentItem = tableOfRecords.getSelectedItem();
        selectedRow = tableOfRecords.getSelectedRow();
        tempItems = new ImageItem[tableOfRecords.getRowCount()];

        for (int i = 0; i < tempItems.length; i++) {
            tempItems[i] = new ImageItem(
                    Integer.valueOf(tableOfRecords.getValueAt(i, 0).toString()),
                    tableOfRecords.getValueAt(i, 1).toString(),
                    tableOfRecords.getValueAt(i, 2).toString(),
                    tableOfRecords.getValueAt(i, 3).toString(),
                    tableOfRecords.getValueAt(i, 4).toString(),
                    tableOfRecords.getValueAt(i, 5).toString(),
                    tableOfRecords.getValueAt(i, 6).toString(),
                    tableOfRecords.getValueAt(i, 7).toString());
        }
    }

    public void showDialog(Boolean s) {
        if (type == EDIT) {
            fieldItemId.setText(String.valueOf(currentItem.getId()));
            fieldItemName.setText(currentItem.getName());
            fieldItemParam1.setText(currentItem.getParam1());
            fieldItemParam2.setText(currentItem.getParam2());
            fieldItemParam3.setText(currentItem.getParam3());
            fieldItemParam4.setText(currentItem.getParam4());
            fieldItemParam5.setText(currentItem.getParam5());
        }

        this.setVisible(s);
    }

    private void showAddRecordDialog() {
        showCommonContent(true);
        titleLabel.setText("Dodawanie rekordu:");
    }

    private void showEditRecordDialog() {
        setSize(400, 290);
        showCommonContent(false);
        titleLabel.setText("Edycja rekordu:");
    }

    private void showCommonContent(boolean addFile) {
        titleLabel = new JLabel();
        titleLabel.setFont(font);

        addedFileName = new JLabel("");

        northPanel = new JPanel(new BorderLayout());
        addButton = new JButton("Dodaj plik");
        addButton.addActionListener((ActionEvent e) -> {
            OpenFileDialog fc = new OpenFileDialog(OpenFileDialog.IMAGE_FILE);
            int returnVal = fc.showDialog(this, "Otwórz");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                chosenFilePath = selectedFile.getAbsolutePath();
                addedFileName.setText(selectedFile.getName());
            }
        });

        northPanel.add(titleLabel, BorderLayout.NORTH);
        if (addFile) {
            northPanel.add(addButton, BorderLayout.WEST);
            northPanel.add(addedFileName, BorderLayout.CENTER);
        }

        northPanel.setPreferredSize(new Dimension(250, 50));
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
            idNameLabel.setText(ImageItem.PARAM_ID_TITLE);
        }

        mainFieldsPanel = new JPanel(new GridLayout(7, 4));
        mainFieldsPanel.add(idNameLabel);
        mainFieldsPanel.add(fieldItemId);
        mainFieldsPanel.add(new JLabel(ImageItem.PARAM_NAME_TITLE));
        mainFieldsPanel.add(fieldItemName);
        mainFieldsPanel.add(new JLabel(ImageItem.PARAM_PARAM1_TITLE));
        mainFieldsPanel.add(fieldItemParam1);
        mainFieldsPanel.add(new JLabel(ImageItem.PARAM_PARAM2_TITLE));
        mainFieldsPanel.add(fieldItemParam2);
        mainFieldsPanel.add(new JLabel(ImageItem.PARAM_PARAM3_TITLE));
        mainFieldsPanel.add(fieldItemParam3);
        mainFieldsPanel.add(new JLabel(ImageItem.PARAM_PARAM4_TITLE));
        mainFieldsPanel.add(fieldItemParam4);
        mainFieldsPanel.add(new JLabel(ImageItem.PARAM_PARAM5_TITLE));
        mainFieldsPanel.add(fieldItemParam5);

        buttonSubmitPanel = new JPanel(new FlowLayout());

        buttonSubmit = new JButton("Zapisz");
        buttonSubmit.addActionListener((ActionEvent e) -> {
            if (type == ADD) {
                if (!fieldItemName.getText().equals("")
                        && !fieldItemParam1.getText().equals("")
                        && !fieldItemParam2.getText().equals("")
                        && !fieldItemParam3.getText().equals("")
                        && !fieldItemParam4.getText().equals("")
                        && !fieldItemParam5.getText().equals("")
                        && !chosenFilePath.equals("") ) {

                    SQLiteConnector.insertItemToDatabase(new ImageItem(
                            fieldItemName.getText(),
                            chosenFilePath,
                            fieldItemParam1.getText(),
                            fieldItemParam2.getText(),
                            fieldItemParam3.getText(),
                            fieldItemParam4.getText(),
                            fieldItemParam5.getText()
                    ));


                    //imagePanelEast.setFilteredData(tableOfRecords);
                    imagePanelEast.refresh(false);
                    databasePanel.refresh();
                    databasePanel.clearFilterInputs();

                    JOptionPane.showMessageDialog(this, "Pomyślnie dodano nowe dane do bazy!", "Sukces!", JOptionPane.INFORMATION_MESSAGE);
                    fieldItemName.setText("");
                    fieldItemParam1.setText("");
                    fieldItemParam2.setText("");
                    fieldItemParam3.setText("");
                    fieldItemParam4.setText("");
                    fieldItemParam5.setText("");
                    chosenFilePath = "";
                    addedFileName.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Proszę wypełnić wszystkie pola przez dodaniem danych do bazy!", "Nie wypełniono wszystkich pól!", JOptionPane.WARNING_MESSAGE);
                }
            }
            else {
                saveChanges();
            }
        });

        //buttonSubmit.setEnabled(false);
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

        buttonPrev.addActionListener((ActionEvent e) -> {
            checkForChanges();
            selectedRow--;
            if (selectedRow < 0) {
                selectedRow = tempItems.length-1;
            }
            //System.out.println("Previous: " +selectedRow);

            currentItem = tempItems[selectedRow];
            showCurrentData();
        });

        buttonNext.addActionListener((ActionEvent e) -> {
            checkForChanges();
            selectedRow++;
            if (selectedRow > tempItems.length-1) {
                selectedRow = 0;
            }
            //System.out.println("Next: " +selectedRow);

            currentItem = tempItems[selectedRow];
            showCurrentData();
        });

        mainPanel.add(buttonSubmitPanel, BorderLayout.SOUTH);
        mainPanel.add(mainFieldsPanel, BorderLayout.CENTER);
    }

    private void checkForChanges() {
        if (!fieldItemName.getText().equals(tempItems[selectedRow].getName())
                || !fieldItemParam1.getText().equals(tempItems[selectedRow].getParam1())
                || !fieldItemParam2.getText().equals(tempItems[selectedRow].getParam2())
                || !fieldItemParam3.getText().equals(tempItems[selectedRow].getParam3())
                || !fieldItemParam4.getText().equals(tempItems[selectedRow].getParam4())
                || !fieldItemParam5.getText().equals(tempItems[selectedRow].getParam5())
                ) {

            String[] options = {"Tak", "Nie"};
            int option = JOptionPane.showOptionDialog(this,
                    "Czy zapisać zmiany?",
                    "Zapisać zmiany?",
                    0,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    null);

            if (option == JOptionPane.YES_OPTION) {
                System.out.println("Zapisywanie zmian...");
                saveChanges();
            }
        }
    }

    private void saveChanges() {
        currentItem = new ImageItem(currentItem.getId(), fieldItemName.getText(), currentItem.getFilename(), fieldItemParam1.getText(), fieldItemParam2.getText(), fieldItemParam3.getText(), fieldItemParam4.getText(), fieldItemParam5.getText());
        tempItems[selectedRow] = currentItem;
        SQLiteConnector.updateItemInDatabase(currentItem);
        imagePanelEast.refresh(false);
        databasePanel.refresh();
        databasePanel.clearFilterInputs();
    }

    private void showCurrentData() {
        fieldItemId.setText(String.valueOf(currentItem.getId()));
        fieldItemName.setText(currentItem.getName());
        fieldItemParam1.setText(currentItem.getParam1());
        fieldItemParam2.setText(currentItem.getParam2());
        fieldItemParam3.setText(currentItem.getParam3());
        fieldItemParam4.setText(currentItem.getParam4());
        fieldItemParam5.setText(currentItem.getParam5());
    }
}
