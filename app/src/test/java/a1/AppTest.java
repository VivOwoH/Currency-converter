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
}
