package at.kalaunerritter.rueckwaertssalto.test;

import at.kalaunerritter.rueckwaertssalto.MyFileWriter;
import at.kalaunerritter.rueckwaertssalto.attributes.*;
import at.kalaunerritter.rueckwaertssalto.dbloader.Table;
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
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class MyFileWriterTest {
    private File rmFile, erdFile;

    /**
     * Initializes the rmFile before every testcase
     */
    @Before
    public void setUp() {
        this.rmFile = new File("rm.html");
        this.erdFile = new File("erd.dot");
    }

    /**
     * Delets the rmFile after each testcase
     */
    @After
    public void tearDown() {
        if (rmFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            rmFile.delete();
        }
        if (erdFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            erdFile.delete();
        }
        File erdPNG = new File("erd.png");
        if (erdPNG.exists()) {
            //noinspection ResultOfMethodCallIgnored
            erdPNG.delete();
        }
    }

    /**
     * Tries to write one table to a rmFile
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

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeRMToFile();

        BufferedReader br = new BufferedReader(new FileReader(rmFile));
        assertEquals("table1 (attribute1, attribute2)<br>", br.readLine());
    }

    /**
     * Tries to write two tables to a rmFile
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

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeRMToFile();

        BufferedReader br = new BufferedReader(new FileReader(rmFile));
        assertEquals("table1 (attribute1, attribute2)<br>", br.readLine());
        assertEquals("table2 (attribute1, attribute2)<br>", br.readLine());
    }

    /**
     * Tries to write one table with one attribute to a rmFile
     *
     * @throws IOException should not happen
     */
    @Test
    public void testOneTableOneAttribute() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new Attribute("attribute1"));
        list.add(t);

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeRMToFile();

        BufferedReader br = new BufferedReader(new FileReader(rmFile));
        assertEquals("table1 (attribute1)<br>", br.readLine());
    }

    /**
     * Tries to create a ERD of one table with one attribute
     *
     * @throws IOException should not happen
     */
    @Test
    public void testERDOneTableOneAttribute() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new PrimaryKey(new Attribute("attribute1")));
        list.add(t);

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeERDToFile();

        BufferedReader br = new BufferedReader(new FileReader(erdFile));
        assertEquals("graph ERD {", br.readLine());
        assertEquals("node [shape=box]; table1; ", br.readLine());
        assertEquals("node [shape=ellipse]; {node [label=<<u>attribute1</u>>] attribute10;};", br.readLine());
        assertEquals("node [shape=diamond]; ", br.readLine());
        assertEquals("attribute10 -- table1;", br.readLine());
        assertEquals("}", br.readLine());

    }

    /**
     * Tries to create a ERD of two tables and one foreign key
     *
     * @throws IOException should not happen
     */
    @Test
    public void testERDTwoTablesOneForeignKey() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new ForeignKey("table2", "attribute1", new PrimaryKey(new Attribute("attribute1"))));
        list.add(t);

        Table t2 = new Table("table2");
        t2.addAttribute(new PrimaryKey(new Attribute("attribute1")));
        list.add(t2);

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeERDToFile();

        BufferedReader br = new BufferedReader(new FileReader(erdFile));

        assertEquals("graph ERD {", br.readLine());
        assertEquals("node [shape=box]; table1 [peripheries=2]; table2; ", br.readLine());
        assertEquals("node [shape=ellipse]; {node [label=<<u>attribute1</u>>] attribute10;};", br.readLine());
        assertEquals("node [shape=diamond]; \"table1-table2\" [peripheries=2];", br.readLine());
        assertEquals("attribute10 -- table2;", br.readLine());
        assertEquals("table2 -- \"table1-table2\" [label=\"1\",len=1.00];", br.readLine());
        assertEquals("\"table1-table2\" -- table1 [label=\"1\",len=1.00,color=\"black:white:black\"];", br.readLine());
        assertEquals("}", br.readLine());
    }

    /**
     * Tries to create a ERD of two tables and one foreign key
     *
     * @throws IOException should not happen
     */
    @Test
    public void testERDTwoTablesOneForeignKey2() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new ForeignKey("table2", "attribute1", new Attribute("attribute1")));
        list.add(t);

        Table t2 = new Table("table2");
        t2.addAttribute(new PrimaryKey(new Attribute("attribute1")));
        list.add(t2);

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeERDToFile();

        BufferedReader br = new BufferedReader(new FileReader(erdFile));

        assertEquals("graph ERD {", br.readLine());
        assertEquals("node [shape=box]; table1; table2; ", br.readLine());
        assertEquals("node [shape=ellipse]; {node [label=<<u>attribute1</u>>] attribute10;};", br.readLine());
        assertEquals("node [shape=diamond]; \"table1-table2\";", br.readLine());
        assertEquals("attribute10 -- table2;", br.readLine());
        assertEquals("table2 -- \"table1-table2\" [label=\"1\",len=1.00];", br.readLine());
        assertEquals("\"table1-table2\" -- table1 [label=\"n\",len=1.00];", br.readLine());
        assertEquals("}", br.readLine());
    }

    /**
     * Tries to create a ERD of one table and two primary keys
     *
     * @throws IOException should not happen
     */
    @Test
    public void testERDOneTableTwoPrimaryKeys() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new PrimaryKey(new Attribute("attribute1")));
        t.addAttribute(new PrimaryKey(new Attribute("attribute2")));
        t.addAttribute(new Attribute("attribute2"));
        list.add(t);

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeERDToFile();

        BufferedReader br = new BufferedReader(new FileReader(erdFile));

        assertEquals("graph ERD {", br.readLine());
        assertEquals("node [shape=box]; table1; ", br.readLine());
        assertEquals("node [shape=ellipse]; {node [label=<<u>attribute1</u>>] attribute10;};{node [label=<<u>attribute2</u>>] attribute21;};{node [label=<attribute2>] attribute22;};", br.readLine());
        assertEquals("node [shape=diamond]; ", br.readLine());
        assertEquals("attribute10 -- table1;", br.readLine());
        assertEquals("attribute21 -- table1;", br.readLine());
        assertEquals("attribute22 -- table1;", br.readLine());
        assertEquals("}", br.readLine());

    }

    @Test
    public void testERDUniqueAndNotNull() throws IOException {
        List<Table> list = new ArrayList<>();
        Table t = new Table("table1");
        t.addAttribute(new PrimaryKey(new Attribute("pk")));
        t.addAttribute(new Unique(new Attribute("uniqueAttribute")));
        t.addAttribute(new NotNull(new Attribute("NotNullAttribute")));
        list.add(t);

        MyFileWriter fw = new MyFileWriter(list);
        fw.writeERDToFile();

        BufferedReader br = new BufferedReader(new FileReader(erdFile));

        assertEquals("graph ERD {", br.readLine());
        assertEquals("node [shape=box]; table1; ", br.readLine());
        assertEquals("node [shape=ellipse]; {node [label=<<u>pk</u>>] pk0;};{node [label=<uniqueAttribute &lt;UNIQUE&gt;>] uniqueAttribute1;};{node [label=<NotNullAttribute &lt;NOT NULL&gt;>] NotNullAttribute2;};", br.readLine());
        assertEquals("node [shape=diamond]; ", br.readLine());
        assertEquals("pk0 -- table1;", br.readLine());
        assertEquals("uniqueAttribute1 -- table1;", br.readLine());
        assertEquals("NotNullAttribute2 -- table1;", br.readLine());
        assertEquals("}", br.readLine());
    }

}