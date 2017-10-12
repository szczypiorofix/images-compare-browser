package com.imagecompare.browser.table;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class GroupableTableHeader extends JTableHeader {

    public GroupableTableHeader(TableColumnModel model) {
        super(model);
        //setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
        // setDefaultRenderer(new MultiLineHeaderRenderer());
    }
}
