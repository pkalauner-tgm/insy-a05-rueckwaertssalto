package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.rueckwaertssalto.attributes.Attribute;
import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
import at.kalaunerritter.rueckwaertssalto.attributes.ForeignKey;
import at.kalaunerritter.rueckwaertssalto.attributes.PrimaryKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads all attributes of a table
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141226.1
 */
public class AttributeLoader {
    private static final Logger LOG = LogManager.getLogger(AttributeLoader.class);

    private AttributeLoader() {
    }


    /**
     * Loads all attributes of the given table
     *
     * @param con       the connection with a specific database
     * @param tablename the name of the table
     * @return Collection with the attributes of the given table
     */
    public static Collection<BaseAttribute> loadAttributes(Connection con, String tablename) {
        try {
            DatabaseMetaData dbmd = con.getMetaData();
            Map<String, BaseAttribute> map = new HashMap<>();

            // gets all columns of the table
            ResultSet rs = dbmd.getColumns(null, null, tablename, null);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                map.put(columnName, new Attribute(columnName));
            }


            // gets all primary keys of the table
            ResultSet pks = dbmd.getPrimaryKeys(con.getCatalog(), null, tablename);
            while (pks.next()) {
                String columnName = pks.getString("COLUMN_NAME");
                // get the attribute out of the map, decorate it and put it back into the map
                BaseAttribute attr = map.get(columnName);
                map.put(columnName, new PrimaryKey(attr));
            }

            // gets all foreign keys of the table
            ResultSet foreignKeys = dbmd.getImportedKeys(con.getCatalog(), null, tablename);
            while (foreignKeys.next()) {
                String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                String pkTableName = foreignKeys.getString("PKTABLE_NAME");
                String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                // get the attribute out of the map, decorate it and put it back into the map
                BaseAttribute attr = map.get(fkColumnName);
                map.put(fkColumnName, new ForeignKey(pkTableName, pkColumnName, attr));
            }
            return map.values();
        } catch (SQLException e) {
            LOG.error("Error while loading Attributes", e);
        }
        return null;
    }
}