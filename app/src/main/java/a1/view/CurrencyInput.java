package a1.view;

import java.awt.*;
import java.awt.event.*;

public class CurrencyInput extends TextField implements ActionListener{
    private final Window window;
    private final int width = 120;
    private final int height = 30;

    public CurrencyInput(Window window, int x, int y) {
        this.window = window;

        setBounds(x, y, width, height);
        window.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {

    }
}
