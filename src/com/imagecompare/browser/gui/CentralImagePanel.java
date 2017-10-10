package com.imagecompare.browser.gui;

import com.imagecompare.browser.system.Log;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class CentralImagePanel extends JPanel implements MouseMotionListener, MouseListener {

    private BufferedImage imageLeft = null, imageRight = null;
    private Point location;
    private final int DIVIDER = 2;
    private int x = 0;
    private JScrollPane scrollPane = null;
    private boolean imageLoaded = false;
    private String imageLeftName = "", imageRightName = "";

    public CentralImagePanel(String imageName1, String imageName2) {
        super(new BorderLayout());

        try {
            this.imageLeft = ImageIO.read(getClass().getResource("/" +imageName1));
            this.imageRight = ImageIO.read(getClass().getResource("/" +imageName2));
            this.imageLeftName = imageName1;
            this.imageRightName = imageName2;
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageLoaded = true;
        this.x = this.imageLeft.getWidth()/2;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(this.imageLeft.getWidth(), (this.imageLeft.getHeight() + this.imageRight.getHeight())/2));
    }

    public CentralImagePanel() {
        super(new BorderLayout());

        this.x = 1;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        //this.setPreferredSize(new Dimension(this.imageLeft.getWidth(), (this.imageLeft.getHeight() + this.imageRight.getHeight())/2));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageLoaded) {
            int dx = this.imageRight.getWidth() - DIVIDER;
            if (this.x + DIVIDER < this.imageRight.getWidth()) {
                dx = this.x;
            }
            g.drawImage(this.imageRight, 0, 0, this.imageRight.getWidth(), this.getHeight(),null);

            //if (this.getHeight() > this.imageLeft.getHeight() && this.getWidth() > this.imageLeft.getWidth()) {
                g.drawImage(cropImage(this.imageLeft, new Rectangle(dx + DIVIDER, this.getHeight())), 0, 0, dx + DIVIDER, this.getHeight(),null);
            //}

            g.setColor(new Color(255, 20, 30));
            g.fillRect(dx, 0, DIVIDER, this.getHeight());
        }
    }

/*    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }*/

    public void loadImage(String imageName1, String imageName2) {
        try {
            this.imageLeft = ImageIO.read(new File(imageName1));
        } catch (IOException e) {
            e.printStackTrace();
            Log.put(false, Level.WARNING, "Błąd otwierania pliku "+ imageName1 +" : "+e.getMessage(), this.getClass().getName());
        }
        finally {
            this.imageLeftName = imageName1;
        }

        try {
            this.imageRight = ImageIO.read(new File(imageName2));
        } catch (IOException e) {
            e.printStackTrace();
            Log.put(false, Level.WARNING, "Błąd otwierania pliku "+ imageName2 +" : "+e.getMessage(), this.getClass().getName());
        }
        finally {
            this.imageRightName = imageName2;
        }

        //System.out.println("Right: " +imageRightName);
        //System.out.println("Left: " +imageLeftName);

        //this.x = this.imageLeft.getWidth()/2;
        imageLoaded = true;
        this.setPreferredSize(new Dimension(this.imageLeft.getWidth(), (this.imageLeft.getHeight() + this.imageRight.getHeight())/2));
        this.repaint();
        this.revalidate();
        this.scrollPane.revalidate();
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        return src.getSubimage(0, 0, rect.width, rect.height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Component component = e.getComponent();
            location = component.getLocation(location);
            this.x = location.x + this.scrollPane.getHorizontalScrollBar().getValue() + e.getX();
            if (this.x <= 0) this.x = 1;
        }

        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e)) {
            Component component = e.getComponent();
            location = component.getLocation(location);
            this.x = location.x + this.scrollPane.getHorizontalScrollBar().getValue() + e.getX();
            if (this.x <= 0) this.x = 1;
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            Component component = e.getComponent();
            location = component.getLocation(location);
            int dx = this.scrollPane.getHorizontalScrollBar().getWidth() - (location.x + e.getX());
            System.out.println(this.scrollPane.getHorizontalScrollBar().getWidth() + location.x - e.getX());
            if (dx > 0 && dx < this.scrollPane.getHorizontalScrollBar().getWidth()) {
                this.scrollPane.getHorizontalScrollBar().setValue(dx);
            }
        }

        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public String getImageLeftName() {
        return imageLeftName;
    }

    public String getImageRightName() {
        return imageRightName;
    }

    public boolean isImageLoaded() {
        return imageLoaded;
    }
}
