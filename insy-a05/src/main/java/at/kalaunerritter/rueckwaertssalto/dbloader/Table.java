package at.kalaunerritter.rueckwaertssalto.dbloader;

import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
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
            if (cur instanceof PrimaryKey)
                return false;
        return true;
    }
}