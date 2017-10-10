package com.imagecompare.browser.gui;

import com.imagecompare.browser.MainWindow;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
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
        mainPanel = new JPanel(new BorderLayout());
        refresh();
        this.add(imagePanelEastScrollPane, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 700));
    }

    public void refresh() {
        // TO DO POPRAWKI !!!
        // TODO Poprawic !!!
        // POPRAWI !!!!!

        mainPanel.removeAll();
        mainPanel.add(new JLabel("Lista obrazków: "), BorderLayout.NORTH);
        DefaultListModel<String> listModel = new DefaultListModel<>();

        imageItems = new ArrayList<>();

        if (SQLiteConnector.connectToDatabase(this.databaseFilename)) {
            imageItems = SQLiteConnector.getResults();
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

        imagePanelEastScrollPane = new JScrollPane(mainPanel);
        mainPanel.add(imagesList);
    }
}
