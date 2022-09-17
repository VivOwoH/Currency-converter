package a1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataTest {

    private Syst system;

    @BeforeEach
    public void setUp() {
        system = new Syst();
        system.systemInit(); // instantiate it before every test
    }

    @AfterEach
    public void clean() {
        system.systemClean();
    }

    // ---------------------------------------------
     /**
      * USD    EUR    GBP     JPY  CAD     AUD
      * 0.0   |1.01  |1.16  |0.01| 0.76  | 0.68
        0.99  |0.0   |1.15  |0.01| 0.75  | 0.67
        0.86  |0.87  |0.0   |0.01| 0.65  | 0.59
        139.44|140.85|162.14|0.0 | 105.99| 94.86
        1.32  |1.33  |1.53  |0.01| 0.0   | 0.89
        1.47  |1.48  |1.71  |0.01| 1.12  | 0.0  -- verify initial currency
     */
    @Test
    void testInitialData() {
        system.getDataInstance().initialize(system);
        // initial currency table
        double[][] expectedTable = {
            {0.0, 1.01, 1.16, 0.01, 0.76, 0.68},
            {0.99, 0.0, 1.15, 0.01, 0.75, 0.67},
            {0.86, 0.87, 0.0, 0.01, 0.65, 0.59},
            {139.44, 140.85, 162.14, 0.0, 105.99, 94.86},
            {1.32, 1.33, 1.53, 0.01, 0.0, 0.89},
            {1.47, 1.48, 1.71, 0.01, 1.12, 0.0}
        };
        double[][] actualTable = system.getDataInstance().getCurrencyTable();
        for (int i = 0; i < actualTable.length; i++) {
            for (int j = 0; j < actualTable[0].length; j++) {
                assertEquals(expectedTable[i][j], actualTable[i][j]);
            }
        }

        // initial popular currency table
        double[][] expectedTable2 = {
            {0.0, 1.01, 1.16, 0.01},
            {0.99, 0.0, 1.15, 0.01},
            {0.86, 0.87, 0.0, 0.01},
            {139.44, 140.85, 162.14, 0.0}
        };
        double[][] actualTable2 = system.getDataInstance().getPopularCurrencyTable();
        for (int i = 0; i < actualTable2.length; i++) {
            for (int j = 0; j < actualTable2[0].length; j++) {
                assertEquals(expectedTable2[i][j], actualTable2[i][j]);
            }
        }
    }

    @Test
    void testUpdateTwoCurrencyTable() {
        // admin add rate auto update currency table
        // update currency table auto update popular currency table
        system.getAdminInstance().addRate("ZIM", "USD", 1.2);
        double[][] expectedTable = {
            {0.0, 1.01, 1.16, 0.01, 0.76, 0.68, 1.2},
            {0.99, 0.0, 1.15, 0.01, 0.75, 0.67, 0.0},
            {0.86, 0.87, 0.0, 0.01, 0.65, 0.59, 0.0},
            {139.44, 140.85, 162.14, 0.0, 105.99, 94.86, 0.0},
            {1.32, 1.33, 1.53, 0.01, 0.0, 0.89, 0.0},
            {1.47, 1.48, 1.71, 0.01, 1.12, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
        };
        double[][] actualTable = system.getDataInstance().getCurrencyTable();
        for (int i = 0; i < actualTable.length; i++) {
            for (int j = 0; j < actualTable[0].length; j++) {
                assertEquals(expectedTable[i][j], actualTable[i][j]);
            }
        }

        // overwrite existing rate in popular currency table
        system.getAdminInstance().addRate("EUR", "USD", 2.5);
        double[][] expectedPopularTable = {
            {0.0, 2.5, 1.16, 0.01},
            {0.99, 0.0, 1.15, 0.01},
            {0.86, 0.87, 0.0, 0.01},
            {139.44, 140.85, 162.14, 0.0}
        };
        double[][] actualPopularTable = system.getDataInstance().getPopularCurrencyTable();
        for (int i = 0; i < actualPopularTable.length; i++) {
            for (int j = 0; j < actualPopularTable[0].length; j++) {
                assertEquals(expectedPopularTable[i][j], actualPopularTable[i][j]);
            }
        }
    }

    @Test
    void testAddCountryToIdx() {
        Data data = system.getDataInstance();
        int nextIdx = data.getCountryIdx().size();
        data.addCountryToIdx("ZIM");

        assertTrue(data.getCountryIdx().get(nextIdx)=="ZIM");
    }

    @Test
    void testAddRemovePopularCountry() {
        Data data = system.getDataInstance();

        assertTrue(data.getPopularCountryIdx().containsValue("USD"));
        assertTrue(data.getPopularCountryIdx().containsValue("EUR"));
        assertTrue(data.getPopularCountryIdx().containsValue("GBP"));
        assertTrue(data.getPopularCountryIdx().containsValue("JPY"));

        // already have 4 popular countries, reject change
        data.addPopularCountry("ZIM"); // attempt to add
        assertFalse(data.getPopularCountryIdx().containsValue("ZIM"));

        // can add more popular country
        assertTrue(data.getPopularCountryIdx().size()==4);
        data.removePopularCountry("EUR"); // remove value at idx=1
        assertNull(data.getPopularCountryIdx().get(3)); // last entry null
        
        // add a popular country that is not in database -> reject change
        data.addPopularCountry("ZIM");
        assertNull(data.getPopularCountryIdx().get(3)); // last entry still null
        
        system.getAdminInstance().addRate("ZIM", "USD", 1.2);
        data.addPopularCountry("ZIM");
        assertEquals("ZIM", data.getPopularCountryIdx().get(3));
    }

    @Test
    void testFindCurrencyInTable() {

    }

    /*
     * This test also covers qualityCheck() and inRange()
     * We require the system date of which the test runs for this test
     * We are only testing init date and time
     */
    @Test
    void testGetSummary() {    
        // get current date
        Date d = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String[] dateParts = dateFormat.format(d).split(" ");
        String currentDate = dateParts[0];
        String currentTime = dateParts[1];

        String[] splitDate = dateParts[0].split("-");
        Integer currentYr = Integer.valueOf(splitDate[0]);
        Integer currentMonth = Integer.valueOf(splitDate[1]);
        Integer currentDay = Integer.valueOf(splitDate[2]);

        // local variable for testing
        String topDate;
        String bottomDate;
        String result;
        String expectedSummary = "CONVERSION HISTORY:\n" +
                                "AUD USD 0.68 " + 
                                String.format("%s %s\n", currentDate, currentTime) +
                                "AVERAGE: 0.68" +
                                "\nMEDIAN: 0.68" +
                                "\nMAXIMUM: 0.68" +
                                "\nMINIMUM: 0.68" +
                                "\nSTANDARD DEVIATION: 0.0";

        /* 
         * -----------------------------------------------------------------
         * date provided in wrong format (i.e. not passing qualityCheck())
         * -----------------------------------------------------------------
         */
        assertNull(Data.getSummary(system, "AUD", "USD", 
                        "2022/9/1", "2022-9-10"));
        assertNull(Data.getSummary(system, "AUD", "USD", 
                        "2022-9-1", "22-09-10"));

        /*
         * -----------------------------------------------------------------
         * given years are out of bounds
         * -----------------------------------------------------------------
         */
        
        // topDate out of bounds
        topDate = String.format("%d-%d-%d", currentYr-1, currentMonth, currentDay);
        bottomDate = String.format("%d-%d-%d", currentYr-2, currentMonth, currentDay);
        assertNull(Data.getSummary(system, "AUD", "USD", 
                        topDate, bottomDate));

        // bottomDate out of bounds
        topDate = String.format("%d-%d-%d", currentYr+2, currentMonth, currentDay);
        bottomDate = String.format("%d-%d-%d", currentYr+1, currentMonth, currentDay);
        assertNull(Data.getSummary(system, "AUD", "USD", 
                        topDate, bottomDate));

        /*
         * -----------------------------------------------------------------
         * given years in bounds
         * -----------------------------------------------------------------
         */
        // currentYr in range -> true
        result = Data.getSummary(system, "AUD", "USD", 
                    currentDate, currentDate);
        assertEquals(expectedSummary, result);

        // Yr is the same, but months in range -> true
        topDate = String.format("%d-%d-%d", currentYr, currentMonth+1, currentDay);
        bottomDate = String.format("%d-%d-%d", currentYr, currentMonth-1, currentDay);
        result = Data.getSummary(system, "AUD", "USD", 
                    topDate, bottomDate);
        assertEquals(expectedSummary, result);

        // Yr is the same, but months NOT in range -> false
        // topDate not in range
        topDate = String.format("%d-%d-%d", currentYr, currentMonth-1, currentDay);
        bottomDate = String.format("%d-%d-%d", currentYr, currentMonth-2, currentDay);
        assertNull(Data.getSummary(system, "AUD", "USD", 
                    topDate, bottomDate));
        // bottomDate not in range
        topDate = String.format("%d-%d-%d", currentYr, currentMonth+1, currentDay);
        bottomDate = String.format("%d-%d-%d", currentYr, currentMonth+2, currentDay);
        assertNull(Data.getSummary(system, "AUD", "USD", 
                    topDate, bottomDate));

        // Yr is the same, month is the same, but day in range -> true
        topDate = String.format("%d-%d-%d", currentYr, currentMonth, currentDay+1);
        bottomDate = String.format("%d-%d-%d", currentYr, currentMonth, currentDay-1);
        result = Data.getSummary(system, "AUD", "USD", 
                    topDate, bottomDate);
        assertEquals(expectedSummary, result);

        // Yr is the same, month is the same, but day NOT in range -> false
        // topDate not in range
        topDate = String.format("%d-%d-%d", currentYr, currentMonth, currentDay-1);
        bottomDate = String.format("%d-%d-%d", currentYr, currentMonth, currentDay-2);
        assertNull(Data.getSummary(system, "AUD", "USD", 
                    topDate, bottomDate));
        // bottomDate not in range
        topDate = String.format("%d-%d-%d", currentYr, currentMonth+1, currentDay+1);
        bottomDate = String.format("%d-%d-%d", currentYr, currentMonth+2, currentDay+2);
        assertNull(Data.getSummary(system, "AUD", "USD", 
                    topDate, bottomDate));
    }


    @Test
    void testDataGetInfoExistingRate() {
        String string = Data.getInfo(system, "AUD", "USD");
        String[] test = string.split(" ");
        assertEquals(test[0], "AUD");
        assertEquals(test[1], "USD");
    }

    @Test
    void testDataGetInfoNewRate() {
        system.getAdminInstance().addRate("ZIM", "USD", 1.2);
        String string = Data.getInfo(system, "ZIM", "USD");
        String[] test = string.split(" ");
        assertEquals(test[0], "ZIM");
        assertEquals(test[1], "USD");
    }

}
