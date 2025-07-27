package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.astrocats.AstroCats;

public class Cat3 {
    private BufferedImage image;
    private int x, y;
    private int tileSize = 128; 

    public Cat3(AstroCats gp, int posX, int posY) {
        this.x = posX;
        this.y = posY;
        loadImage(gp);
    }

    public void loadImage(AstroCats gp) {
        try {
            image = ImageIO.read(getClass().getResource("/images/Cat3.png"));
            System.out.println("Cat3 image loaded");
        } catch (IOException e) {
            System.err.println("Error loading Cat3 image");
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int scrollX, int scrollY, int tileSize) {
        
        int screenX = x - scrollX;
        int screenY = y - scrollY;

        if (image != null) {
            g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
        } else {
            g2.setColor(new Color(139, 69, 19)); // Marrón
            g2.fillRect(screenX, screenY, tileSize, tileSize);
        }
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getTileSize() {
        return tileSize;
    }
    
 
    public boolean containsPoint(int worldX, int worldY) {
        return worldX >= x && worldX <= x + tileSize &&
               worldY >= y && worldY <= y + tileSize;
    }
}