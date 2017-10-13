package com.imagecompare.browser.gui.imagepane;

import com.imagecompare.browser.gui.main.MainWindow;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.system.SQLiteConnector;

import javax.swing.*;
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
        refresh();
        this.setPreferredSize(new Dimension(300, 700));
    }

    public void refresh() {
        this.removeAll();
        mainPanel = new JPanel(new BorderLayout());
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
        mainPanel.add(imagesList);
        imagePanelEastScrollPane = new JScrollPane(mainPanel);
        this.add(imagePanelEastScrollPane);
    }
}
