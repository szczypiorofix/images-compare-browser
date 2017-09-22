package com.imagecompare.browser;

import java.awt.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

//public class MainClass extends BasicGame {
public class MainClass {

    private MainWindow mainWindow;
    //private static AppGameContainer app;
    //private final static int SCREEN_WIDTH = 640;
    //private final static int SCREEN_HEIGHT = 480;

    private MainClass() {
        //super("Image Compare Browser");
         mainWindow = new MainWindow();
         mainWindow.showWindow(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(MainClass::new);

/*        try
        {
            app = new AppGameContainer(new MainClass());
            app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
            app.setUpdateOnlyWhenVisible(true);
            Display.setResizable(true);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }*/
    }
/*
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setShowFPS(false);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
*//*        if(Display.getWidth() != gameContainer.getWidth() || Display.getHeight() != gameContainer.getHeight()) {
            try {
                app.setDisplayMode(Display.getWidth(), Display.getHeight(), false);
                Display.setResizable(true);
                app.reinit();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }*//*
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }*/
}
