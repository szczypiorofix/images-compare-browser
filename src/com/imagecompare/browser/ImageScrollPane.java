package com.imagecompare.browser;

import javax.swing.JScrollPane;
import java.awt.Component;


class ImageScrollPane extends JScrollPane {

    private Component view = null;

    ImageScrollPane(Component view) {
        super(view);
        this.view = view;
        this.getVerticalScrollBar().setUnitIncrement(16);
        //this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
}
