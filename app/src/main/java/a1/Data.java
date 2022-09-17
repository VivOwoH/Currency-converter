package a1;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        HashSet<List<String>> foundPairs = new HashSet<List<String>>();

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

                    // check if this pair of countries has been checked (do not overwrite with old data!)
                    List<String> pair = List.of(fromCountry, toCountry);
                    if (foundPairs.contains(pair)) {
                        continue; // skip this line
                    } else {
                        foundPairs.add(pair);
                    }

                    // find index of each country
                    int fromIdx = 0;
                    int toIdx = 0;
                    boolean foundFromCountry = false;
                    boolean foundToCountry = false;
                    for(int i = 0; i < fileList.length; i++){
                        if (Objects.equals(countryIdx.get(i), fromCountry)){
                            fromIdx = i;
                            foundFromCountry = true;
                        }
                        if (Objects.equals(countryIdx.get(i), toCountry)){
                            toIdx = i;
                            foundToCountry = true;
                        }
                    }

                    // construct new currencyTable if both from and to country is found
                    if (foundFromCountry && foundToCountry) {
                        tempTable[toIdx][fromIdx] = rate;
                    }
                }
                bufferReader.close();
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

    //returns true if string is in correct quality
    private static boolean qualityCheck(String date){
        // match YEAR(4 digits)-MONTH(1/2 digits)-DAY(1/2 digits)
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$");
        Matcher matcher = pattern.matcher(date);
        boolean matchFound = matcher.matches();

        return matchFound;
    }

    private static boolean inRange(String[] check, String[] top, String[] bottom){
        Integer checkYear = Integer.valueOf(check[0]);
        Integer topYear = Integer.valueOf(top[0]);
        Integer bottomYear = Integer.valueOf(bottom[0]);
        Integer checkMonth = Integer.valueOf(check[1]);
        Integer topMonth = Integer.valueOf(top[1]);
        Integer bottomMonth = Integer.valueOf(bottom[1]);
        Integer checkDay = Integer.valueOf(check[2]);
        Integer topDay = Integer.valueOf(top[2]);
        Integer bottomDay = Integer.valueOf(bottom[2]);

        if(topYear.intValue() > checkYear.intValue() && checkYear.intValue() > bottomYear.intValue()){
            return true;
        }
        else if(topYear.intValue() == checkYear.intValue() && checkYear.intValue() == bottomYear.intValue()){
            //check that it fits within range
            if(topMonth.intValue() == checkMonth.intValue() && checkMonth.intValue() == bottomMonth.intValue()){
                if(topDay.intValue() >= checkDay.intValue() && checkDay.intValue() >= bottomDay.intValue()){
                    return true;
                }
            }
            else if(topMonth.intValue() > checkMonth.intValue() && checkMonth.intValue() > bottomMonth.intValue()){
                return true;
            }
        }

        System.out.printf("Date %s-%s-%s is not in range %s-%s-%s -> %s-%s-%s\n",
                checkYear, checkMonth, checkDay,
                topYear, topMonth, topDay,
                bottomYear, bottomMonth, bottomDay);

        return false;
    }

    public static String getSummary(Syst system, String from, String to, String topDate, String bottomDate){
        if(!qualityCheck(bottomDate) || !qualityCheck(topDate)){
            System.out.println("Input strings should be in the format YEAR-MONTH-DAY");
            return null;
        }
        boolean foundOne = false;
        String[] splitTop = topDate.split("-");
        String[] splitBottom = bottomDate.split("-");

        File current = system.getCurrencyHist().get(from);
        StringBuilder summary = new StringBuilder("CONVERSION HISTORY:\n");
        ArrayList<Double> rates = new ArrayList<Double>();
        String line;

        try {
            FileReader reader = new FileReader(current);
            BufferedReader bufferReader = new BufferedReader(reader);

            while ((line = bufferReader.readLine()) != null) {
                String[] split = line.split(" ");

                if (split[1].equalsIgnoreCase(to)) {
                    String date = split[3];
                    String[] splitDate = date.split("-");

                    //check year, then month, then day
                    if(inRange(splitDate, splitTop, splitBottom)){
                        foundOne = true;
                        rates.add(Double.valueOf(split[2]));
                        summary.append(line + "\n");
                    }
                }
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!foundOne){
            return null;
        }
        else{
            Collections.sort(rates);

            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            double sum = 0;
            for(double value : rates){
                if(value > max){
                    max = value;
                }
                if(value < min){
                    min = value;
                }
                sum += value;
            }
            double average = sum / rates.size();
            double median = rates.get((int) Math.ceil(rates.size()/ 2));

            double temp = 0;

            for (double value : rates)
            {
                double squrDiffToMean = Math.pow(value - average, 2);
                temp += squrDiffToMean;
            }

            double meanOfDiffs = (double) temp / (double) (rates.size());
            double stdDev = Math.sqrt(meanOfDiffs);

            summary.append("AVERAGE: ");
            summary.append(average);
            summary.append("\nMEDIAN: ");
            summary.append(median);
            summary.append("\nMAXIMUM: ");
            summary.append(max);
            summary.append("\nMINIMUM: ");
            summary.append(min);
            summary.append("\nSTANDARD DEVIATION: ");
            summary.append(stdDev);

            return summary.toString();
        }
    }

    public static String getInfo(Syst system, String from, String to) {
        File current = system.getCurrencyHist().get(from);
        String line;
        try {
            FileReader reader = new FileReader(current);
            try (BufferedReader bufferReader = new BufferedReader(reader)) {
                while ((line = bufferReader.readLine()) != null) {
                    String[] split = line.split(" ");
                    if (split[1].equalsIgnoreCase(to)) {
                        return line;
                    }
                }
                bufferReader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        for (Map.Entry<Integer,String> entry : this.popularCountryIdx.entrySet()) {
            if (entry.getValue().equals(country)) { // country in set
                Integer keyToRemove = entry.getKey();
                this.popularCountryIdx.replace(keyToRemove, null);

                // move key forward (except last entry removed)
                if (keyToRemove != 3) {
                    for (int i = keyToRemove; i < 3; i++) {
                        this.popularCountryIdx.replace(i, this.popularCountryIdx.get(i+1));
                    }
                    this.popularCountryIdx.replace(3, null);
                }
                this.updatePopularCurrencyTable();
                return;
            }
        }
    }

    public void addPopularCountry(String country) {
        // A popular country must already be addedin currency table
        if (!this.countryIdx.containsValue(country)) {
            System.out.println("Country not in database. Add rate first.");
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (this.popularCountryIdx.get(i) == null) {
                this.popularCountryIdx.replace(i, country);
                this.updatePopularCurrencyTable();
            }
        }
        System.out.println("Maximum inputs reached. Remove a country first.");
    }

    public void updatePopularCurrencyTable() {
        // popularCurrencyTable rol&col = popularCountryIdx key
        // currency found using countryIdx keys on currencyTable[key1][key2]
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                String country1 = this.popularCountryIdx.get(row);
                String country2 = this.popularCountryIdx.get(col);

                if (country1 == null || country2 == null) {
                    this.popularCurrencyTable[row][col] = 0.0;
                    return;
                }

                int currencyRowIdx = this.findCurrencyInTable(country1, country2)[0];
                int currencyColIdx = this.findCurrencyInTable(country1, country2)[1];
                this.popularCurrencyTable[row][col] = this.currencyTable
                        [currencyRowIdx][currencyColIdx];
            }
        }
    }

    // ---------------- SETTER/GETTER -------------------

    public double[][] getCurrencyTable() {
        return this.currencyTable;
    }

    public double[][] getPopularCurrencyTable() {
        return this.popularCurrencyTable;
    }

    public void setCountryIdx(Integer key, String country) {
        this.countryIdx.put(key, country);
    }

    public HashMap<Integer, String> getCountryIdx() {
        return this.countryIdx;
    }

    public HashMap<Integer, String> getPopularCountryIdx() {
        return this.popularCountryIdx;
    }

    // ------------ UI related functions -------------
    public String[] showPopularCountry() {
        String[] tmpArray = new String[this.popularCountryIdx.values().toArray().length];
//        System.out.println(this.popularCountryIdx.values().toArray()[1].toString());
        for(int i = 0; i < this.popularCountryIdx.values().toArray().length; i++){
            tmpArray[i] = this.popularCountryIdx.values().toArray()[i].toString();
        }
        return tmpArray;
    }

    public String[] showAllCountry() {
        String[] tmpArray = new String[this.countryIdx.values().toArray().length];
//        System.out.println(this.countryIdx.values().toArray()[1].toString());
        for(int i = 0; i < this.countryIdx.values().toArray().length; i++){
            tmpArray[i] = this.countryIdx.values().toArray()[i].toString();
        }
        return tmpArray;
    } 

    // Jenkin Test n2

}
