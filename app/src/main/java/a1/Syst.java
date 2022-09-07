package a1;

import a1.view.AdminWindow;
import a1.view.Window;

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
    // other classes can access but cannot change
    final static String[] initialCurrencies = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD"};
    //initial rates are all in form US to
    final Double[] initialRates = {Double.valueOf(1.0),
            Double.valueOf(0.99),
            Double.valueOf(0.86),
            Double.valueOf(139.44),
            Double.valueOf(1.3156),
            Double.valueOf(1.47)
    };
    
    // Only Admin should be allowed write access
    private HashMap<String, Double> initialRatesMap = new HashMap<>();
    private HashMap<String, File> currencyHist = new HashMap<>();
    private File dir = new File("tmp/test");
    private Data data = new Data();
    private Admin admin = new Admin(data, this);
    private User user = new User(data, this); // assume 1 user per session

    //window control
    private int runningWindows = 2;
    
    /*
        Input FromCountry, ToCountry and the rate and this will return the string with date that should then
        be written to a file.
     */
    public static String currencyFormat(String countryOne, String countryTwo, Double rate){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
        this.data.initialize(this); // initialize data with initial values

        //create new window object
        Window window = new Window(1000, 600, "User", this);
        window.run();

        AdminWindow adminWindow = new AdminWindow(1000, 600, this);
        adminWindow.run();
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

    // ------------ SETTER/GETTER ---------------

    public File getDirectory() {
        return this.dir;
    }

    public HashMap<String, File> getCurrencyHist() {
        return this.currencyHist;
    }

    public Admin getAdminInstance() {
        return this.admin;
    }

    public User getUserInstance() {
        return this.user;
    }

    public Data getDataInstance() {
        return this.data;
    }

    public static String[] getCurrencies() {
        return Syst.initialCurrencies;
    }

    public void closeAWindow() {
        runningWindows --;
        if (runningWindows == 0) {
            System.exit(0);
        }
    }
}
