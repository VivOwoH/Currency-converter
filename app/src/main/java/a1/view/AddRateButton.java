package a1.view;

import java.awt.*;
import java.awt.event.*;

public class AddRateButton extends Button implements ActionListener{
    private final AdminWindow window;
    private final int width = 80;
    private final int height = 30;
    
    public AddRateButton(AdminWindow window, int x, int y) {
        super("add rate");

        this.window = window;

        setBounds(x, y, width, height);

        window.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        window.addRate();
    }
}
