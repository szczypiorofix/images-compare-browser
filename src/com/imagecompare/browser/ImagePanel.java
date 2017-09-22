package com.imagecompare.browser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel implements MouseMotionListener, MouseListener{

    private Image image = null;
    private Point location;
    private MouseEvent pressed;
    private int x, y;
    private JScrollPane scrollPane = null;

    ImagePanel(String imageName) {
        super(new FlowLayout());

        try {
            this.image = ImageIO.read(getClass().getResource("/" +imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.setPreferredSize(new Dimension(300, 400));
        //this.image = getScaledImage(this.image, 300, 300);
        //System.out.println(this.getWidth());

        //this.add(new JLabel(new ImageIcon(this.image)));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is my custom Panel!",10,20);
        g.setColor(Color.RED);
        g.fillRect(10, 100, 100, 200);
        g.setColor(Color.BLACK);
        g.drawRect(10, 100, 120, 200);
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e;
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

        int x = location.x - pressed.getX() + e.getX();
        int y = location.y - pressed.getY() + e.getY();
        this.scrollPane.getHorizontalScrollBar().setValue(-x);
        this.scrollPane.getVerticalScrollBar().setValue(-y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public int getScrollX() {
        return this.x;
    }

    public int getScrollY() {
        return this.y;
    }
}
