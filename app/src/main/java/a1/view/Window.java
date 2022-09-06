package a1.view;

import a1.Syst;

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

    public boolean setMode(int m) {
        if (m != 1 || m != 0) {
            System.out.println("incorrect mode input!");
            return false;
        }

        if (mode == m) {
            return false;
        } else {
            mode = m;
            return true;
        }
    }

    public Syst getSystem() {
        return system;
    }
}