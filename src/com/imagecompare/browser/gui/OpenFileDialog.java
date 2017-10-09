package com.imagecompare.browser.gui;

import com.imagecompare.browser.MainClass;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class OpenFileDialog extends JFileChooser {

    public static final int IMAGE_FILE = 0;
    public static final int DATABASE_FILE = 1;
    private FileNameExtensionFilter filter = null;

    public OpenFileDialog(int type) {

        super(FileSystemView.getFileSystemView().getRoots()[0]);
        this.setAcceptAllFileFilterUsed(false);
        if (type == IMAGE_FILE) {
            filter = new FileNameExtensionFilter("Obrazy JPG, PNG i TIFF", "jpg", "png", "tiff");
        }
        if (type == DATABASE_FILE) {
            filter = new FileNameExtensionFilter("Pliki bazy danych " +
                    "*."+ MainClass.DATABASE_FILE_EXTENSION_DB +
                    ", *."+MainClass.DATABASE_FILE_EXTENSION_SQLITE +
                    ", *."+MainClass.DATABASE_FILE_EXTENSION_SQLITE3 +
                    ", *."+MainClass.DATABASE_FILE_EXTENSION_DB3,
                    MainClass.DATABASE_FILE_EXTENSION_DB,
                    MainClass.DATABASE_FILE_EXTENSION_SQLITE,
                    MainClass.DATABASE_FILE_EXTENSION_SQLITE3,
                    MainClass.DATABASE_FILE_EXTENSION_DB3);
        }

        this.setFileFilter(filter);
    }
}
