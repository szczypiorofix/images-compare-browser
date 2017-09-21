package com.imagecompare.browser;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


final class MainWindow extends JFrame {

    private JPanel mainPanel;
    private JSplitPane splitPane;
    private JMenuBar mainMenuBar;
    private JMenu menuFile, menuOptions;
    private JMenuItem menuFileExit, menuFileOpen;
    private JMenuItem menuOptionShowDatabase, menuOptionsAddNewImage;

    MainWindow() {
        super("Browser");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        setUIManager();
        createMainMenu();
        createMainPanel();

        SQLiteConnector sqliteConnector = new SQLiteConnector();
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
        JPanel sd1 = new JPanel(new BorderLayout());
        JLabel lb1 = new JLabel("Hello");
        sd1.add(lb1);

        JPanel sd2 = new JPanel(new BorderLayout());
        JLabel lb2 = new JLabel("Hello");
        sd2.add(lb2);

        mainPanel = new JPanel(new BorderLayout());
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                sd1, sd2);
        splitPane.setResizeWeight(0.5);
        //splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(6);

        Image myImage = null;
        try {
            myImage = ImageIO.read(getClass().getResource("/image1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myImage = myImage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        myImage = getScaledImage(myImage, 300, 400);
        ImageIcon myImageIcon = new ImageIcon(myImage);
        JLabel l1 = new JLabel(myImageIcon);
        l1.setSize(300, 300);
        sd2.add(l1);

        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(new JButton("Otwórz zdjęcie"), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent panel1 = new JPanel(new BorderLayout());
        tabbedPane.addTab("Przeglądarka zdjęć", null, panel1, "Lista zdjęć");

        JComponent panel2 = new JPanel(new BorderLayout());
        tabbedPane.addTab("Baza danych", null, panel2, "Baza danych zdjęć");

        panel1.add(mainPanel);
        this.add(tabbedPane);
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
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


        //menuFile.getAccessibleContext().setAccessibleDescription("This menu does nothing");


        menuOptions = new JMenu("Opcje");
        menuOptions.setMnemonic(KeyEvent.VK_O);

        menuOptionsAddNewImage = new JMenuItem("Dodaj zdjęcie do bazy danych");
        menuOptionShowDatabase = new JMenuItem("Zdjęcia w bazie danych");

        menuOptions.add(menuOptionsAddNewImage);
        menuOptions.add(menuOptionShowDatabase);

        mainMenuBar.add(menuFile);
        mainMenuBar.add(menuOptions);


        this.setJMenuBar(mainMenuBar);
    }

    void showWindow(Boolean s) {
        this.setVisible(s);
    }
}
