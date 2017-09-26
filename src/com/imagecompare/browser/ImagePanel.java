package com.imagecompare.browser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel implements MouseMotionListener, MouseListener {

    private BufferedImage image1 = null, image2 = null;
    private Point location;
    private final int DIVIDER = 3;
    private int x = 0;
    private JScrollPane scrollPane = null;

    ImagePanel(String imageName1, String imageName2) {
        super(new FlowLayout());

        try {
            this.image1 = ImageIO.read(getClass().getResource("/" +imageName1));
            this.image2 = ImageIO.read(getClass().getResource("/" +imageName2));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.x = this.image1.getWidth()/2;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(this.image1.getWidth(), (this.image1.getHeight() + this.image2.getHeight())/2));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int dx = this.image2.getWidth() - DIVIDER;
        if (this.x + DIVIDER < this.image2.getWidth()) {
            dx = this.x;
        }
        g.drawImage(this.image2, 0, 0, this.image2.getWidth(), this.getHeight(),null);
        g.drawImage(cropImage(this.image1, new Rectangle(dx + DIVIDER, this.getHeight())), 0, 0, dx + DIVIDER, this.getHeight(),null);
        g.setColor(new Color(255, 20, 30));
        g.fillRect(dx, 0, DIVIDER, this.getHeight());
    }

/*    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }*/

    void setScrollPane(JScrollPane scrollPane) {
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
        Component component = e.getComponent();
        location = component.getLocation(location);
        this.x = location.x + this.scrollPane.getHorizontalScrollBar().getValue() + e.getX();
        if (this.x <= 0) this.x = 1;
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Component component = e.getComponent();
        location = component.getLocation(location);
        this.x = location.x + this.scrollPane.getHorizontalScrollBar().getValue() + e.getX();
        if (this.x <= 0) this.x = 1;
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
