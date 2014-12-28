package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.rueckwaertssalto.attributes.Attribute;
import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Testcases for {@code Table}
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141226.1
 */
public class TableTest {

    /**
     * Tests the {@code getTablename()} method
     */
    @Test
    public void testTableName() {
        Table t = new Table("table");
        assertEquals(t.getTablename(), "table");
    }

    /**
     * Tries to add a new attribute to a table
     */
    @Test
    public void testAddAttributes() {
        Table t = new Table("table1");
        t.addAttribute(new Attribute("attribute1"));
        t.addAttribute(new Attribute("attribute2"));
        assertEquals("[attribute1, attribute2]", t.getAttributes().toString());
    }

    /**
     * Tries to add multiple attributes at once
     */
    @Test
    public void testAddMultipleAttributes() {
        Table t = new Table("table1");
        List<BaseAttribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute("attribute1"));
        attributeList.add(new Attribute("attribute2"));
        t.addAttributes(attributeList);
        assertEquals("[attribute1, attribute2]", t.getAttributes().toString());
    }
}