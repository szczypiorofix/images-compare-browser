package com.imagecompare.browser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FilterInputStream;

class OpenFileDialog extends JFileChooser {

    static final int IMAGE_FILE = 0;
    static final int DATABASE_FILE = 1;
    private FileNameExtensionFilter filter = null;

    OpenFileDialog(int type) {

        super(FileSystemView.getFileSystemView().getRoots()[0]);

        if (type == IMAGE_FILE) {
            filter = new FileNameExtensionFilter("Obrazy JPG, PNG i TIFF", "jpg", "png", "tiff");
        }
        if (type == DATABASE_FILE) {
            filter = new FileNameExtensionFilter("Pliki bazy danych *.db", "db");
        }

        this.setFileFilter(filter);
    }
}
