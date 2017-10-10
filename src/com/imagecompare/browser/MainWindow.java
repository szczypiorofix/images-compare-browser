package com.imagecompare.browser;

import com.imagecompare.browser.gui.*;
import com.imagecompare.browser.system.Log;
import com.imagecompare.browser.system.SQLiteConnector;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;


import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;


public final class MainWindow extends JFrame implements WindowListener {

    public static final String frameTitleName = " Images Compare Browser";
    private JPanel mainPanel;
    private JMenuBar mainMenuBar;
    private JMenu menuFile, menuOptions, menuPomoc;
    private JMenuItem menuFileExit, menuFileOpen, menuPomocInformacje, menuPomocPomoc;
    private JMenuItem menuOptionShowDatabase, menuOptionsAddNewImage;
    private CentralImagePanel imageViewerPanel;
    public InformationDialog informationDialog;
    private String databaseFileName;
    public MainTabbedPanel tabbedPane;
    private DatabasePanel panelDatabase;
    private ImagePanelEast mainPanelEast;
    private  ImagePanelWest mainPanelWest;

    MainWindow() {
        super(MainWindow.frameTitleName);
        Log.put(false, Level.INFO, "Created GUI on EDT? " + SwingUtilities.isEventDispatchThread(), this.getClass().getName());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setSize(1100, 600);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
        this.addWindowListener(this);
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

        tabbedPane = new MainTabbedPanel();

        //imageViewerPanel = new CentralImagePanel("image1.jpg", "image2.jpg");
        imageViewerPanel = new CentralImagePanel();
        mainPanel = new JPanel(new BorderLayout());
        ImageScrollPane scrollPanel = new ImageScrollPane(imageViewerPanel);
        imageViewerPanel.setScrollPane(scrollPanel);
        mainPanel.add(scrollPanel, BorderLayout.CENTER);

        JPanel mainPanelNorth = new JPanel();
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanelEast = new ImagePanelEast(databaseFileName, this, imageViewerPanel);
        mainPanelWest = new ImagePanelWest(databaseFileName, this, imageViewerPanel);
        JPanel mainPanelSouth = new JPanel();

        //mainPanel.add(mainPanelNorth, BorderLayout.NORTH);
        //mainPanel.add(mainPanelWest, BorderLayout.WEST);
        //mainPanel.add(mainPanelEast, BorderLayout.EAST);
        //mainPanel.add(mainPanelSouth, BorderLayout.SOUTH);

        //JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        rightSplitPane.setDividerSize(6);
        rightSplitPane.setContinuousLayout(true);
        rightSplitPane.setDividerLocation(0.3);
        rightSplitPane.setLeftComponent(mainPanel);
        rightSplitPane.setRightComponent(mainPanelEast);

        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftSplitPane.setDividerSize(6);
        leftSplitPane.setContinuousLayout(true);
        leftSplitPane.setDividerLocation(0.3);
        leftSplitPane.setLeftComponent(mainPanelWest);
        leftSplitPane.setRightComponent(rightSplitPane);

        tabbedPane.addTab("Przeglądarka zdjęć", null, leftSplitPane, "Lista zdjęć");

        panelDatabase = new DatabasePanel(this, this.databaseFileName);

        JScrollPane panelDatabaseScroll = new JScrollPane(panelDatabase);
        panelDatabaseScroll.getVerticalScrollBar().setUnitIncrement(12);
        tabbedPane.addTab("Baza danych", null, panelDatabaseScroll, "Baza danych zdjęć");

        tabbedPane.setColors();

        // IKONA
        java.net.URL imgUrl = MainClass.class.getResource("/conn_off.png");
        ImageIcon icon = new ImageIcon(imgUrl);
        tabbedPane.setIconAt(1, icon);


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
            OpenFileDialog fc = new OpenFileDialog(OpenFileDialog.IMAGE_FILE);
            int returnVal = fc.showDialog(this, "Otwórz");
        });

        menuFileExit = new JMenuItem("Wyjście");
        menuFileExit.setMnemonic(KeyEvent.VK_W);
        menuFileExit.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        menuFileExit.setToolTipText("Wyjdź z programu");
        menuFileExit.addActionListener(e -> {
            Log.put(false, Level.INFO, "Zamykanie połączenia z bazą SQLite...", this.getClass().getName());
            SQLiteConnector.closeConnection();
            Log.put(false, Level.INFO, "Zamykanie aplikacji ...", this.getClass().getName());
            System.exit(0);
        });

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
        menuPomocInformacje.addActionListener((ActionEvent action) -> informationDialog.setVisible(true));

        menuPomoc.add(menuPomocPomoc);
        menuPomoc.add(menuPomocInformacje);

        mainMenuBar.add(menuFile);
        mainMenuBar.add(menuOptions);
        mainMenuBar.add(menuPomoc);

        this.setJMenuBar(mainMenuBar);
    }

    void showWindow(Boolean s, String databaseFileName) {
        this.databaseFileName = databaseFileName;
        setUIManager();
        createMainMenu();
        createMainPanel();
        this.setTitle(databaseFileName +" - " +MainWindow.frameTitleName);
        checkAndShowSqlConnection();
        this.setVisible(s);
    }

    // Zmiana ikony przy JTabbedPane - wskaźnik połączenia z bazą danych
    public void checkAndShowSqlConnection() {
        String imgIconName = "/conn_off.png";
        if (SQLiteConnector.isConnected()) imgIconName = "/conn_on.png";
        java.net.URL imgUrl = MainClass.class.getResource(imgIconName);
        ImageIcon icon = new ImageIcon(imgUrl);
        tabbedPane.setIconAt(1, icon);
    }

    public void refreshDatabasePanel() {
        mainPanelEast.refresh();
        panelDatabase.refresh();
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        Log.put(false, Level.INFO, "Zamykanie połączenia z bazą SQLite...", this.getClass().getName());
        SQLiteConnector.closeConnection();
        Log.put(false, Level.INFO, "Zamykanie aplikacji ...", this.getClass().getName());
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println(SQLiteConnector.getStatus());
    }

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

}
