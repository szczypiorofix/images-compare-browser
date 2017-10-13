package com.imagecompare.browser.gui.shared;

import com.imagecompare.browser.MainClass;
import com.imagecompare.browser.system.Log;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;


public class OpenFileDialog extends JFileChooser {

    public static final int IMAGE_FILE = 0;
    public static final int DATABASE_FILE = 1;

    public OpenFileDialog(int type) {

        super(FileSystemView.getFileSystemView().getRoots()[0]);
        this.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter;
        if (type == IMAGE_FILE) {
            filter = new FileNameExtensionFilter("Obrazy JPG, PNG i TIFF", "jpg", "png", "tiff");
        }
        else {
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
        //this.setCurrentDirectory(new File(System.getProperty("user.home")));
        try {
            this.setCurrentDirectory(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
        } catch (URISyntaxException urie) {
            Log.put(false, Level.WARNING, "Błąd pobierania aktualnej ścieżki folderów: "+urie.getMessage(), this.getClass().getName());
        }
        this.setFileFilter(filter);
    }
}
