package com.imagecompare.browser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

class OpenFileDialog extends JFileChooser {

    OpenFileDialog() {
        super();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        this.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.setFileFilter(filter);
    }
}
