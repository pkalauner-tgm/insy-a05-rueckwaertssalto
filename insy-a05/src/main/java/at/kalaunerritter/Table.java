package at.kalaunerritter;

import at.kalaunerritter.attributes.BaseAttribute;

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

    public Table(String tablename) {
        this.attributes = new ArrayList<>();
        this.tablename = tablename;
    }

    public void addAttribute(BaseAttribute attribute) {
        this.attributes.add(attribute);
    }

    public void addAttributes(Collection<BaseAttribute> attrs) {
        this.attributes.addAll(attrs);
    }

    public String getTablename() {
        return tablename;
    }

    public List<BaseAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        return !(tablename != null ? !tablename.equals(table.tablename) : table.tablename != null);

    }

    @Override
    public int hashCode() {
        return tablename != null ? tablename.hashCode() : 0;
    }
}