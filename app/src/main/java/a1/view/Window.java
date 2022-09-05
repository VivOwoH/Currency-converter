package a1.view;

import java.io.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;

public class Window extends Frame{
    private int height;
    private int width;
    private Label label;

    private int mode; //0 for convert, 1 for popular
    private TabButton convTabBtn;
    private TabButton popTabBtn;

    public Window(int width, int height, String title) {
        super(title);

        this.height = height;
        this.width = width;
        mode = 0;

        label = new Label("0", Label.RIGHT);
        label.setBackground(Color.decode("#f0ead6"));

        convTabBtn = new TabButton(this, 10, 50, "convert");

        popTabBtn = new TabButton(this, 10, 80, "popular currencies");
    }

    public void run() {
        if (mode == 0) {
            this.drawConvertPage();
        } else {
            this.drawPopularPage();
        }

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {System.exit(0);}
        });

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {System.exit(0);}
        });

        setLayout(null);
        setSize(width, height);
        setVisible(true);
    }

    private void drawConvertPage() {

    }

    private void drawPopularPage() {

    }

    public boolean setMode(int m) {
        if (m != 1 || m != 0) {
            System.out.println("incorrect mode input!");
            return false;
        }

        if (mode == m) {
            return false;
        } else {
            mode = m;
            return true;
        }
    }
}
