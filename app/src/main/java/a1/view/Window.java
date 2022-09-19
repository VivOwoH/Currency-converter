package a1.view;

import a1.Data;
import a1.Syst;
import a1.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Window extends Frame {
    private int height;
    private int width;
    private Label label;
    private DateInput dateInLower;
    private DateInput dateInUpper;
    private CurrencyInput summaryCurrencyIn;
    private CurrencyInput summaryCurrencyOut;

    private WindowText summaryInText;
    private WindowText summaryOutText;
    private SummaryButton summaryButton;
    private WindowText dateFrom;
    private WindowText dateTo;
    private Syst system;
    private Data data;

    private TabButton convBtn;
    private CurrencyInput currIn;
    protected CurrencySelect currSelec;
    protected CurrencySelect currSelecResult;
    private WindowText convertResult;
    protected PopularCurrencies popTable;
    private RefreshButton refreshBtn;
    protected JScrollPane sp;

    public Window(int width, int height, String title, Syst system) {
        super(title);
        this.system = system;

        this.height = height;
        this.width = width;

        label = new Label("0", Label.RIGHT);
        label.setBackground(Color.decode("#f0ead6"));

        String[][] popCurr = this.system.getUserInstance().displayPopularCurrency();
        this.data = system.getDataInstance();
        String[] countries = data.showPopularCountry();
        DefaultTableModel dm = new DefaultTableModel(popCurr, countries);

        popTable = new PopularCurrencies(this, popCurr, countries);
        JList<String> rowHead = new JList<String>(countries);
        rowHead.setFixedCellWidth(50);
        sp = new JScrollPane(popTable);
        sp.setBounds(300, 300, 400, 100);
        sp.setRowHeaderView(rowHead);
        add(sp);

        convBtn = new TabButton(this, 450, 150, "convert");

        currIn = new CurrencyInput(this, 250, 100);

        currSelec = new CurrencySelect(this, 380, 80);
        currSelecResult = new CurrencySelect(this, 650, 80);

        convertResult = new WindowText(this, 550, 105, "plz enter value");

        refreshBtn = new RefreshButton(this);

        dateInLower = new DateInput(this, 240, 450);
        dateInUpper = new DateInput(this, 400, 450);

        dateFrom = new WindowText(this, 200, 450, "start:");
        dateTo = new WindowText(this, 370, 450, "end:");

        summaryCurrencyIn = new CurrencyInput(this, 240, 500);
        summaryCurrencyOut = new CurrencyInput(this, 400, 500);

        summaryInText = new WindowText(this, 200, 500, "from");
        summaryOutText = new WindowText(this, 370, 500, "to");
        summaryButton = new SummaryButton(this);
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

    public Syst getSystem() {
        return system;
    }

    public void summary(){
        String fromDate = dateInLower.getText();
        String toDate = dateInUpper.getText();
        String fromCur = summaryCurrencyIn.getText();
        String toCur = summaryCurrencyOut.getText();

        String summary = Data.getSummary(this.system, fromCur, toCur, toDate, fromDate);

        if(summary != null){
            System.out.println(summary);
        }
    }

    public void convertCurrency() {
        // get input
        String inString = currIn.getText();
        double money = Double.parseDouble(inString);

        String country1 = String.valueOf(currSelec.getSelectedItem());
        String country2 = String.valueOf(currSelecResult.getSelectedItem());

        // run convert money
        User user = system.getUserInstance();
        double rate = user.convertMoney(country1, country2, money);
        double output = rate * money;

        // print output
        convertResult.setText(Double.toString(output));
    }

    public void refreshTable() {
        String[][] newData = system.getUserInstance().displayPopularCurrency();

        // clear all data
        DefaultTableModel m = (DefaultTableModel) popTable.getModel();
        m.getDataVector().removeAllElements();
        m.fireTableDataChanged();

        // add data
        for (String[] row : newData) {
            m.addRow(row);
        }

        //refreshComboBox
        String[] newSelections = data.showAllCountry();

        currSelec.removeAllItems();
        currSelecResult.removeAllItems();

        for (String newItem : newSelections) {
            currSelec.addItem(newItem);
            currSelecResult.addItem(newItem);
        }
    }
}