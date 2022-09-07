package a1.view;

import java.awt.*;
import java.awt.event.*;

public class RefreshButton extends Button implements ActionListener {
    private final Window window;
    private int width = 80;
    private int height = 30;

    public RefreshButton(Window window) {
        super("reload");

        this.window = window;

        setBounds(580, 500, width, height);

        this.window.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {

    }
    
}
