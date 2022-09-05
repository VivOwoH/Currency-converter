package a1.view;

import java.awt.*;
import java.awt.event.*;

public class CurrencySelect extends List implements ActionListener {
    private final Window window;
    private final Syst system;

    public CurrencySelect(Window winow, int x, int y, Syst system) {
        this.window = window;
        this.system = system;

        //read and add currencies from list

        this.window.add(this);
    }

    public void addCurrency(String currency) {
        //adds currency to current list and updates it
    }

    public void actionPerformed() {

    }
}