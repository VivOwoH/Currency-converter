package a1.view;

import java.awt.*;
import java.awt.event.*;

public class PopCurrUpdateButton extends Button implements ActionListener {
    private AdminWindow window;
    private int width = 100;
    private int height = 30;
    private boolean isAdd;

    public PopCurrUpdateButton(AdminWindow window, String text, int x, int y, boolean isAdd) {
        super(text);

        this.window = window;
        this.isAdd = isAdd;
        setBounds(x ,y, width, height);

        window.add(this);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isAdd) {
            window.addPopularCurrecny();
            return;
        }
        window.removePopularCurrecny();
    }

}
