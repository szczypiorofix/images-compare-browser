package com.imagecompare.browser.gui.imagepane;

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
    private final int DIVIDER_SIZE = 2;
    private final Color dividerColor = new Color(255, 20, 30);
    private int selectorX = 300;
    private JScrollPane scrollPane = null;
    private boolean imageLoaded = false;
    private String imageLeftName = "", imageRightName = "";

    public CentralImagePanel(String imageName1, String imageName2) {
        super(new BorderLayout());

        try {
            this.imageLeft = ImageIO.read(getClass().getResource("/" +imageName1));
            this.imageRight = ImageIO.read(getClass().getResource("/" +imageName2));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.imageLeftName = imageName1;
        this.imageRightName = imageName2;

        imageLoaded = true;
        this.selectorX = this.imageLeft.getWidth()/2;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(this.imageLeft.getWidth(), (this.imageLeft.getHeight() + this.imageRight.getHeight())/2));
    }

    public CentralImagePanel() {
        super(new BorderLayout());

        this.selectorX = 1;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        //this.setPreferredSize(new Dimension(this.imageLeft.getWidth(), (this.imageLeft.getHeight() + this.imageRight.getHeight())/2));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageLoaded) {
            int dx = this.imageRight.getWidth() - DIVIDER_SIZE;
            if (this.selectorX + DIVIDER_SIZE < this.imageRight.getWidth()) {
                dx = this.selectorX;
            }
            g.drawImage(this.imageRight, 0, 0, this.imageRight.getWidth(), this.getHeight(),null);

            //if (this.getHeight() > this.imageLeft.getHeight()) {

            int scaledWidth = 0;
            int scaledHeight = 0;

            scaledHeight = this.getHeight();
            scaledWidth = this.getWidth();

            g.drawImage(cropImage(getScaledImage(this.imageLeft, scaledWidth, scaledHeight),
                    new Rectangle(dx + DIVIDER_SIZE, this.getHeight())), 0, 0, dx + DIVIDER_SIZE, this.getHeight(),this);
            //}

            g.setColor(dividerColor);
            g.fillRect(dx, 0, DIVIDER_SIZE, this.getHeight());
        }
    }

    private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public void loadImage(String imageLeftName, String imageRightName) {
        try {
            this.imageLeft = ImageIO.read(new File(imageLeftName));
        } catch (IOException e) {
            e.printStackTrace();
            Log.put(false, Level.WARNING, "Błąd otwierania pliku "+ imageLeftName +" : "+e.getMessage(), this.getClass().getName());
        }
        finally {
            this.imageLeftName = imageLeftName;
        }

        try {
            this.imageRight = ImageIO.read(new File(imageRightName));
        } catch (IOException e) {
            e.printStackTrace();
            Log.put(false, Level.WARNING, "Błąd otwierania pliku "+ imageRightName +" : "+e.getMessage(), this.getClass().getName());
        }
        finally {
            this.imageRightName = imageRightName;
        }

        int scaledImageLeftWidth = imageLeft.getWidth();
        int scaledImageLeftHeight = imageLeft.getHeight();
        int scaledImageRightWidth = imageRight.getWidth();
        int scaledImageRightHeight = imageRight.getHeight();


        if (scaledImageLeftHeight > scaledImageRightHeight) {
            imageLeft = resizeToBig(imageLeft, imageRight.getWidth(), imageRight.getHeight());
        }
        else {
            imageRight = resizeToBig(imageRight, imageLeft.getWidth(), imageLeft.getHeight());
        }


        //this.selectorX = this.imageLeft.getWidth()/2;
        imageLoaded = true;
        this.setPreferredSize(new Dimension(this.imageLeft.getWidth(), (this.imageLeft.getHeight() + this.imageRight.getHeight())/2));
        this.repaint();
        this.revalidate();
        this.scrollPane.revalidate();
    }




    private BufferedImage resizeToBig(BufferedImage originalImage, int biggerWidth, int biggerHeight) {
        int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage resizedImage = new BufferedImage(biggerWidth, biggerHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, this);
        g.dispose();
        return resizedImage;
    }




    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;
        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }
        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }
        return new Dimension(new_width, new_height);
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
            this.selectorX = location.x + this.scrollPane.getHorizontalScrollBar().getValue() + e.getX();
            if (this.selectorX <= 0) this.selectorX = 1;
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
            this.selectorX = location.x + this.scrollPane.getHorizontalScrollBar().getValue() + e.getX();
            if (this.selectorX <= 0) this.selectorX = 1;
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
