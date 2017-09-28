package com.imagecompare.browser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


class OpenFileDialog extends JFileChooser {

    static final int IMAGE_FILE = 0;
    static final int DATABASE_FILE = 1;
    private FileNameExtensionFilter filter = null;

    OpenFileDialog(int type) {

        super(FileSystemView.getFileSystemView().getRoots()[0]);
        this.setAcceptAllFileFilterUsed(false);
        if (type == IMAGE_FILE) {
            filter = new FileNameExtensionFilter("Obrazy JPG, PNG i TIFF", "jpg", "png", "tiff");
        }
        if (type == DATABASE_FILE) {
            filter = new FileNameExtensionFilter("Pliki bazy danych *." +MainClass.DATABASE_FILE_EXTENSION, MainClass.DATABASE_FILE_EXTENSION);
        }

        this.setFileFilter(filter);
    }
}
