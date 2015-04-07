package com.jenkov.cliargs;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jjenkov on 07-04-2015.
 */
public class CliArgsTest {

    @Test
    public void testValueMethods() {
        String[] theArgs = {"firstTarget", "-ha", "midTarget", "-conf", "file1.txt", "file2.txt", "-port", "122",  "lastTarget1", "lastTarget2", "-bla"};

        CliArgs cliArgs = new CliArgs(theArgs);

        boolean  ha    = cliArgs.switchPresent("-ha");
        assertTrue(ha);

        String[] conf  = cliArgs.switchValues("-conf");
        assertEquals(2, conf.length);
        assertEquals("file1.txt", conf[0]);
        assertEquals("file2.txt", conf[1]);

        boolean port = cliArgs.switchPresent("-port");
        assertTrue(port);

        String portNo = cliArgs.switchValue("-port");
        assertEquals("122", portNo);

        String defaultValue = "1234";
        String portNo2 = cliArgs.switchValue("-port2", defaultValue);
        assertEquals(defaultValue, portNo2);

        boolean bla = cliArgs.switchPresent("-bla");
        assertTrue(bla);


        String[] targets = cliArgs.targets();
        assertEquals(4, targets.length);
        assertEquals("firstTarget", targets[0]);
        assertEquals("midTarget"  , targets[1]);
        assertEquals("lastTarget1", targets[2]);
        assertEquals("lastTarget2", targets[3]);
    }

    @Test
    public void testSwitchPojo() {
        String[] theArgs = {"firstTarget", "-ha", "midTarget", "-conf", "file1.txt", "file2.txt", "-port", "122",  "lastTarget1", "lastTarget2", "-bla"};

        CliArgs cliArgs = new CliArgs(theArgs);

        CliOptions switches = cliArgs.switchPojo(CliOptions.class);
        assertEquals(true, switches.ha);
        assertEquals(122, switches.port);
        assertEquals(123, switches.port2); //default value - set in CliOptions class directly
        assertEquals(true, cliArgs.switchPresent("-bla"));

        assertEquals(2, switches.conf.length);
        assertEquals("file1.txt", switches.conf[0]);
        assertEquals("file2.txt", switches.conf[1]);


        String[] targets = cliArgs.targets();
        assertEquals(4, targets.length);
        assertEquals("firstTarget", targets[0]);
        assertEquals("midTarget"  , targets[1]);
        assertEquals("lastTarget1", targets[2]);
        assertEquals("lastTarget2", targets[3]);


    }
}
