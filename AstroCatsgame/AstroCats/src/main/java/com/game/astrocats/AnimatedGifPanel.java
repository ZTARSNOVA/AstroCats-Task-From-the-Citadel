package com.game.astrocats;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class AnimatedGifPanel extends JPanel {
    private ImageIcon gifIcon;

    public AnimatedGifPanel(String resourcePath) {
        gifIcon = new ImageIcon(getClass().getResource(resourcePath));
        setPreferredSize(new Dimension(1280, 1024));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gifIcon != null) {
            Image image = gifIcon.getImage();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void showForDuration(String resourcePath, int durationMs, Runnable onComplete) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setUndecorated(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            AnimatedGifPanel panel = new AnimatedGifPanel(resourcePath);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Timer timer = new Timer(durationMs, e -> {
                frame.dispose();
                if (onComplete != null) {
                    onComplete.run();
                }
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

}
