package a1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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