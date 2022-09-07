/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package a1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class App {
    
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

    public static void main(String[] args) {
        Syst system = new Syst();
        system.systemInit();
        system.systemDraw();
        //test summary
        system.getAdminInstance().addRate("USD", "AUD", 1.6);
        System.out.println(system.getDataInstance().getSummary(system, "USD", "AUD",
                "2022-09-06", "2022-09-05"));
        system.systemClean();

        // test Data
//        Data test = new Data();
//        // test update currency, should be called from admin class
//        test.setCurrency("EUR", "USD", 0.99);
//        test.setCurrency("USD", "EUR", 1.01);
//        test.updatePopularCurrencyTable();
//
//        User u1 = new User(test);
//        u1.displayPopularCurrency(); // no popular currency table yet
//        System.out.println(u1); // test currency table
    }
}
