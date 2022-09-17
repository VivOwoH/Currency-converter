package a1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

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

    //-----------------------------------------
    @Test
    void testUserConvertMoney() {
        system.getDataInstance().updateCurrencyTable(system);
        // on UI (included in popular table)
        double test_result_1 = system.getUserInstance().convertMoney("JPY", "GBP", 1.00);
        assertEquals(1.00 * 0.01, test_result_1);

        // internal data (included in currency table only)
        double test_result_2 = system.getUserInstance().convertMoney("USD", "AUD", 1.00);
        assertEquals(1.00 * 1.47, test_result_2);
    }

    @Test 
    void testUserCompareRate() {
        // USD - EUR: initial 0.99

        // test no previous data
        assertTrue(-2 == system.getUserInstance().compareRate(0.99, "USD", "EUR"));
        
        // test no change
        system.getAdminInstance().addRate("USD", "EUR", 0.99);
        assertTrue(0 == system.getUserInstance().compareRate(0.99, "USD", "EUR"));
        
        // test increase
        system.getAdminInstance().addRate("USD", "EUR", 5.0);
        assertTrue(1 == system.getUserInstance().compareRate(5.0, "USD", "EUR"));
        
        // test decrease
        system.getAdminInstance().addRate("USD", "EUR", 3.0);
        assertTrue(-1 == system.getUserInstance().compareRate(3.0, "USD", "EUR"));
        
    }

    /*
     * 0.0|1.01|1.16|0.01|
        0.99|0.0|1.15|0.01|
        0.86|0.87|0.0|0.01|
        139.44|140.85|162.14|0.0|  -- initial popular currency
     */
    @Test
    void testUserDisplayPopularCurrencyTable() {
        // Initial table
        String[][] expectedTable = {
                                    {"0.0", "1.01", "1.16", "0.01"},
                                    {"0.99", "0.0", "1.15", "0.01"},
                                    {"0.86", "0.87", "0.0", "0.01"},
                                    {"139.44", "140.85", "162.14", "0.0"}
                                };
        String[][] actualTable = system.getUserInstance().displayPopularCurrency();
        for (int i = 0; i < actualTable.length; i++) {
            for (int j = 0; j < actualTable[0].length; j++) {
                assertEquals(expectedTable[i][j], actualTable[i][j]);
            }
        }

        // After changing rate (i.e. showing I/D)

    }
}
