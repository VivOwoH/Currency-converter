package a1.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import a1.Admin;
import a1.Data;
import a1.Syst;

public class AdminWindow extends Window {
    private final Syst system;
    private final int width;
    private final int height;
    private Label label;
    private Admin admin;
    private Data data;

    private AddRateButton addRateBtn;
    private CurrencyInput from;
    private CurrencyInput to;
    private CurrencyInput rate;

    private CurrencySelect popCurrSelect;
    private PopCurrUpdateButton popCurrAdd;
    private PopCurrUpdateButton popCurrDel;

    private WindowText fromTxt;
    private WindowText toTxt;
    private WindowText rateTxt;
    private WindowText confirmTxt;

    public AdminWindow(int width, int height, Syst system) {
        super(width, height, "Admin", system);

        this.width = width;
        this.height = height;
        this.system = system;
        this.admin = system.getAdminInstance();
        this.data = system.getDataInstance();

        label = new Label("0", Label.RIGHT);
        label.setBackground(Color.decode("#f0ead6"));

        from = new CurrencyInput(this, 240, 200);
        to = new CurrencyInput(this, 400, 200);
        rate = new CurrencyInput(this, 650, 200);

        fromTxt = new WindowText(this, 200, 200, "from:");
        toTxt = new WindowText(this, 380, 200, "to:");
        rateTxt = new WindowText(this, 580, 200, "new rate:");
        confirmTxt = new WindowText(this, 580, 255, "");

        popCurrSelect = new CurrencySelect(this, 800, 300);
        popCurrAdd = new PopCurrUpdateButton(this, "add", 800, 380, true);
        popCurrDel = new PopCurrUpdateButton(this, "delete", 800, 410, false);

        addRateBtn = new AddRateButton(this, 470, 250);
    }

    public void run() {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                system.systemClean();
                System.exit(0);
            }
        });

        setLayout(null);
        setSize(width, height);
        setVisible(true);
    }

    public void addRate() {
        String fromStr = from.getText().toUpperCase();
        String toStr = to.getText().toUpperCase();
        String rateStr = rate.getText();
        Double rateDouble = Double.parseDouble(rateStr);

        admin.addRate(fromStr, toStr, rateDouble);
        confirmTxt.setText("Rate added!");
    }

    @Override
    public void refreshTable() {
        String[][] newData = system.getUserInstance().displayPopularCurrency();
        // String[] newColumns = system.getDataInstance().showPopularCountry();

        // clear all data
        DefaultTableModel m = (DefaultTableModel) super.popTable.getModel();
        m.getDataVector().removeAllElements();
        m.fireTableDataChanged();
        // m.setColumnCount(0);

        // add data
        for (String[] row : newData) {
            m.addRow(row);
        }
        // JList<String> rowHead = new JList<String>(newColumns);
        // super.sp.setRowHeaderView(rowHead);

        //refreshComboBox
        String[] newSelections = data.showAllCountry();

        super.currSelec.removeAllItems();
        super.currSelecResult.removeAllItems();
        popCurrSelect.removeAllItems();

        for (String newItem : newSelections) {
            super.currSelec.addItem(newItem);
            super.currSelecResult.addItem(newItem);
            popCurrSelect.addItem(newItem);
        }
    }

    public void addPopularCurrecny() {
        String country = String.valueOf(popCurrSelect.getSelectedItem());

        Data data = system.getDataInstance();
        data.addPopularCountry(country);
    }

    public void removePopularCurrecny() {
        String country = String.valueOf(popCurrSelect.getSelectedItem());

        Data data = system.getDataInstance();
        data.removePopularCountry(country);
    }
}
