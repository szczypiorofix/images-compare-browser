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
    private MouseEvent pressed;
    private final int DIVIDER = 3;
    private int x = 0;
    private JScrollPane scrollPane = null;
    private int panelWidth = 0, panelHeight = 0;

    ImagePanel(String imageName1, String imageName2, int panelWidth, int panelHeight) {
        super(new FlowLayout());

        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;

        try {
            this.image1 = ImageIO.read(getClass().getResource("/" +imageName1));
            this.image2 = ImageIO.read(getClass().getResource("/" +imageName2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.setPreferredSize(new Dimension(300, 400));
        //this.image = getScaledImage(this.image, 300, 300);

        //this.add(new JLabel(new ImageIcon(this.image1)));

        this.x = 300;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(this.image1.getWidth(), (this.image1.getHeight() + this.image2.getHeight())/2));
    }

/*    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }*/

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image2, 0, 0, this.getWidth(), this.getHeight(),null);
        g.drawImage(cropImage(this.image1, new Rectangle(this.x + DIVIDER, this.getHeight())), 0, 0, (this.x + DIVIDER), this.getHeight(),null);
        g.setColor(new Color(255, 20, 30));
        g.fillRect(this.x, 0, DIVIDER, this.getHeight());
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
        this.x = location.x + e.getX();
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
        this.x = location.x + e.getX();
        if (this.x <= 0) this.x = 1;
        //System.out.println(e.getX());
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
