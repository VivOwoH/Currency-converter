package a1.view;

import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Window {
    JFrame frame;
    JPanel panel;
    private int height;
    private int width;

    public Window(int width, int height) {
        this.height = height;
        this.width = width;

        frame = new JFrame("app name");
        frame.setSize(width, height);
        frame.setBackground(Color.decode("#f0ead6"));

        panel = new JPanel();

        frame.add(panel, BorderLayout.CENTER);
    }

    public void run() {
        this.draw();
    }

    private void draw() {
        DrawRectangle drawRect = new DrawRectangle(height, width, Color.green);
        frame.add(drawRect);

        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
