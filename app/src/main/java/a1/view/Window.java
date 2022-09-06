package a1.view;

import a1.Syst;
import a1.User;

import java.awt.*;
import java.awt.event.*;

public class Window extends Frame{
    private int height;
    private int width;
    private Label label;

    private Syst system;

    private int mode; //0 for convert, 1 for popular
    private TabButton convTabBtn;
    private TabButton popTabBtn;
    private CurrencyInput currIn;
    private CurrencySelect currSelec;
    private CurrencySelect currSelecResult;
    private WindowText convertResult;

    public Window(int width, int height, String title, Syst system) {
        super(title);
        this.system = system;

        this.height = height;
        this.width = width;
        mode = 0;

        label = new Label("0", Label.RIGHT);
        label.setBackground(Color.decode("#f0ead6"));

        convTabBtn = new TabButton(this, 450, 150, "convert");

        currIn = new CurrencyInput(this, 250, 100);

        currSelec = new CurrencySelect(this, 380, 80);
        currSelecResult = new CurrencySelect(this, 650, 80);

        convertResult = new WindowText(this, 550, 105, "sample");
    }

    public void run() {
        if (mode == 0) {
            this.drawConvertPage();
        } else {
            this.drawPopularPage();
        }

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {System.exit(0);}
        });

        setLayout(null);
        setSize(width, height);
        setVisible(true);
    }

    private void drawConvertPage() {

    }

    private void drawPopularPage() {

    }

    public Syst getSystem() {
        return system;
    }

    public void convertCurrency() {
        //get input
        String inString = currIn.getText();
        double money = Double.parseDouble(inString);

        String country1 = String.valueOf(currSelec.getSelectedItem());
        String country2 = String.valueOf(currSelecResult.getSelectedItem());

        //run convert money
        User user = system.getUserInstance();
        Double output = user.convertMoney(country1, country2, money);

        //print output
        convertResult.setText(Double.toString(output));
    }
}