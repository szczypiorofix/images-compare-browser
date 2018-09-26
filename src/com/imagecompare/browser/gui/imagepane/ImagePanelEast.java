package com.imagecompare.browser.gui.imagepane;

import com.imagecompare.browser.gui.databasepane.DatabasePanel;
import com.imagecompare.browser.gui.databasepane.table.DatabaseTable;
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
    private JScrollPane imagesListScrollPane;
    private ArrayList<ImageItem> imageItems;
    private DatabasePanel databasePanel;

    public ImagePanelEast(String databaseFilename, CentralImagePanel centralImagePanel, DatabasePanel databasePanel) {
        super(new BorderLayout());
        this.databaseFilename = databaseFilename;
        this.centralImagePanel = centralImagePanel;
        this.databasePanel = databasePanel;
        this.setPreferredSize(new Dimension(300, 700));
        this.setMinimumSize(new Dimension(100, 700));
        this.setMaximumSize(new Dimension(300, 700));
    }


    public void refresh(boolean filteredData) {
        this.removeAll();
        mainPanel = new JPanel(new BorderLayout());
        JPanel labelAndRefreshButtonPanel = new JPanel(new FlowLayout());
        labelAndRefreshButtonPanel.add(new JLabel("Lista obrazków:"));

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

        imagesList.setVisibleRowCount(10);
        imagesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel) {
                    ((JLabel) renderer).setText(imageItems.get(index).getName());
                }
                return renderer;
            }
        });

        imagesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && centralImagePanel.isImageLoaded()) {
                centralImagePanel.loadImage(centralImagePanel.getImageLeftName(), imagesList.getSelectedValue());
            }
        });

        imagesListScrollPane = new JScrollPane(imagesList);
        imagesListScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        mainPanel.add(imagesListScrollPane);
        this.add(mainPanel);
    }

    public void setFilteredData(DatabaseTable tableOfRecords) {
        imageItems = new ArrayList<>();
        for (int i = 0; i < tableOfRecords.getRowCount(); i++) {
            imageItems.add(new ImageItem(
                    Integer.valueOf(tableOfRecords.getValueAt(i, 0).toString()),
                    tableOfRecords.getValueAt(i, 1).toString(),
                    tableOfRecords.getValueAt(i, 2).toString(),
                    tableOfRecords.getValueAt(i, 3).toString(),
                    tableOfRecords.getValueAt(i, 4).toString(),
                    tableOfRecords.getValueAt(i, 5).toString(),
                    tableOfRecords.getValueAt(i, 6).toString(),
                    tableOfRecords.getValueAt(i, 7).toString()
                    ));
        }
        refresh(true);
    }
}
