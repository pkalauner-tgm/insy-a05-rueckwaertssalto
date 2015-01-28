package at.kalaunerritter.rueckwaertssalto.test.connection;


import at.kalaunerritter.rueckwaertssalto.test.TestAppender;
import at.kalaunerritter.rueckwaertssalto.Main;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import static org.junit.Assert.assertTrue;

/**
 * Testen des MySQLConnectionCreators.
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class MySQLConnectionCreatorTest {


    private TestAppender testAppender;


    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    /**
     * Appender fuer Log4J initialisieren um den Output analysieren zu koennen.
     */
    @Before
    public void setUp() {
        this.testAppender = new TestAppender();
        Logger.getRootLogger().addAppender(testAppender);
    }

    /**
     * Testen des Verbindungsaufbaus
     */
    @Test
    public void testCreateConnection() {
        String[] args = { "-d", "Datenbank", "-u", "user", "-p", "12345", "-h", "127.0.0.1"};

        Main.parseArgs(args);
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> {
            assertTrue(testAppender.getLog().size() >= 1);
        });


        Main.getConnection().createConnection();

    }

}