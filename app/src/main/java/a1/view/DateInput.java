package a1.view;

import a1.view.Window;

import java.awt.*;
import java.awt.event.*;

public class DateInput extends TextField implements ActionListener{
    private final a1.view.Window window;
    private final int width = 120;
    private final int height = 30;

    public DateInput(Window window, int x, int y) {
        this.window = window;

        setBounds(x, y, width, height);
        window.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {

    }
}
