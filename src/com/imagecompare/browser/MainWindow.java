package com.imagecompare.browser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

final class MainWindow extends JFrame {

    private JPanel mainPanel;
    private BorderLayout borderLayout;
    private JSplitPane splitPane;
    private JMenuBar mainMenuBar;
    private JMenu menuFile, menuOptions;
    private JMenuItem menuFileExit, menuFileOpen;
    private JMenuItem menuOptionShowDatabase, menuOptionsAddNewImage;

    MainWindow() {
        super("Browser");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);

        createMainMenu();
        createMainPanel();
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
            myImage = ImageIO.read(getClass().getResource("/spaceship.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myImage = myImage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon myImageIcon = new ImageIcon(myImage);
        JLabel l1 = new JLabel(myImageIcon);
        sd2.add(l1);

        mainPanel.add(splitPane);
        this.add(mainPanel);
    }

    private void createMainMenu() {
        mainMenuBar = new JMenuBar();

        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);

        menuFileOpen = new JMenuItem("Open");
        menuFileOpen.setMnemonic(KeyEvent.VK_O);
        menuFileOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        menuFileOpen.setToolTipText("Open image file");
        menuFileOpen.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
            fc.setFileFilter(filter);
            int returnVal = fc.showDialog(this, "Attach");
        });

        menuFileExit = new JMenuItem("Exit");
        menuFileExit.setMnemonic(KeyEvent.VK_X);
        menuFileExit.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        menuFileExit.setToolTipText("Exit program");
        menuFileExit.addActionListener(e -> System.exit(0));

        menuFile.add(menuFileOpen);
        menuFile.add(menuFileExit);


        //menuFile.getAccessibleContext().setAccessibleDescription("This menu does nothing");


        menuOptions = new JMenu("Options");
        menuOptions.setMnemonic(KeyEvent.VK_O);

        menuOptionsAddNewImage = new JMenuItem("Add image to database");
        menuOptionShowDatabase = new JMenuItem("Images in database");

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
