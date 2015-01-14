package at.kalaunerritter.rueckwaertssalto.attributes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testen des Decorators fuer die Datenbank-Attribute
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class AttributeDecoratorTest {

    /**
     * Testen, ob ein einfaches Datenbank-Attribut richtig als HTML-String ausgegeben wird
     */
    @Test
    public void testBaseAttribute() {
        BaseAttribute a = new Attribute("Test");
        assertEquals("Test", a.getHTMLValue());
    }

    /**
     * Testen, ob ein als Primary Key dekoriertes Datenbank-Attribut richtig als HTML-String ausgegeben wird
     */
    @Test
    public void testBaseAttributeWithPrimaryKey() {
        BaseAttribute a = new PrimaryKey(new Attribute("Test"));
        assertEquals("<u>Test</u>", a.getHTMLValue());
    }


    /**
     * Testen, ob ein als Foreign Key dekoriertes Datenbank-Attribut richtig als HTML-String ausgegeben wird
     */
    @Test
    public void testBaseAttributeWithForeignKey() {
        BaseAttribute a = new ForeignKey("Table", "Attribute", new Attribute("Test"));
        assertEquals("<i>Test: Table.Attribute</i>", a.getHTMLValue());
    }

    /**
     * Testen, ob ein als Foreign Key dekoriertes Datenbank-Attribut richtig als HTML-String ausgegeben wird
     */
    @Test
    public void testBaseAttributeWithForeignKey2() {
        BaseAttribute a = new ForeignKey("Table", "Test", new Attribute("Test"));
        assertEquals("<i>Table.Test</i>", a.getHTMLValue());
    }


    /**
     * Testen, ob ein als Primary Key und Foreign Key dekoriertes Datenbank-Attribut richtig als HTML-String ausgegeben wird
     */
    @Test
    public void testBaseAttributeWithForeignKeyPrimaryKey() {
        BaseAttribute a = new ForeignKey("Table", "Attribute", new PrimaryKey(new Attribute("Test")));
        assertEquals("<i><u>Test: Table.Attribute</u></i>", a.getHTMLValue());
    }


    /**
     * Testen, ob ein als Primary Key und Foreign Key dekoriertes Datenbank-Attribut richtig als HTML-String ausgegeben wird
     */
    @Test
    public void testBaseAttributeWithForeignKeyPrimaryKey2() {
        BaseAttribute a = new PrimaryKey(new ForeignKey("Table", "Attribute", new Attribute("Test")));
        assertEquals("<u><i>Test: Table.Attribute</i></u>", a.getHTMLValue());
    }

    /**
     * Testen, ob ein als Unique gekennzeichnetes Attribut richtig ausgegeben wird
     */
    @Test
    public void testBaseAttributeWithUnique() {
        BaseAttribute a = new Unique(new Attribute("Test"));
        assertEquals("Test &lt;UNIQUE&gt;", a.getHTMLValue());
    }

}