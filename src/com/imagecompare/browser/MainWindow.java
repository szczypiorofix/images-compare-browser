package com.imagecompare.browser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


final class MainWindow extends JFrame {

    private JPanel mainPanel;
    private JMenuBar mainMenuBar;
    private JMenu menuFile, menuOptions, menuPomoc;
    private JMenuItem menuFileExit, menuFileOpen, menuPomocInformacje, menuPomocPomoc;
    private JMenuItem menuOptionShowDatabase, menuOptionsAddNewImage;
    private ImagePanel imageViewerPanel;
    private InformationDialog informationDialog;

    MainWindow() {
        super("Images Compare Browser");
        System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1100, 600);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        setUIManager();
        createMainMenu();
        createMainPanel();

        //SQLiteConnector sqliteConnector = new SQLiteConnector();
    }

    private void setUIManager() {
        UIManager.put("FileChooser.acceptAllFileFilterText", "*.*");
        UIManager.put("FileChooser.lookInLabelText", "Lokalizacja");
        UIManager.put("FileChooser.cancelButtonText", "Anuluj");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Anuluj");
        UIManager.put("FileChooser.openButtonText", "Otwórz");
        UIManager.put("FileChooser.openButtonText=Open", "Otwórz");
        UIManager.put("FileChooser.openButtonToolTipText", "Otwórz plik(i)");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Typ plików");
        UIManager.put("FileChooser.fileNameLabelText", "Plik(i)");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
        UIManager.put("FileChooser.listViewButtonAccessibleName", "Lista");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegóły");
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", "Szczegóły");
        UIManager.put("FileChooser.upFolderToolTipText", "W górę");
        UIManager.put("FileChooser.upFolderAccessibleName", "W górę");
        UIManager.put("FileChooser.homeFolderToolTipText", "Lokalizacja domowa");
        UIManager.put("FileChooser.homeFolderAccessibleName", "Lokalizacja domowa"); UIManager.put("FileChooser.fileNameHeaderText", "Home");
        UIManager.put("FileChooser.fileSizeHeaderText", "Rozmiar");
        UIManager.put("FileChooser.fileTypeHeaderText", "Typ");
        UIManager.put("FileChooser.fileDateHeaderText", "Data");
        UIManager.put("FileChooser.fileAttrHeaderText", "Atrybuty");
        UIManager.put("FileChooser.openDialogTitleText","Adicionar Fotos");
        UIManager.put("FileChooser.readOnly", Boolean.TRUE);
    }

    private void createMainPanel() {

        MainTabbedPanel tabbedPane = new MainTabbedPanel();

        JComponent panelImages = new JPanel(new BorderLayout());
        imageViewerPanel = new ImagePanel("image1.jpg", "image2.jpg");
        mainPanel = new JPanel(new BorderLayout());
        ImageScrollPane scrollPanel = new ImageScrollPane(imageViewerPanel);
        imageViewerPanel.setScrollPane(scrollPanel);
        mainPanel.add(scrollPanel);
        panelImages.add(mainPanel);
        tabbedPane.addTab("Przeglądarka zdjęć", null, panelImages, "Lista zdjęć");


        DatabasePanel panelDatabase = new DatabasePanel(this);
        JScrollPane panelDatabaseScroll = new JScrollPane(panelDatabase);
        panelDatabaseScroll.getVerticalScrollBar().setUnitIncrement(12);
        tabbedPane.addTab("Baza danych", null, panelDatabaseScroll, "Baza danych zdjęć");

        tabbedPane.setColors();


        this.add(tabbedPane);
    }

    private void createMainMenu() {
        mainMenuBar = new JMenuBar();

        menuFile = new JMenu("Plik");
        menuFile.setMnemonic(KeyEvent.VK_P);

        menuFileOpen = new JMenuItem("Otwórz");
        menuFileOpen.setMnemonic(KeyEvent.VK_O);
        menuFileOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        menuFileOpen.setToolTipText("Otwórz plik ze zdjęciem");
        menuFileOpen.addActionListener(e -> {
            OpenFileDialog fc = new OpenFileDialog();
            int returnVal = fc.showDialog(this, "Otwórz");
        });

        menuFileExit = new JMenuItem("Wyjście");
        menuFileExit.setMnemonic(KeyEvent.VK_W);
        menuFileExit.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        menuFileExit.setToolTipText("Wyjdź z programu");
        menuFileExit.addActionListener(e -> System.exit(0));

        menuFile.add(menuFileOpen);
        menuFile.add(menuFileExit);

        menuOptions = new JMenu("Opcje");
        menuOptions.setMnemonic(KeyEvent.VK_O);

        menuOptionsAddNewImage = new JMenuItem("Dodaj zdjęcie do bazy danych");
        menuOptionShowDatabase = new JMenuItem("Zdjęcia w bazie danych");

        menuOptions.add(menuOptionsAddNewImage);
        menuOptions.add(menuOptionShowDatabase);

        informationDialog = new InformationDialog(this, "Informacje", true);

        menuPomoc = new JMenu("Pomoc");
        menuPomoc.setMnemonic(KeyEvent.VK_C);

        menuPomocPomoc = new JMenuItem("Tematy pomocy");
        menuPomocPomoc.setMnemonic(KeyEvent.VK_T);
        menuPomocInformacje = new JMenuItem("Informacje");
        menuPomocInformacje.setMnemonic(KeyEvent.VK_I);
        menuPomocInformacje.addActionListener((ActionEvent action) -> {
            informationDialog.setVisible(true);
        });

        menuPomoc.add(menuPomocPomoc);
        menuPomoc.add(menuPomocInformacje);

        mainMenuBar.add(menuFile);
        mainMenuBar.add(menuOptions);
        mainMenuBar.add(menuPomoc);

        this.setJMenuBar(mainMenuBar);
    }

    void showWindow(Boolean s) {
        this.setVisible(s);
    }

}
