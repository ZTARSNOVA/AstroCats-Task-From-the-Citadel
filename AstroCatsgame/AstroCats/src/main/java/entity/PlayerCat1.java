package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.game.astrocats.AstroCats;
import com.game.astrocats.KeyHandler;

public class PlayerCat1 extends EntityCat1 {
    AstroCats gp;
    KeyHandler keyH;
    private BufferedImage idleImage; //static image

    private List<BufferedImage> animationFrames = new ArrayList<>();
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DURATION = 100; 

    private boolean isMoving = false;
    

    public static final int MAP_WIDTH = 2670;  
    public static final int MAP_HEIGHT = 1024; 
    
    // Area of the map that the player can move around in
    public  static final int BOUND_X = 60;      // Left edge of the map
    public  static final int BOUND_Y = 400;    
    public static final int BOUND_WIDTH = 1610;   //  Width of the map
    public  static final int BOUND_HEIGHT = 165;  // Height of the map

    public PlayerCat1(AstroCats gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();

        // load image
        try {
            idleImage = ImageIO.read(getClass().getResource("/images/Cat1.png"));
            System.out.println("static image loaded");
        } catch (IOException e) {
            System.err.println("ERROR: Not able to load image");
            e.printStackTrace();
        }

        // load animation frames
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(getClass().getResourceAsStream("/images/cat1_animated.gif"));
            ImageReader reader = ImageIO.getImageReaders(iis).next();
            reader.setInput(iis);
            
            for (int i = 0; i < reader.getNumImages(true); i++) {
                animationFrames.add(reader.read(i));
            }
            reader.dispose();
            
            System.out.println("Animation load correct (" + animationFrames.size() + " frames)");
        } catch (IOException e) {
            System.err.println("ERROR: not fount the animation cat2_animated.gif");
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        // initial position
        x = BOUND_X + 50;
        y = BOUND_Y + 50;
        speed = 4;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getTileSize() {
        return gp.tileSize;
    }
    
    public int getBoundX() {
        return BOUND_X;
    }
    
    public int getBoundY() {
        return BOUND_Y;
    }
    
    public int getBoundWidth() {
        return BOUND_WIDTH;
    }
    
    public int getBoundHeight() {
        return BOUND_HEIGHT;
    }

    public void update() {
        int originalX = x;
        int originalY = y;

        // Detect movements
        isMoving = false;
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            isMoving = true;
        }

        if (keyH.upPressed) y -= speed;
        if (keyH.downPressed) y += speed;
        if (keyH.leftPressed) x -= speed;
        if (keyH.rightPressed) x += speed;


        if (x < BOUND_X) x = BOUND_X;
        if (x > BOUND_X + BOUND_WIDTH - gp.tileSize) x = BOUND_X + BOUND_WIDTH - gp.tileSize;
        if (y < BOUND_Y) y = BOUND_Y;
        if (y > BOUND_Y + BOUND_HEIGHT - gp.tileSize) y = BOUND_Y + BOUND_HEIGHT - gp.tileSize;

        // upload if the player has moved
        if (isMoving) {
            updateAnimation();
        }
    }

    private void updateAnimation() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % animationFrames.size();
            lastFrameTime = currentTime;
        }
    }

    public void draw(Graphics2D g2, int scrollX, int scrollY) {
        int screenX = x - scrollX;
        int screenY = y - scrollY;
        
        if (!isMoving && idleImage != null) {
            g2.drawImage(idleImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else if (isMoving && !animationFrames.isEmpty()) {
            // show the current frame of the animation
            g2.drawImage(animationFrames.get(currentFrame), screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }
}