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

    public Window(int width, int height) {
        frame = new JFrame("app name");
        frame.setSize(width, height);

        panel = new JPanel();

        frame.add(panel);
        frame.setVisible(true);
    }

    public void run() {
        this.draw();
    }

    private void draw() {
//        DrawRect mainPanel = new DrawRect();
    }
}
