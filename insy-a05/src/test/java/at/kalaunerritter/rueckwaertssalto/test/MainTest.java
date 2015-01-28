package at.kalaunerritter.rueckwaertssalto.test;

import at.kalaunerritter.rueckwaertssalto.Main;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testen der Kommandoverarbeitung in der Klasse Main
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class MainTest {

    /**
     * Testen der Verarbeitung der Kommandozeilenargumente: Keine Eingabe
     */
    @Test
    public void testParseArgs1() {
        String[] args = { };
        Assert.assertEquals(false, Main.parseArgs(args));
    }

    /**
     * Testen der Verarbeitung der Kommandozeilenargumente: Eingabe von falschen Argumenten
     */
    @Test
    public void testParseArgs2() {
        String[] args = { "bla bla" };
        assertEquals(false, Main.parseArgs(args));
    }

    /**
     * Testen der Verarbeitung der Kommandozeilenargumente: Eingabe von der DB
     */
    @Test
    public void testParseArgs3() {
        String[] args = { "-d", "Datenbank" };
        assertEquals(true, Main.parseArgs(args));
    }

    /**
     * Testen der Verarbeitung der Kommandozeilenargumente: Eingabe von der DB und vom Hostname
     */
    @Test
    public void testParseArgs4() {
        String[] args = { "-d", "Datenbank", "-h", "127.0.0.1" };
        assertEquals(true, Main.parseArgs(args));
    }

    /**
     * Testen der Verarbeitung der Kommandozeilenargumente: Eingabe von der DB, vom Hostnamen und vom User
     */
    @Test
    public void testParseArgs5() {
        String[] args = { "-d", "Datenbank", "-h", "127.0.0.1", "-u", "user1" };
        assertEquals(true, Main.parseArgs(args));
    }

    /**
     * Testen der Verarbeitung der Kommandozeilenargumente: Eingabe von der DB, vom Hostname, vom User und vom Passwort
     */
    @Test
    public void testParseArgs6() {
        String[] args = { "-d", "Datenbank", "-h", "127.0.0.1", "-u", "user1", "-p", "12345" };
        assertEquals(true, Main.parseArgs(args));
    }

}