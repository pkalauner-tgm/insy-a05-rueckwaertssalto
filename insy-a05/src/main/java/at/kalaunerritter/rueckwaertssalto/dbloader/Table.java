package at.kalaunerritter.rueckwaertssalto.dbloader;

import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
import at.kalaunerritter.rueckwaertssalto.attributes.ForeignKey;
import at.kalaunerritter.rueckwaertssalto.attributes.PrimaryKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a table of a database
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141226.1
 */
public class Table {
    private String tablename;
    private List<BaseAttribute> attributes;

    /**
     * Initializes the table with the given name
     *
     * @param tablename name of the table
     */
    public Table(String tablename) {
        this.attributes = new ArrayList<>();
        this.tablename = tablename;
    }

    /**
     * Adds a new attribute to the table
     *
     * @param attribute attribute to add
     */
    public void addAttribute(BaseAttribute attribute) {
        this.attributes.add(attribute);
    }

    /**
     * adds multiple attributes to the table
     *
     * @param attrs collection with the arguments to add
     */
    public void addAttributes(Collection<BaseAttribute> attrs) {
        this.attributes.addAll(attrs);
    }

    public String getTablename() {
        return tablename;
    }

    public List<BaseAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Checks if the entity is weak
     *
     * @return true if weak
     */
    public boolean isWeak() {
        for (BaseAttribute cur : attributes)
            if (cur.isPrimaryKey())
                if (!cur.isForeignKey())
                    return false;

        return true;
    }

    /**
     * Ueberpruefen, ob es sich um eine 1:1 Relation handelt.
     *
     * @param fk ForeignKey
     * @param tables Alle Tabellen
     * @return True wenn es eine 1:1 Beziehung ist
     */
    public boolean isOneToOne(ForeignKey fk, List<Table> tables) {

        boolean oneToOneRelation = false;

        //Die Entitaet muss schwach sein und der ForeignKey muss auch PrimaryKey sein
        if (this.isWeak() && fk.isPrimaryKey()) {

            //Die andere Tabelle (auf die der ForeignKey verweist) wird gesucht
            Table foreignTable = null;
            for (Table table1 : tables)
                if (table1.getTablename().equals(fk.getForeignTable()))
                    foreignTable = table1;

            //Wenn sie gefunden wurde (nicht null ist), dann werden die Attribute durchsucht
            //Das Attribut muss ein Primary Key sein, und es muss der einzige sein.
            if (foreignTable != null) {
                for (BaseAttribute attr : foreignTable.getAttributes())
                    if (attr.isPrimaryKey())
                        if (attr.getOriginalValue().equals(fk.getForeignAttribute()))
                            oneToOneRelation = true;
                        else
                            oneToOneRelation = false;


            }

            //In der aktuellen Tabelle darf es nur den ForeignKey als PrimaryKey geben
            for (BaseAttribute attr: this.getAttributes())
                if (attr.isPrimaryKey()) {
                    if (!attr.getOriginalValue().equals(fk.getOriginalValue()))
                        oneToOneRelation = false;
                }

        }

        return oneToOneRelation;

    }

}