package at.kalaunerritter;

import at.kalaunerritter.rueckwaertssalto.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MainTest {


    @Test
    public void testParseArgs1() throws Exception {
        String[] args = { };
        assertEquals(false, Main.parseArgs(args));
    }

    @Test
    public void testParseArgs2() throws Exception {
        String[] args = { "-d", "Datenbank" };
        assertEquals(false, Main.parseArgs(args));
    }


    @Test
    public void testParseArgs5() throws Exception {
        String[] args = { "-d", "Datenbank", "-T", "Tabelle" };
        assertEquals(false, Main.parseArgs(args));
    }

    @Test
    public void testParseArgs6() throws Exception {
        String[] args = { "-d", "Datenbank", "-f", "*" };
        assertEquals(false, Main.parseArgs(args));
    }


    @Test
    public void testParseArgs8() throws Exception {
        String[] args = { "bla bla" };
        assertEquals(false, Main.parseArgs(args));
    }




}