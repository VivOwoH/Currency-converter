package a1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Syst {
    final String[] initialCurrencies = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD"};
    //initial rates are all in form US to
    final Double[] initialRates = {Double.valueOf(1.0),
            Double.valueOf(0.99),
            Double.valueOf(0.86),
            Double.valueOf(139.44),
            Double.valueOf(1.3156),
            Double.valueOf(1.47)
    };
    static HashMap<String, Double> initialRatesMap = new HashMap<>();
    public HashMap<String, File> currencyHist = new HashMap<>();
    static File dir = new File("tmp/test");
    public Data data = new Data();
    public Admin admin = new Admin(this, data);
    /*
        Input FromCountry, ToCountry and the rate and this will return the string with date that should then
        be written to a file.
     */
    public static String currencyFormat(String countryOne, String countryTwo, Double rate){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        String returnStr = String.format("%s %s %.2f %s\n", countryOne, countryTwo, rate, strDate);
        return returnStr;
    }
    public void systemInit(){
        //create new text files in resources to record history.
        /*e.g. USD file records all US From in form:
                USD EUR 0.99 9/1/22
           reads as one USD gets 0.99 EUR on 9/1/22
        */
        dir.mkdirs();

        for(int i = 0; i < 6; i++){
            try{
                File tmp = new File(dir, initialCurrencies[i]);
                currencyHist.put(initialCurrencies[i], tmp);
                tmp.createNewFile();

                //initialize map
                initialRatesMap.put(initialCurrencies[i], initialRates[i]);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        //write five conversions for each country
        for(String country : initialCurrencies){
            Double[] rates = {null, null, null, null, null, null};

            if(!(country.equals("USD"))){
                //need to mess w/ conversion values
                for(int i = 0; i < 6; i++){
                    if(initialCurrencies[i].equals(country)){
                        rates[i] = Double.valueOf(1.0);
                    }
                    else{
                        if(initialCurrencies[i].equals("USD")) {
                            rates[i] = (1/(initialRatesMap.get(country)));
                        }
                        else{
                            Double first = Double.valueOf(1 / initialRatesMap.get(country));
                            rates[i] = first * initialRatesMap.get(initialCurrencies[i]);
                        }
                    }
                }
            } else{
                rates = initialRates;
            }

            try{
                FileWriter currentWriter = new FileWriter(currencyHist.get(country));
                for(int i = 0; i < 6; i++){
                    if(!initialCurrencies[i].equals(country)) {
                        String toWrite = currencyFormat(country, initialCurrencies[i], rates[i]);
                        currentWriter.write(toWrite);
                    }
                }
                currentWriter.close();
            }
            catch(IOException e){
                System.out.println("Couldn't write to file " + country);
                e.printStackTrace();
            }
        }
    }
    public void systemClean(){
        File dir = new File("tmp/test");
        for (File file: Objects.requireNonNull(dir.listFiles())) {
            if (!file.isDirectory()) {
                file.delete();
            }
        };
        dir.delete();
    }
}