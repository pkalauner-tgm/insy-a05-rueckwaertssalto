package at.kalaunerritter.rueckwaertssalto;

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
        assertEquals(true, Main.parseArgs(args));
    }

    @Test
    public void testParseArgs3() throws Exception {
        String[] args = { "bla bla" };
        assertEquals(false, Main.parseArgs(args));
    }




}