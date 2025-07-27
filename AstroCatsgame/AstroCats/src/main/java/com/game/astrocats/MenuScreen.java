package com.game.astrocats;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

public class MenuScreen extends Canvas implements MouseListener {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;
    
    private BufferedImage backgroundImage;
    private BufferedImage introImage; // Nueva imagen de introducción
    private Rectangle startButton;
    private boolean isButtonHovered = false;
    private GameStarter gameStarter;
    private boolean showingIntro = false; // Indica si estamos mostrando la introducción
    private long introStartTime;
    private static final long INTRO_DURATION = 20000; // 20 segundos en milisegundos

    private Clip menuMusic; // for background music
    private boolean musicPlaying = false; // Para verificar si la música está sonando

    private Font technoFont;
    
    // class for start the game
    public interface GameStarter {
        void startGame();
    }

    public MenuScreen(GameStarter starter) {
        this.gameStarter = starter;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        
        // load background image
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/spaceship_background.png"));
            System.out.println("background image loaded");
        } catch (IOException e) {
            System.err.println("ERROR: Not able to load background image");
            e.printStackTrace();
        }
        
        // Load intro image
        try {
            introImage = ImageIO.read(getClass().getResource("/images/game_intro.png"));
            System.out.println("Intro image loaded");
        } catch (IOException e) {
            System.err.println("ERROR: Not able to load intro image");
            e.printStackTrace();
        }

        loadTechnoFontr();

        // load and play background music
        playMenuMusic();
        
        // Button position and size
        int buttonWidth = 300;
        int buttonHeight = 80;
        int buttonX = (WIDTH - buttonWidth) / 2;
        int buttonY = HEIGHT - 300;
        startButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        
        addMouseListener(this);
        setFocusable(true);
        requestFocus();
    }

    private void playMenuMusic() {
        try {
            URL soundURL = getClass().getResource("/music/deep-space-barrier-121195.wav");
            
            if (soundURL == null) {
                System.err.println("ERROR: not able to load menu music");
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            menuMusic = AudioSystem.getClip();
            menuMusic.open(audioStream);
            
            // Reproducir en bucle continuo
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
            menuMusic.start(); // Asegurar que la música comience a sonar
            musicPlaying = true;
            System.out.println("Music loaded and playing");
        } catch (UnsupportedAudioFileException e) {
            System.err.println("ERROR: problem with audio file");
            System.err.println("if you are using a mac, try to use a .wav file");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("ERROR: not there : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR: not there : " + e.getMessage());
        }
    }
    
    public void stopMusic() {
        if (menuMusic != null && menuMusic.isRunning()) {
            menuMusic.stop();
            menuMusic.close();
            musicPlaying = false;
            System.out.println("music stopped");
        }
    } 
    
    public Clip getMenuMusic() {
        return menuMusic;
    }
    
    public boolean isMusicPlaying() {
        return musicPlaying;
    }

    private void loadTechnoFontr() {
        try {
            technoFont = Font.createFont(Font.TRUETYPE_FONT, 
                            getClass().getResourceAsStream("/fonts/GenericTechno.otf"))
                            .deriveFont(80f);
            System.out.println("Font loaded");
        } catch (Exception e) {
            System.err.println("ERROR: Not able to load font");
            System.err.println("Use default font");
            e.printStackTrace();
            
            technoFont = new Font("Arial", Font.BOLD, 80);
        }
    }


    @Override
    public void paint(Graphics g) {
        if (showingIntro && introImage != null) {
            // Mostrar la imagen de introducción ocupando toda la pantalla
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(introImage, 0, 0, WIDTH, HEIGHT, null);
            g2.dispose();
            return;
        }
        
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // draw background image
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        } else {
            // if background image is not loaded
            g2.setColor(new Color(10, 20, 40));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
        }
        
        //title
        g2.setFont(technoFont);
        g2.setColor(Color.WHITE);
        drawCenteredString(g2, "AstroCats", WIDTH / 2, 249);
        
        // subtitle
        g2.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 50));
        g2.setColor(new Color(255, 255, 0));
        drawCenteredString(g2, "Task From the Citadel", WIDTH / 2, 302);
        
        // Main button
        if (isButtonHovered) {
            // Hover state: Light Blue (173, 216, 230)
            g2.setColor(new Color(173, 216, 230));
            g2.fillRoundRect(startButton.x, startButton.y, startButton.width, startButton.height, 30, 30);
            
            // Add gradient effect for hover
            java.awt.GradientPaint gradient = new java.awt.GradientPaint(
                startButton.x, startButton.y, new Color(173, 216, 230),
                startButton.x + startButton.width, startButton.y + startButton.height, new Color(140, 180, 200)
            );
            g2.setPaint(gradient);
            g2.fillRoundRect(startButton.x, startButton.y, startButton.width, startButton.height, 30, 30);
            
            g2.setColor(Color.BLACK);
        } else {
            // Default state: Semi-transparent Blue (65, 105, 255, 150)
            g2.setColor(new Color(65, 105, 255, 150)); // Added alpha channel
            g2.fillRoundRect(startButton.x, startButton.y, startButton.width, startButton.height, 30, 30);
            g2.setColor(Color.WHITE);
        }
        
        // Button border with transparency
        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(65, 105, 255, 150));
        g2.drawRoundRect(startButton.x, startButton.y, startButton.width, startButton.height, 30, 30);

        // Text color contrast
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.setColor(new Color(0, 0, 0, 180)); // Darker text for better contrast
        drawCenteredString(g2, "START GAME", startButton.x + startButton.width / 2, startButton.y + startButton.height / 2 + 5);
        g2.dispose();
    }
    
    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        java.awt.FontMetrics metrics = g2.getFontMetrics();
        int width = metrics.stringWidth(text);
        int height = metrics.getHeight();
        g2.drawString(text, x - width / 2, y + height / 4);
    }
    
    private boolean isMouseOverButton(int x, int y) {
        return startButton.contains(x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isMouseOverButton(e.getX(), e.getY()) && gameStarter != null) {
            if (!showingIntro) {
                // NO DETENEMOS LA MÚSICA - ¡ESTA ES LA CLAVE!
                // Mantenemos la música sonando
                
                // Iniciar la introducción
                showingIntro = true;
                introStartTime = System.currentTimeMillis();
                repaint(); // Para mostrar la imagen de introducción
                
                // Programar el inicio del juego después de 20 segundos
                new Thread(() -> {
                    try {
                        Thread.sleep(INTRO_DURATION);
                        // Iniciar el juego después de la introducción
                        SwingUtilities.invokeLater(() -> {
                            gameStarter.startGame();
                        });
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not need for this case
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not need for this case
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!showingIntro) {
            isButtonHovered = true;
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!showingIntro) {
            isButtonHovered = false;
            repaint();
        }
    }
}