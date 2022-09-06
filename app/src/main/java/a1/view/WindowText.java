package a1.view;

import java.awt.*; 

public class WindowText extends Label {
    private final String text;
    private final Window window;
    private final int width = 100;
    private final int height = 20;

    public WindowText(Window window, int x, int y, String text) {
        super(text);

        this.window = window;
        this.text = text;

        setBounds(x, y, width, height);
        this.window.add(this);
    }
    
}
