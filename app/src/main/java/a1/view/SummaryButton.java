package a1.view;

import java.awt.*;
import java.awt.event.*;

public class SummaryButton extends Button implements ActionListener {
    private final Window window;
    private int width = 80;
    private int height = 30;

    public SummaryButton(Window window) {
        super("summarize");

        this.window = window;

        setBounds(580, 475, width, height);

        this.window.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {
        //PRINT SUMMARY!!
        window.summary();
    }
}