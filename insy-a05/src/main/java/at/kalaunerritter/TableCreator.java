package at.kalaunerritter;

import at.kalaunerritter.rueckwaertssalto.AttributeLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets all tables and puts them into a List
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141226.1
 */
public class TableCreator {
    private static final Logger LOG = LogManager.getLogger(TableCreator.class);
    private Connection con;
    private DatabaseMetaData dbmd;

    private List<Table> tables;

    public TableCreator(Connection con) {
        this.tables = new ArrayList<>();
        this.con = con;
        try {
            this.dbmd = con.getMetaData();
        } catch (SQLException e) {
            LOG.error("Could not get MetaData", e);
        }
    }

    public void loadTables() {
        try {
            // Get all tables
            ResultSet rs = dbmd.getTables(null, null, "%", null);
            while (rs.next()) {
                String tablename = rs.getString(3);
                Table t = new Table(tablename);
                t.addAttributes(AttributeLoader.loadAttributes(con, tablename));
                this.tables.add(t);
            }

        } catch (SQLException e) {
            LOG.error("Error while getting Tables", e);
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}