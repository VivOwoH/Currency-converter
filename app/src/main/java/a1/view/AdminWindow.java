package a1.view;

import java.awt.*;
import java.awt.event.*;

import a1.Admin;
import a1.Syst;

public class AdminWindow extends Window {
    private final Syst system;
    private final int width;
    private final int height;
    private Label label;
    private Admin admin;

    private AddRateButton addRateBtn;
    private CurrencyInput from;
    private CurrencyInput to;
    private CurrencyInput rate;

    private WindowText fromTxt;
    private WindowText toTxt;
    private WindowText rateTxt;

    public AdminWindow(int width, int height, Syst system) {
        super(width, height, "Admin", system);

        this.width = width;
        this.height = height;
        this.system = system;
        this.admin = system.getAdminInstance();

        label = new Label("0", Label.RIGHT);
        label.setBackground(Color.decode("#f0ead6"));

        from = new CurrencyInput(this, 240, 200);
        to = new CurrencyInput(this, 400, 200);
        rate = new CurrencyInput(this, 650, 200);

        fromTxt = new WindowText(this, 200, 200, "from:");
        toTxt = new WindowText(this, 380, 200, "to:");
        rateTxt = new WindowText(this, 580, 200, "new rate:");

        addRateBtn = new AddRateButton(this, 470, 250);
    }

    public void run() {

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {system.closeAWindow();}
        });

        setLayout(null);
        setSize(width, height);
        setVisible(true);
    }

    public void addRate() {
        String fromStr = from.getText();
        String toStr = to.getText();
        String rateStr = rate.getText();
        Double rateDouble = Double.parseDouble(rateStr);

        admin.addRate(fromStr, toStr, rateDouble);
    }
    
}
