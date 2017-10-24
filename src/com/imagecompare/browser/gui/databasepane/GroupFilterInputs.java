package com.imagecompare.browser.gui.databasepane;

import com.imagecompare.browser.gui.databasepane.table.TableRowFilter;
import com.imagecompare.browser.model.RecordsTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class GroupFilterInputs extends JPanel {

    private FilterInput[] filterInputs;

    public GroupFilterInputs(JFrame frame, RecordsTableModel recordsTableModel, TableRowSorter<TableModel> sorter) {
        super(new FlowLayout());

        FilterInput filterInputID = new FilterInput(frame, "Id", recordsTableModel, this);
        FilterInput filterInputName = new FilterInput(frame, "Nazwa", recordsTableModel, this);
        FilterInput filterInputFilename = new FilterInput(frame, "Plik", recordsTableModel, this);
        FilterInput filterInputParam1 = new FilterInput(frame, "Parametr 1", recordsTableModel, this);
        FilterInput filterInputParam2 = new FilterInput(frame, "Parametr 2", recordsTableModel, this);
        FilterInput filterInputParam3 = new FilterInput(frame, "Parametr 3", recordsTableModel, this);
        FilterInput filterInputParam4 = new FilterInput(frame, "Parametr 4", recordsTableModel, this);
        FilterInput filterInputParam5 = new FilterInput(frame, "Parametr 5", recordsTableModel, this);

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

    /*TableRowFilter tableRowFilter = new TableRowFilter(recordsTableModel, this.getText(), col);
            this.sorter.setRowFilter(tableRowFilter);*/


    public void updateFilters() {

    }
}
