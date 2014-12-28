package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.rueckwaertssalto.attributes.Attribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests the FileWriter
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141226.1
 */
public class MyFileWriterTest {
    private File file;

    /**
     * Initializes the file before every testcase
     */
    @Before
    public void setUp() {
        this.file = new File("rm.html");
    }

    /**
     * Delets the file after each testcase
     */
    @After
    public void tearDown() {
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }

    /**
     * Tries to write one table to a file
     *
     * @throws IOException should not happen
     */
    @Test
    public void testOneTable() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new Attribute("attribute1"));
        t.addAttribute(new Attribute("attribute2"));
        list.add(t);

        MyFileWriter.writeRmToFile(list);

        BufferedReader br = new BufferedReader(new FileReader(file));
        assertEquals("table1: attribute1, attribute2<br>", br.readLine());
    }

    /**
     * Tries to write two tables to a file
     *
     * @throws IOException should not happen
     */
    @Test
    public void testTwoTables() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new Attribute("attribute1"));
        t.addAttribute(new Attribute("attribute2"));
        list.add(t);

        t = new Table("table2");
        t.addAttribute(new Attribute("attribute1"));
        t.addAttribute(new Attribute("attribute2"));
        list.add(t);

        MyFileWriter.writeRmToFile(list);

        BufferedReader br = new BufferedReader(new FileReader(file));
        assertEquals("table1: attribute1, attribute2<br>", br.readLine());
        assertEquals("table2: attribute1, attribute2<br>", br.readLine());
    }

    /**
     * Tries to write one table with one attribute to a file
     *
     * @throws IOException should not happen
     */
    @Test
    public void testOneTableOneAttribute() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new Attribute("attribute1"));
        list.add(t);

        MyFileWriter.writeRmToFile(list);

        BufferedReader br = new BufferedReader(new FileReader(file));
        assertEquals("table1: attribute1<br>", br.readLine());
    }
}