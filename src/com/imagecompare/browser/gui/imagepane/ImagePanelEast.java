package com.imagecompare.browser.gui.imagepane;

import com.imagecompare.browser.gui.main.MainWindow;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ImagePanelEast extends JPanel {

    private JPanel mainPanel;
    private JList<String> imagesList;
    private String databaseFilename;
    private CentralImagePanel centralImagePanel;
    private JScrollPane imagePanelEastScrollPane;
    private ArrayList<ImageItem> imageItems;

    public ImagePanelEast(String databaseFilename, MainWindow mainWindow, CentralImagePanel centralImagePanel) {
        super(new BorderLayout());
        this.databaseFilename = databaseFilename;
        this.centralImagePanel = centralImagePanel;
        refresh(false);
        this.setPreferredSize(new Dimension(300, 700));
    }

    public void refresh(boolean filteredData) {
        this.removeAll();
        mainPanel = new JPanel(new BorderLayout());
        JPanel labelAndRefreshButtonPanel = new JPanel(new FlowLayout());
        labelAndRefreshButtonPanel.add(new JLabel("Lista obrazków:"));
        JButton refreshDataButton = new JButton("Odśwież");
        refreshDataButton.addActionListener((ActionEvent e) -> {
            refresh(true);
        });
        labelAndRefreshButtonPanel.add(refreshDataButton);


        mainPanel.add(labelAndRefreshButtonPanel, BorderLayout.NORTH);
        DefaultListModel<String> listModel = new DefaultListModel<>();


        if (!filteredData) {
            imageItems = new ArrayList<>();
            if (SQLiteConnector.connectToDatabase(this.databaseFilename)) {
                imageItems = SQLiteConnector.getResults();
            }
        }

        for (ImageItem imageItem : imageItems) {
            listModel.addElement(imageItem.getFilename());
        }

        imagesList = new JList<>(listModel);

        imagesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && centralImagePanel.isImageLoaded()) {
                //System.out.println(imagesList.getSelectedValue());
                centralImagePanel.loadImage(centralImagePanel.getImageLeftName(), imagesList.getSelectedValue());
            }
        });
        mainPanel.add(imagesList);
        imagePanelEastScrollPane = new JScrollPane(mainPanel);
        this.add(imagePanelEastScrollPane);
    }

    public void setFilteredData(JTable tableOfRecords) {
        //System.out.println(tableOfRecords.getValueAt(1,1));
        imageItems = new ArrayList<>();
        for (int i = 0; i < tableOfRecords.getRowCount(); i++) {
            imageItems.add(new ImageItem(
                    tableOfRecords.getValueAt(i, 0),
                    tableOfRecords.getValueAt(i, 1),
                    tableOfRecords.getValueAt(i, 2),
                    tableOfRecords.getValueAt(i, 3),
                    tableOfRecords.getValueAt(i, 4),
                    tableOfRecords.getValueAt(i, 5),
                    tableOfRecords.getValueAt(i, 6),
                    tableOfRecords.getValueAt(i, 7)
                    ));
        }
        System.out.println(tableOfRecords.getRowCount());
        refresh(true);
    }
}
