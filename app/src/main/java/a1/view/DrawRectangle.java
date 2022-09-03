package a1.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DrawRectangle extends JPanel {
    private final int height;
    private final int width;
    private Color color;

    public DrawRectangle(int height, int width, Color color) {
        this.height = height;
        this.width = width;
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.decode("#FFA500"));
        g.fillRect(0, 0, 100, 50);

        g.setColor(Color.decode("#87AB69"));
        g.fillRect(0, 50, 100, 50);

        g.setColor(Color.decode("#FFA500"));
        g.fillRect(100, 0, 40, height);
    }

}