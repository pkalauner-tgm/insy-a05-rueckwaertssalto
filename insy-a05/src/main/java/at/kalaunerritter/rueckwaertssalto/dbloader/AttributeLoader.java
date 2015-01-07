package at.kalaunerritter.rueckwaertssalto.dbloader;

import at.kalaunerritter.rueckwaertssalto.attributes.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    private Connection con;
    private DatabaseMetaData dbmd;

    /**
     * Initializes the AttributeLoader with the given Connection
     *
     * @param con the connection with a specific database
     */
    public AttributeLoader(Connection con) {
        this.con = con;
        try {
            this.dbmd = con.getMetaData();
        } catch (SQLException e) {
            LOG.error("Could not get MetaData", e);
        }
    }

    /**
     * Loads all attributes of the given table
     *
     * @param tablename the name of the table
     * @return Collection with the attributes of the given table
     */
    public Collection<BaseAttribute> loadAttributes(String tablename) {
        LOG.debug("Getting attributes for table " + tablename);
        try {
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

            // get unique attributes of the table
            ResultSet uniques = dbmd.getIndexInfo(con.getCatalog(), null, tablename, true, true);
            while (uniques.next()) {
                String indexName = uniques.getString("INDEX_NAME");
                if (indexName == null)
                    continue;
                String columnName = uniques.getString("COLUMN_NAME");
                BaseAttribute attr = map.get(columnName);
                if (!attr.isPrimaryKey() && !attr.isForeignKey())
                    map.put(columnName, new Unique(attr));
            }
            return map.values();
        } catch (SQLException e) {
            LOG.error("Error while loading Attributes", e);
        }
        return null;
    }
}