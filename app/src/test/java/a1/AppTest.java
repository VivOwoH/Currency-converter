/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package a1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;

class AppTest {

    private Syst system;

    @BeforeEach
    public void setUp() {
        system = new Syst();
        system.systemInit(); // instantiate it before every test
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

    }

    @Test
    void testAdminWriteExistingCountry() {
        system.getAdminInstance().addRate("AUD", "USD", 1.2);
        String string = Data.getInfo(system, "AUD", "USD");
        String[] test = string.split(" ");
        assertEquals(test[0], "AUD");
        assertEquals(test[1], "USD");
        assertEquals(test[2], "1.20");
    }

    @Test
    void testAdminWriteNewCountry() {
        system.getAdminInstance().addRate("ZIM", "USD", 1.2);
        String string = Data.getInfo(system, "ZIM", "USD");
        String[] test = string.split(" ");
        assertEquals(test[0], "ZIM");
        assertEquals(test[1], "USD");
        assertEquals(test[2], "1.20");
    }

    @Test
    void testUserRetrieveCurrency() {
        system.getDataInstance().updateCurrencyTable(system);
        double test_result = system.getUserInstance().convertMoney("USD", "AUD", 1.00);
        assertEquals(1.00 * 1.47, test_result);
    }

    /*
     * WIP
     * 0.0|1.01|1.16|0.01|
        0.99|0.0|1.15|0.01|
        0.86|0.87|0.0|0.01|
        139.44|140.85|162.14|0.0|  -- verify if this is initial popular currency
     */
    // @Test
    // void testUserDisplayPopularCurrencyTable() {
    //     // Initial table
    //     String[][] expectedTable = {
    //                                 {},
    //                                 {},
    //                                 {},
    //                                 {}
    //                             };
    //     assertEquals(expectedTable, system.getUserInstance().displayPopularCurrency(););
    // }
}
