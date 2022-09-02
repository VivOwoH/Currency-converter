package a1;

import java.util.*;

public class User {

    private Data db;
    private App app;

    public User(Data dbInstance) {
        this.db = dbInstance;
    }
    public User(){;}
    public double getRate(String country1, String country2) {
        int rowIdx = db.findCurrencyInTable(country1, country2)[0];
        int colIdx = db.findCurrencyInTable(country1, country2)[1];
        return db.getCurrencyTable()[rowIdx][colIdx];
    }

    public double[][] displayPopularCurrency() {
        return db.getPopularCurrencyTable();
    }

    // TODO: remove, output test only
    public String toString() {
        StringBuilder b = new StringBuilder();

        for (double[] row : db.getCurrencyTable()) {
            for (double i : row)
                b.append(i + "|");
            b.append("\n");
        }
        return b.toString();
    }
}
