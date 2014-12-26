package at.kalaunerritter.rueckwaertssalto.connection;


import at.kalaunerritter.rueckwaertssalto.TestAppender;
import at.kalaunerritter.rueckwaertssalto.Main;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class MySQLConnectionCreatorTest {


    private TestAppender testAppender;

    private static final Logger LOG = LogManager.getLogger(MySQLConnectionCreatorTest.class);


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

    @Test
    public void testCreateConnection() throws SQLException {
        String[] args = { "-d", "Datenbank", "-u", "user", "-p", "12345", "-h", "127.0.0.1"};

        Main.parseArgs(args);
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> {
            assertTrue(testAppender.getLog().size() >= 1);
        });


        Main.getConnection().createConnection();




    }

}