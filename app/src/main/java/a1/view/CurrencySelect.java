package a1.view;

import a1.Syst;

import java.awt.*;
import java.awt.event.*;

public class CurrencySelect extends List implements ActionListener {
    private final Window window;
    private final Syst system;

    public CurrencySelect(Window window, int x, int y) {
        this.window = window;
        this.system = window.getSystem();

        //read and add currencies from list

        this.window.add(this);
    }

    public void addCurrency(String currency) {
        //adds currency to current list and updates it
    }

    public void actionPerformed(ActionEvent ae) {

    }
}