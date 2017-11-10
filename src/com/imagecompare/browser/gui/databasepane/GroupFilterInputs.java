package com.imagecompare.browser.gui.databasepane;

import com.imagecompare.browser.gui.databasepane.table.DatabaseTable;
import com.imagecompare.browser.gui.databasepane.table.TableRowFilter;
import com.imagecompare.browser.gui.imagepane.ImagePanelEast;
import com.imagecompare.browser.model.ImageItem;
import com.imagecompare.browser.system.Log;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.logging.Level;

public class GroupFilterInputs extends JPanel {

    private FilterInput[] filterInputs;
    private TableRowSorter<TableModel> sorter;
    private ImagePanelEast imagePanelEast;
    private DatabaseTable tableOfRecords;
;
    public GroupFilterInputs(JFrame frame, TableRowSorter<TableModel> sorter, ImagePanelEast imagePanelEast, DatabaseTable tableOfRecords) {
        super(new GridLayout(1, 8));
        this.sorter = sorter;
        this.imagePanelEast = imagePanelEast;
        this.tableOfRecords = tableOfRecords;

        FilterInput filterInputID = new FilterInput(frame, ImageItem.PARAM_ID_TITLE,  this);
        FilterInput filterInputName = new FilterInput(frame, ImageItem.PARAM_NAME_TITLE, this);
        FilterInput filterInputFilename = new FilterInput(frame, ImageItem.PARAM_FILENAME_TITLE,  this);
        FilterInput filterInputParam1 = new FilterInput(frame, ImageItem.PARAM_PARAM1_TITLE,  this);
        FilterInput filterInputParam2 = new FilterInput(frame, ImageItem.PARAM_PARAM2_TITLE,  this);
        FilterInput filterInputParam3 = new FilterInput(frame, ImageItem.PARAM_PARAM3_TITLE,  this);
        FilterInput filterInputParam4 = new FilterInput(frame, ImageItem.PARAM_PARAM4_TITLE,  this);
        FilterInput filterInputParam5 = new FilterInput(frame, ImageItem.PARAM_PARAM5_TITLE,  this);

        add(filterInputID);
        add(filterInputName);
        add(filterInputFilename);
        add(filterInputParam1);
        add(filterInputParam2);
        add(filterInputParam3);
        add(filterInputParam4);
        add(filterInputParam5);

        filterInputs = new FilterInput[]{
                filterInputID,
                filterInputName,
                filterInputFilename,
                filterInputParam1,
                filterInputParam2,
                filterInputParam3,
                filterInputParam4,
                filterInputParam5};
        setPreferredSize(new Dimension(400, 25));
    }

    public FilterInput[] getFilterInputs() {
        return filterInputs;
    }

    public void setSorter(TableRowSorter<TableModel> sorter) {
        this.sorter = sorter;
    }

    public void setImagePanelEast(ImagePanelEast imagePanelEast) {
        this.imagePanelEast = imagePanelEast;
    }

    public void clearFilters() {
        for (int i = 0; i < filterInputs.length; i++) {
            filterInputs[i].setText("");
        }
        Log.put(false, Level.INFO, "Filtry wyczyszczone.", this.getClass().toString());
    }

    public void updateFilters() {
        TableRowFilter tableRowFilter = new TableRowFilter();
        tableRowFilter.setFilterInputs(this.filterInputs);
        sorter.setRowFilter(tableRowFilter);

        if (imagePanelEast != null) {
            imagePanelEast.refresh(true);
            imagePanelEast.setFilteredData(tableOfRecords);
        }
    }
}
