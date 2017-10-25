package com.imagecompare.browser.gui.databasepane;

import com.imagecompare.browser.gui.databasepane.table.TableRowFilter;
import com.imagecompare.browser.model.RecordsTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class GroupFilterInputs extends JPanel {

    private FilterInput[] filterInputs;
    private TableRowSorter<TableModel> sorter;

    public GroupFilterInputs(JFrame frame, TableRowSorter<TableModel> sorter) {
        super(new FlowLayout());
        this.sorter = sorter;

        FilterInput filterInputID = new FilterInput(frame, "Id",  this);
        FilterInput filterInputName = new FilterInput(frame, "Nazwa", this);
        FilterInput filterInputFilename = new FilterInput(frame, "Plik",  this);
        FilterInput filterInputParam1 = new FilterInput(frame, "Parametr 1",  this);
        FilterInput filterInputParam2 = new FilterInput(frame, "Parametr 2",  this);
        FilterInput filterInputParam3 = new FilterInput(frame, "Parametr 3",  this);
        FilterInput filterInputParam4 = new FilterInput(frame, "Parametr 4",  this);
        FilterInput filterInputParam5 = new FilterInput(frame, "Parametr 5",  this);

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
    }

    public FilterInput[] getFilterInputs() {
        return filterInputs;
    }

    public void setSorter(TableRowSorter<TableModel> sorter) {
        this.sorter = sorter;
    }

    public void updateFilters() {
        TableRowFilter tableRowFilter = new TableRowFilter();
        tableRowFilter.setFilterInputs(this.filterInputs);
        sorter.setRowFilter(tableRowFilter);
    }
}