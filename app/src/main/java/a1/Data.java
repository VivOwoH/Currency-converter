package a1;

import java.util.*;

public class Data {
    /*
     * hashmap: int key, string country
     * 0 = AUD
     * 1 = SGD
     * 2 = US
     * 3 = EU
     * (Safer for Key to be audo-assigned than admin-assigned)
     */
    private HashMap<Integer, String> countryIdx;
    /*
     * 2d array [country1][country2]
     * E.g. [0][0] (r1c1) = AUD <-> AUD
     * [0][1] (r1c2) = AUD <-> SGD
     * [3][2] (r3c2) = EU <-> US
     */
    private double[][] currencyTable;
    private double[][] popularCurrencyTable;


    public Data() {
        // default initialized with 6 currencies and 6 exchange rates
        this.currencyTable = new double[6][6]; // may need to expand this 2d array later (or ->arrayList)
        this.popularCurrencyTable = new double[4][4];
//        this.countryIdx = new HashMap<Integer, String>() {{
//            put(1, App.initialCurrencies[0]);
//            put(2, App.initialCurrencies[1]);
//            put(3, App.initialCurrencies[2]);
//            put(4, App.initialCurrencies[3]);
//            put(5, App.initialCurrencies[4]);
//            put(6, App.initialCurrencies[5]); // TODO: countries should be loaded from file, along with date
//        }};
    }

    //TODO: function->admin maintain/update popular currency table



    public void setCountryIdx(Integer key, String country) {
        this.countryIdx.put(key, country);
    }

    public void setCurrency(String country1, String country2, double value) {
        int rowIdx = this.findCurrencyInTable(country1, country2)[0];
        int colIdx = this.findCurrencyInTable(country1, country2)[1];
        this.currencyTable[rowIdx][colIdx] = value;
    }

    public int[] findCurrencyInTable(String country1, String country2) {
        
        int rowIdx = -1; // country1 index
        int colIdx = -1; // country2 index

        for (Map.Entry<Integer, String> e : this.countryIdx.entrySet()) {
            if (e.getValue().equals(country1))
                rowIdx = e.getKey()-1;
            if (e.getValue().equals(country2))
                colIdx = e.getKey()-1;
        }

        if (rowIdx < 0 || colIdx < 0) 
            throw new IllegalArgumentException("Invalid input.");

        int[] result = new int[]{rowIdx, colIdx};
        return result;
    }

    public double[][] getCurrencyTable() {
        return this.currencyTable;
    }

    public double[][] getPopularCurrencyTable() {
        return this.popularCurrencyTable;
    }

    public HashMap<Integer, String> getCountryIdx() {
        return this.countryIdx;
    }
}
