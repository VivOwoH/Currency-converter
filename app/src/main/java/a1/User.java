package a1;

import java.util.*;

public class User {

    public Data db;
    public Syst syst;

    public User(Data dbInstance) {
        this.db = dbInstance;
    }
    public User(Data db, Syst syst){this.db = db;
        this.syst = syst;}
    public double getRate(String country1, String country2) {
        int rowIdx = db.findCurrencyInTable(country1, country2)[0];
        int colIdx = db.findCurrencyInTable(country1, country2)[1];
        return db.getCurrencyTable()[rowIdx][colIdx];
    }

    public double convertMoney(String country1, String country2, double amount){
        int from = -1;
        int to = -1;
        for(int i = 0; i < db.size; i++){
            if (Objects.equals(db.countryIdx.get(i), country1)){
                from = i;
            }
            if (Objects.equals(db.countryIdx.get(i), country2)){
                to = i;
            }
        }

        if(from == -1 || to == -1){
            System.out.println("Error finding countries.");
            return 0;
        }

        return db.getCurrencyTable()[to][from];
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
