package com.imagecompare.browser.gui.main;

import com.imagecompare.browser.MainClass;
import com.imagecompare.browser.gui.databasepane.DatabasePanel;
import com.imagecompare.browser.gui.imagepane.CentralImagePanel;
import com.imagecompare.browser.gui.imagepane.ImagePanelEast;
import com.imagecompare.browser.gui.imagepane.ImagePanelWest;
import com.imagecompare.browser.gui.imagepane.ImageScrollPane;
import com.imagecompare.browser.gui.shared.OpenFileDialog;
import com.imagecompare.browser.system.ConfigFileHandler;
import com.imagecompare.browser.system.Log;
import com.imagecompare.browser.system.SQLiteConnector;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import java.util.logging.Level;


/**
 * #########################################################################
 * MainWindow class
 * #########################################################################
 *
 * This class extends java JFrame class. This is the main window of the application.
 *
 * #########################################################################
 */
public final class MainWindow extends JFrame {

    public static final String frameTitleName = "AdminImages";

    private InformationDialog informationDialog;
    private TopicsDialog topicsDialog;
    private MainTabbedPanel tabbedPane;
    private JPanel mainPanel;
    private JMenuBar mainMenuBar;
    private JMenu menuFile, menuOptions, menuHelp;
    private JMenuItem menuFileExit, menuFileOpen, menuHelpInformations, menuHelpTopics;
    private JMenuItem menuOptionShowDatabase, menuOptionsAddNewImage;
    private CentralImagePanel imageViewerPanel;
    private String databaseFileName;
    private DatabasePanel panelDatabase;
    private ImagePanelEast mainPanelEast;
    private ImagePanelWest mainPanelWest;
    private JSplitPane rightSplitPane;

    /**
     * Main constructor of the MainWindow class.
     *
     * @param width Integer - Width of the main window.
     * @param height Integer - Height of the main window.
     * @param maximized Boolean - true if the window has to be maximized (fill out all the screen).
     */
    public MainWindow(int width, int height, boolean maximized) {
        super(MainWindow.frameTitleName);
        Log.put(false, Level.INFO, "Created GUI on EDT? " + SwingUtilities.isEventDispatchThread(), this.getClass().getName());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        if (width == 0 || height == 0) {
            width = ConfigFileHandler.DEFAULT_CONFIG_FILE_WIDTH;
            height = ConfigFileHandler.DEFAULT_CONFIG_FILE_HEIGHT;
        }
        this.setSize(width, height);
        if (maximized) {
            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });
    }

    /**
     * setUIManager - method that sets polish localisation on FileChooser class.
     */
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

    /**
     * createMainPanel - group of method which creates main panel in the window: image panel, database panel and addidiotanl panel.
     */
    private void createMainPanel() {
        tabbedPane = new MainTabbedPanel();

        imageViewerPanel = new CentralImagePanel();
        mainPanel = new JPanel(new BorderLayout());
        ImageScrollPane scrollPanel = new ImageScrollPane(imageViewerPanel);
        imageViewerPanel.setScrollPane(scrollPanel);
        mainPanel.add(scrollPanel, BorderLayout.CENTER);

        panelDatabase = new DatabasePanel(this, this.databaseFileName);

        rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanelEast = new ImagePanelEast(databaseFileName, imageViewerPanel, panelDatabase);
        mainPanelWest = new ImagePanelWest(databaseFileName, this, imageViewerPanel);

        panelDatabase.setImagePanelEast(mainPanelEast);

        mainPanelEast.refresh(false);

        rightSplitPane.setDividerSize(6);
        rightSplitPane.setContinuousLayout(true);
        rightSplitPane.setDividerLocation(0.7);
        rightSplitPane.setLeftComponent(mainPanel);
        rightSplitPane.setRightComponent(mainPanelEast);

        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftSplitPane.setDividerSize(6);
        leftSplitPane.setContinuousLayout(true);
        leftSplitPane.setDividerLocation(0.3);
        leftSplitPane.setLeftComponent(mainPanelWest);
        leftSplitPane.setRightComponent(rightSplitPane);

        tabbedPane.addTab("Przeglądarka zdjęć", null, leftSplitPane, "Lista zdjęć");

        // UPDATE DATA
        mainPanelEast.setFilteredData(panelDatabase.getTableOfRecords());

        JScrollPane panelDatabaseScroll = new JScrollPane(panelDatabase);
        panelDatabaseScroll.getVerticalScrollBar().setUnitIncrement(12);
        tabbedPane.addTab("Baza danych", null, panelDatabaseScroll, "Baza danych zdjęć");
        tabbedPane.setColors();

        // IKONA
        java.net.URL imgUrl = MainClass.class.getResource("/conn_off.png");
        ImageIcon icon = new ImageIcon(imgUrl);
        tabbedPane.setIconAt(1, icon);

        this.add(tabbedPane);

        // Ustawianie wielkości podziału "na sztywno"
        // TODO Zmienić ustawianie podziału na podstawie wielkości okna
        imageViewerPanel.setPreferredSize(new Dimension(500, 400));
    }

    /**
     * createMainMenu - group of method which creates menu and menu items.
     */
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
        // WYJSCIE Z PROGRAMU
        menuFileExit.addActionListener(e -> exitApp());

        //menuFile.add(menuFileOpen);
        menuFile.add(menuFileExit);

        menuOptions = new JMenu("Opcje");
        menuOptions.setMnemonic(KeyEvent.VK_O);

        menuOptionsAddNewImage = new JMenuItem("Dodaj zdjęcie do bazy danych");
        menuOptionShowDatabase = new JMenuItem("Zdjęcia w bazie danych");

        menuOptions.add(menuOptionsAddNewImage);
        menuOptions.add(menuOptionShowDatabase);

        informationDialog = new InformationDialog(this, "Informacje", true);
        topicsDialog = new TopicsDialog(this, "Tematy pomocy", false);

        menuHelp = new JMenu("Pomoc");
        menuHelp.setMnemonic(KeyEvent.VK_C);

        menuHelpTopics = new JMenuItem("Tematy pomocy");
        menuHelpTopics.setMnemonic(KeyEvent.VK_T);
        menuHelpTopics.addActionListener((ActionEvent e) -> topicsDialog.setVisible(true));

        menuHelpInformations = new JMenuItem("Informacje");
        menuHelpInformations.setMnemonic(KeyEvent.VK_I);
        //menuHelpInformations.addActionListener((ActionEvent action) -> informationDialog.setVisible(true));

        menuHelp.add(menuHelpTopics);
        menuHelp.add(menuHelpInformations);

        mainMenuBar.add(menuFile);
        mainMenuBar.add(menuOptions);
        mainMenuBar.add(menuHelp);

        this.setJMenuBar(mainMenuBar);
    }

    /**
     * showWindow - this method shows
     * @param s Boolean - if true, the main window will be shown.
     * @param databaseFileName String - database full filename (with path)
     */
    public void showWindow(Boolean s, String databaseFileName) {
        this.databaseFileName = databaseFileName;
        setUIManager();
        createMainMenu();
        createMainPanel();
        this.setTitle(databaseFileName +" - " +MainWindow.frameTitleName);
        checkAndShowSqlConnection();
        this.setVisible(s);
    }

    /**
     * checkAndShowSqlConnection - checks if connection with SQLite Database is still opened.
     * It shows connection status as a icon in tabbed pane in the main window.
     */
    public void checkAndShowSqlConnection() {
        String imgIconName = "/conn_off.png";
        if (SQLiteConnector.isConnected()) imgIconName = "/conn_on.png";
        java.net.URL imgUrl = MainClass.class.getResource(imgIconName);
        ImageIcon icon = new ImageIcon(imgUrl);
        tabbedPane.setIconAt(1, icon);
    }

    /**
     * refreshDatabaseAndEastPanel - refresh the content of mainPanelEast and panelDatabase when needed.
     */
    public void refreshDatabaseAndEastPanel() {
        mainPanelEast.refresh(false);
        panelDatabase.refresh();
    }

    /**
     * exitApp - exit main application. Put some information on logs, writes some settings data to file and closes connection with SQLite Database.
     */
    public void exitApp() {
        Log.put(false, Level.INFO, "Zapis ustawień okna.", this.getClass().getName());
        ConfigFileHandler.saveWindowSizeToIniFile(this.getWidth(), this.getHeight(), (this.getExtendedState() == JFrame.MAXIMIZED_BOTH));
        Log.put(false, Level.INFO, "Zamykanie połączenia z bazą SQLite.", this.getClass().getName());
        SQLiteConnector.closeConnection();
        Log.put(false, Level.INFO, "Zamykanie aplikacji.", this.getClass().getName());
        System.exit(0);
    }

}
