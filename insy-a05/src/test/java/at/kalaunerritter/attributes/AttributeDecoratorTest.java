package at.kalaunerritter.attributes;

import org.junit.Test;

import static org.junit.Assert.*;

public class AttributeDecoratorTest {

    @Test
    public void testBaseAttribute() {
        BaseAttribute a = new Attribute("Test");
        assertEquals("Test", a.getHTMLValue());
    }

    @Test
    public void testBaseAttributeWithPrimaryKey() {
        BaseAttribute a = new PrimaryKey(new Attribute("Test"));
        assertEquals("<u>Test</u>", a.getHTMLValue());
    }

    @Test
    public void testBaseAttributeWithForeignKey() {
        BaseAttribute a = new ForeignKey("Table", "Attribute", new Attribute("Test"));
        assertEquals("<i>Test: Table.Attribute</i>", a.getHTMLValue());
    }

    @Test
    public void testBaseAttributeWithForeignKey2() {
        BaseAttribute a = new ForeignKey("Table", "Test", new Attribute("Test"));
        assertEquals("<i>Table.Test</i>", a.getHTMLValue());
    }

    @Test
    public void testBaseAttributeWithForeignKeyPrimaryKey() {
        BaseAttribute a = new ForeignKey("Table", "Attribute", new PrimaryKey(new Attribute("Test")));
        assertEquals("<i><u>Test: Table.Attribute</u></i>", a.getHTMLValue());
    }

    @Test
    public void testBaseAttributeWithForeignKeyPrimaryKey2() {
        BaseAttribute a = new PrimaryKey(new ForeignKey("Table", "Attribute", new Attribute("Test")));
        assertEquals("<u><i>Test: Table.Attribute</i></u>", a.getHTMLValue());
    }

}