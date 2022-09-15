package a1;

import java.io.*;
import java.util.*;

public class User {

    protected Data db;
    protected Syst syst;

    public User(Data dbInstance) {
        this.db = dbInstance;
    }

    public User(Data db, Syst syst) {
        this.db = db;
        this.syst = syst;
    }

    public double convertMoney(String country1, String country2, double amount) {
        int from = -1;
        int to = -1;
        for (int i = 0; i < db.getCurrencyTable().length; i++) {
            if (Objects.equals(db.getCountryIdx().get(i), country1)) {
                from = i;
            }
            if (Objects.equals(db.getCountryIdx().get(i), country2)) {
                to = i;
            }
        }

        if (from == -1 || to == -1) {
            throw new IllegalArgumentException("Error finding countries.");
        }

        return db.getCurrencyTable()[to][from];
    }

    public int compareRate(double rateToCompare, String country1, String country2) {

        double prevRate = -1;
        double currentRate = -1;

        try {
            // find the file corresponding to country1
            File info = syst.getCurrencyHist().get(country1);
            FileReader reader = new FileReader(info);
            BufferedReader bufferReader = new BufferedReader(reader);

            String line;
            // find 1st & 2nd data lines IN ORDER corresponding to country2
            while ((line = bufferReader.readLine()) != null) {
                String toCountry = line.split(" ")[1];

                if (currentRate == -1 && toCountry.equals(country2)) {
                    currentRate = Double.parseDouble(line.split(" ")[2]);
                    assert rateToCompare == currentRate : "Data inconsistent in table and database.";
                } else if (currentRate != -1 && toCountry.equals(country2)) {
                    prevRate = Double.parseDouble(line.split(" ")[2]);
                }

                if (prevRate != -1 && currentRate != -1)
                    break;
            }
            bufferReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File must exist in Database to be read");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (prevRate == -1) // no previous rate 
                        // (i.e. current rate the only existing record)
            return -2;

        else {
            if (rateToCompare == prevRate)
                return 0;
            else if (rateToCompare > prevRate)
                return 1;
            else
                return -1;
        }
    }

    public String[][] displayPopularCurrency() {
        // update again before fectch for validaty
        db.updatePopularCurrencyTable();

        double[][] table = db.getPopularCurrencyTable();
        String[][] resultTable = new String[table.length][table[0].length];
        // popularCurrencyTable rol & col = popularCountryIdx key
        HashMap<Integer, String> idx = db.getPopularCountryIdx();

        // for every value in the table, compare to previous rate
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[0].length; col++) {
                
                double rate = table[row][col];

                // col = from, row = to
                int result = this.compareRate(rate, idx.get(col), idx.get(row));
                
                // 1 = increased
                // 0 = no change
                // -1 = decreased
                // -2 = no history record to compare
                if (result == 1)
                    resultTable[row][col] = String.format("%.2f (↑)", rate);
                else if (result == 0 || result == -2)
                    resultTable[row][col] = Double.toString(rate);
                else if (result == -1)
                    resultTable[row][col] = String.format("%.2f (↓)", rate);
            }
        }
        return resultTable;
    }
}
