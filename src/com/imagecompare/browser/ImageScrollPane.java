package com.imagecompare.browser;

import javax.swing.*;
import java.awt.*;

class ImageScrollPane extends JScrollPane {

    private Component view = null;

    ImageScrollPane(Component view) {
        super(view);
        this.view = view;
        this.getVerticalScrollBar().setUnitIncrement(16);
    }
}
