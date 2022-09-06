package a1;

import java.io.*;
import java.util.*;

public class Data {
    /*
     * hashmap: int key, string country
     * 0 = AUD
     * 1 = SGD
     * 2 = US
     * 3 = EU
     */
    private HashMap<Integer, String> countryIdx;
    private HashMap<Integer, String> popularCountryIdx; // not same as countryIdx
    /*
     * 2d array [country1][country2]
     * E.g. [0][0] = AUD <-> AUD
     * [0][1] = AUD <-> SGD
     * [3][2] = EU <-> US
     */
    private double[][] currencyTable;
    private double[][] popularCurrencyTable;


    public Data() {
        // default initialized with 6 currencies and 6 exchange rates
        this.currencyTable = new double[6][6]; // may need to expand this 2d array later (or ->arrayList)
        this.countryIdx = new HashMap<Integer, String>();
        for (int i = 0; i < 6; i++)
            this.countryIdx.put(i, Syst.initialCurrencies[i]);

        // default initialized with first 4 currencies as most popular
        this.popularCurrencyTable = new double[4][4]; // always 4x4
        this.popularCountryIdx = new HashMap<Integer, String>(); // always 4 countries
        for (int i = 0; i < 4; i++)
            this.popularCountryIdx.put(i, countryIdx.get(i));
    }

    public void initialize(Syst system) {
        updateCurrencyTable(system);
        updatePopularCurrencyTable();
    }

    public void updateCurrencyTable(Syst system) {
        // read every file in test
        File directory = system.getDirectory();
        String[] fileList = directory.list();
        String line;
        assert fileList != null;
        double[][] tempTable = new double[fileList.length][fileList.length];
        try {
            for (String file : fileList) {
                FileReader reader = new FileReader(directory + "/" + file);
                BufferedReader bufferReader = new BufferedReader(reader);

                while ((line = bufferReader.readLine()) != null) {
                    // read first three word of each line, split into array
                    String[] split = line.split(" ");
                    String fromCountry = split[0];
                    String toCountry = split[1];
                    double rate = Double.parseDouble(split[2]);

                    // find index of each country
                    int fromIdx = 0;
                    int toIdx = 0;
                    boolean foundFromCountry = false;
                    boolean foundToCountry = false;
                    for(int i = 0; i < fileList.length; i++){ // incredibly inefficient, oh well :P
                        if (Objects.equals(countryIdx.get(i), fromCountry) && !(foundFromCountry)){
                            fromIdx = i;
                            foundFromCountry = true;
                        }
                        if (Objects.equals(countryIdx.get(i), toCountry) && !(foundToCountry)){
                            toIdx = i;
                            foundToCountry = true;
                        }
                    }

                    // construct new currencyTable if both from and to country is found
                    if (foundFromCountry && foundToCountry) {
                        tempTable[toIdx][fromIdx] = rate;
                    }

                }
            }
            this.currencyTable = tempTable;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // also update popular currency table
        this.updatePopularCurrencyTable();
    }

    public void addCountryToIdx(String countryName){
        countryIdx.put(countryIdx.size(), countryName);
    }

    public String getSummary(Syst system, String from, String to){
        File current = system.getCurrencyHist().get(from);
        StringBuilder summary = new StringBuilder("");
        String line;

        try {
            FileReader reader = new FileReader(current);
            BufferedReader bufferReader = new BufferedReader(reader);

            while ((line = bufferReader.readLine()) != null) {
                String[] split = line.split(" ");
                if (split[1].equalsIgnoreCase(to)) {
                    summary.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(summary.equals(new StringBuilder(""))){
            return null;
        }
        else{
            return summary.toString();
        }
    }

    public void setCountryIdx(Integer key, String country) {
        this.countryIdx.put(key, country);
    }

    public static String getInfo(Syst system, String from, String to) {
        File current = system.getCurrencyHist().get(from);
        String line;
        try {
            FileReader reader = new FileReader(current);
            BufferedReader bufferReader = new BufferedReader(reader);

            while ((line = bufferReader.readLine()) != null) {
                String[] split = line.split(" ");
                if (split[1].equalsIgnoreCase(to)) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                rowIdx = e.getKey();
            if (e.getValue().equals(country2))
                colIdx = e.getKey();
        }

        if (rowIdx < 0 || colIdx < 0)
            throw new IllegalArgumentException("Invalid input.");

        int[] result = new int[]{rowIdx, colIdx};
        return result;
    }

    public void removePopularCountry(String country) {
        this.popularCountryIdx.values().remove(country);
        this.updatePopularCurrencyTable();
    }

    public void addPopularCountry(String country) {
        int currentNum = this.popularCountryIdx.size();
        if (currentNum == 4) {
            System.out.println("Maximum inputs reached. Remove a country first.");
            // TODO: does not throw error, but display this msg on UI
        } else {
            this.popularCountryIdx.put(currentNum + 1, country);
            this.updatePopularCurrencyTable();
        }
    }

    public void updatePopularCurrencyTable() {
        // popularCurrencyTable rol&col = popularCountryIdx key
        // currency found using countryIdx keys on currencyTable[key1][key2]
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                String country1 = this.popularCountryIdx.get(row);
                String country2 = this.popularCountryIdx.get(col);

                int currencyRowIdx = this.findCurrencyInTable(country1, country2)[0];
                int currencyColIdx = this.findCurrencyInTable(country1, country2)[1];
                this.popularCurrencyTable[row][col] = this.currencyTable
                        [currencyRowIdx][currencyColIdx];
            }
        }
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

    public HashMap<Integer, String> getPopularCountryIdx() {
        return this.popularCountryIdx;
    }
}
