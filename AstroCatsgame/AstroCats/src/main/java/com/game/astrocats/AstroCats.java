package com.game.astrocats;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import entity.Cat3;
import entity.PlayerCat1;
import entity.ScientistCat;


public class AstroCats extends Canvas implements Runnable, MouseListener {

    public static final long serialVersionUID = 1L;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 1024;
    public static final int tileSize = 128;
    public static final String TITLE = "AstroCats";
    public static final int FPS = 60;
   
    public static JFrame frame;
    public static Thread astrocatsThread;

    KeyHandler keyH = new KeyHandler();
    PlayerCat1 player1 = new PlayerCat1(this, keyH);

    private boolean gameStarted = false;
    private BufferedImage spaceshipInterior;
    
    
    private int scrollX = 0;
    private int scrollY = 0;
    private static final int MAP_WIDTH = 2000;  
    private static final int MAP_HEIGHT = 1024; 

    private ScientistCat scientist;
    private Cat3 cat3;
    private boolean showFinalProblem = false; 

    public AstroCats() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
  
        loadBackground();
        showMenuScreen();

        scientist = new ScientistCat(this, 
        1450, 
        PlayerCat1.BOUND_Y + 50   
        );

  
        addMouseListener(this);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocus();
        frame.addKeyListener(keyH);
        frame.setFocusable(true);
    }

    private void loadBackground() {
        try {
            spaceshipInterior = ImageIO.read(getClass().getResource("/images/spaceship_interior.png"));
            System.out.println("background loaded");
        } catch (IOException e) {
            System.err.println("ERROR: not able to load background image");
            System.err.println("Use a background cyan");
            e.printStackTrace();
        }
    }

    private void showMenuScreen() {
        MenuScreen menu = new MenuScreen(() -> {
            frame.getContentPane().removeAll();
            frame.add(this, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            gameStarted = true;
            start();
        });
        frame.add(menu, BorderLayout.CENTER);
        frame.revalidate();
    }

    public void start() {
        astrocatsThread = new Thread(this, "graphic");
        astrocatsThread.start();
    }

    public void stop() {
        astrocatsThread = null;
    }

    public void run() {
        if (!gameStarted) return;

        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (astrocatsThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player1.update();
        
        calculateScrolling();
    }
    
    private void calculateScrolling() {
        int targetScrollX = player1.getX() - (WIDTH / 2) + (player1.getTileSize() / 2);
        int targetScrollY = player1.getY() - (HEIGHT / 2) + (player1.getTileSize() / 2);
        
        int minScrollX = player1.getBoundX();
        int maxScrollX = player1.getBoundX() + player1.getBoundWidth() - WIDTH;
        int minScrollY = player1.getBoundY();
        int maxScrollY = player1.getBoundY() + player1.getBoundHeight() - HEIGHT;

        scrollX = Math.max(minScrollX, Math.min(targetScrollX, maxScrollX));
        scrollY = Math.max(minScrollY, Math.min(targetScrollY, maxScrollY));
        
    
        if (player1.getBoundWidth() <= WIDTH) {
            scrollX = player1.getBoundX() + (player1.getBoundWidth() - WIDTH) / 2;
        }
        if (player1.getBoundHeight() <= HEIGHT) {
            scrollY = player1.getBoundY() + (player1.getBoundHeight() - HEIGHT) / 2;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

   
        if (showFinalProblem) {
         
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            
        
            g2.setColor(Color.WHITE);
            g2.drawString("Cargando el problema final...", WIDTH / 2 - 100, HEIGHT / 2);
            g2.dispose();
            return;
        }

    
        if (spaceshipInterior != null) {

            int srcX = 200;    
            int srcY = 300;      
            int srcWidth = 500; 
            int srcHeight = 500; 

         
            g2.drawImage(spaceshipInterior, 
                        0, 0, WIDTH, HEIGHT, 
                        srcX + scrollX, srcY + scrollY, 
                        srcX + scrollX + srcWidth, srcY + scrollY + srcHeight, 
                        null);
        } else {
            g2.setColor(Color.CYAN);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
        }

        g2.setColor(new Color(0, 0, 0, 100)); 
        int screenBoundX = player1.getBoundX() - scrollX;
        int screenBoundY = player1.getBoundY() - scrollY;
        g2.fillRect(screenBoundX, screenBoundY, player1.getBoundWidth(), player1.getBoundHeight());

        //drawGrid(g2);
        player1.draw(g2, scrollX, scrollY);


        int scientistScreenX = scientist.getX() - scrollX;
        if (scientistScreenX > -200 && scientistScreenX < WIDTH + 200) {
            scientist.draw(g2, scrollX, scrollY, tileSize);
        }
        
        g2.dispose();
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(new Color(255, 255, 255, 50));
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 8; row++) {
                g2.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!showFinalProblem) {
           
            int worldX = e.getX() + scrollX;
            int worldY = e.getY() + scrollY;
            
    
            if (scientist.containsPoint(worldX, worldY)) {
                showFinalProblemScreen();
            }
        }
    }

    private void showFinalProblemScreen() {
        frame.getContentPane().removeAll();

        FinalProblem finalProblem = new FinalProblem(() -> {
    
            frame.getContentPane().removeAll();
            frame.add(this, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        frame.add(finalProblem, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

    }


    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        AstroCats game = new AstroCats();
    }
}