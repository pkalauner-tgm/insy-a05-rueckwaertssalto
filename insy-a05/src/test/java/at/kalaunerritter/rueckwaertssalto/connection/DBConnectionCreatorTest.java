package at.kalaunerritter.rueckwaertssalto.connection;

import at.kalaunerritter.rueckwaertssalto.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testen des DBConnectionCreators bzw. ob die eingegebenen Argumente richtig gesetzt werden
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class DBConnectionCreatorTest {

    /**
     * Testen, ob bei Angabe des Hosts dieser korrekt uebernommen wird
     */
    @Test
    public void testHost() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("165.45.3.5", Main.getConnection().getHost());
    }

    /**
     * Testen, ob ohne Angabe des Hosts der richtige Default-Wert uebernommen wird
     */
    @Test
    public void testHostDefault() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("127.0.0.1", Main.getConnection().getHost());
    }

    /**
     * Testen, ob bei Angabe der DB dieses korrekt uebernommen wird
     */
    @Test
    public void testDatabase() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("Datenbank", Main.getConnection().getDatabase());
    }

    /**
     * Testen, ob bei Angabe des Users dieser korrekt uebernommen wird
     */
    @Test
    public void testUser() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("user", Main.getConnection().getUser());
    }

    /**
     * Testen, ob ohne Angabe des Users der richtige Default-Wert uebernommen wird
     */
    @Test
    public void testUserDefault() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5" };
        Main.parseArgs(args);
        assertEquals(System.getProperty("user.name"), Main.getConnection().getUser());
    }

    /**
     * Testen, ob bei Angabe des Passworts dieses korrekt uebernommen wird
     */
    @Test
    public void testPassword() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("12345", Main.getConnection().getPassword());
    }

    /**
     * Testen, ob ohne Angabe des Passworts der richtige Default-Wert uebernommen wird
     */
    @Test
    public void testPasswordDefault() {
        String[] args = { "-d", "Datenbank", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("", Main.getConnection().getPassword());
    }

}