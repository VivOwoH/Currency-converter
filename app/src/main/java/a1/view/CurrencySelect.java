package a1.view;

import a1.Syst;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JComboBox;

<<<<<<< HEAD
<<<<<<< HEAD
public class CurrencySelect extends JComboBox<String> implements ActionListener{
=======
public class CurrencySelect extends JComboBox<String> {
>>>>>>> 179851f (added CurrencySelect.java)
=======
public class CurrencySelect extends JComboBox<String> implements ActionListener{
>>>>>>> b36240c (fix conflicts)
    private final Window window;
    private final Syst system;

    private String[] currencyList;

    private final int height = 75;
<<<<<<< HEAD
<<<<<<< HEAD
    private final int width = 100;
=======
    private final int width = 75;
>>>>>>> 179851f (added CurrencySelect.java)
=======
    private final int width = 100;
>>>>>>> b36240c (fix conflicts)

    public CurrencySelect(Window window, int x, int y) {
        super(Syst.getCurrencies());

        this.window = window;
        this.system = window.getSystem();
        this.currencyList = Syst.getCurrencies();

        addActionListener(this);

        setBounds(x, y, width, height);
        this.window.add(this);
    }

    public void addCurrency(String currency) {
        //adds currency to current list and updates it
    }

    public void actionPerformed(ActionEvent ae) {

    }
}