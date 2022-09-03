/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package a1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void testInitClean(){
        Syst system = new Syst();
        system.systemInit();
        File directory=new File("tmp/test");
        int fileCount=directory.list().length;
        assertEquals(fileCount, 6);
        system.systemClean();
        assertNull(directory.list());
    }

    @Test void testDataGetInfoExistingRate(){
        Syst system = new Syst();
        system.systemInit();
        String string = Data.getInfo(system, "AUD", "USD");
        String[] test = string.split(" ");
        assertEquals(test[0], "AUD");
        assertEquals(test[1], "USD");
    }

    @Test void testDataGetInfoNewRate(){

    }

    @Test void testAdminWriteExistingCountry(){
        Syst system = new Syst();
        system.systemInit();
        system.admin.addRate("AUD", "USD", 1.2);
        String string = Data.getInfo(system, "AUD", "USD");
        String[] test = string.split(" ");
        assertEquals(test[0], "AUD");
        assertEquals(test[1], "USD");
        assertEquals(test[2], "1.20");
    }

    @Test void testAdminWriteNewCountry(){
        Syst system = new Syst();
        system.systemInit();
        system.admin.addRate("ZIM", "USD", 1.2);
        String string = Data.getInfo(system, "ZIM", "USD");
        String[] test = string.split(" ");
        assertEquals(test[0], "ZIM");
        assertEquals(test[1], "USD");
        assertEquals(test[2], "1.20");
    }
}
