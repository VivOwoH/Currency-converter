package a1.view;

import java.awt.*;
import java.awt.event.*;

class TabButton extends Button implements ActionListener {
    private final Window window;
    private int width = 120;
    private int height = 30;

    public TabButton(Window w, int x, int y, String text) {
        super(text);

        window = w;

        setBounds(x, y, width, height);
        setForeground(Color.BLACK);

        window.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {

    }
}