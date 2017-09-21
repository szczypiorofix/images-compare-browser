package com.imagecompare.browser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private Image image = null;

    ImagePanel(String imageName) {
        super(new BorderLayout());

        try {
            this.image = ImageIO.read(getClass().getResource("/" +imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //this.image = getScaledImage(this.image, 300, 300);
        //System.out.println(this.getWidth());
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(image, 0, 0, 600, 300,null);
    }
}
