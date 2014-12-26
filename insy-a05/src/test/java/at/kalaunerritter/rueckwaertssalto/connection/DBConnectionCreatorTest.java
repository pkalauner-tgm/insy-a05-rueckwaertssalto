package at.kalaunerritter.rueckwaertssalto.connection;

import at.kalaunerritter.rueckwaertssalto.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBConnectionCreatorTest {

    @Test
    public void testHost() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("165.45.3.5", Main.getConnection().getHost());
    }

    @Test
    public void testHostDefault() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("127.0.0.1", Main.getConnection().getHost());
    }

    @Test
    public void testDatabase() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("Datenbank", Main.getConnection().getDatabase());
    }

    @Test
    public void testUser() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("user", Main.getConnection().getUser());
    }

    @Test
    public void testUserDefault() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5" };
        Main.parseArgs(args);
        assertEquals(System.getProperty("user.name"), Main.getConnection().getUser());
    }

    @Test
    public void testPassword() {
        String[] args = { "-d", "Datenbank", "-p", "12345", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("12345", Main.getConnection().getPassword());
    }

    @Test
    public void testPasswordDefault() {
        String[] args = { "-d", "Datenbank", "-h", "165.45.3.5", "-u", "user" };
        Main.parseArgs(args);
        assertEquals("", Main.getConnection().getPassword());
    }

}