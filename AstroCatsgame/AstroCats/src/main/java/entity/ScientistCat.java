package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.astrocats.AstroCats;

public class ScientistCat {
    private BufferedImage image;
    private int x, y;
    private int tileSize = 128; // Tamaño del científico

    public ScientistCat(AstroCats gp, int posX, int posY) {
        this.x = posX;
        this.y = posY;
        loadImage(gp);
    }

    public void loadImage(AstroCats gp) {
        try {
            image = ImageIO.read(getClass().getResource("/images/scientist.png"));
            System.out.println("Scientist image loaded");
        } catch (IOException e) {
            System.err.println("Error loading scientist image");
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int scrollX, int scrollY, int tileSize) {
        // Calcular la posición en pantalla (ajustada por el scrolling)
        int screenX = x - scrollX;
        int screenY = y - scrollY;

        if (image != null) {
            g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
        } else {
            g2.setColor(Color.RED);
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
    
    // Verificar si un punto está dentro del área del científico
    public boolean containsPoint(int worldX, int worldY) {
        return worldX >= x && worldX <= x + tileSize &&
               worldY >= y && worldY <= y + tileSize;
    }
}