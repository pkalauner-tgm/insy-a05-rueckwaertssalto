package at.kalaunerritter.rueckwaertssalto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles the database connection
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141208.1
 */
public class DBConnection implements Closeable {
    private static final Logger LOG = LogManager.getLogger(DBConnection.class);

    private Exporter exporter;
    private Connection con;

    /**
     * Initializes DBConnection with an {@code Exporter} instance
     *
     * @param exporter the {@code Exporter} instance
     */
    public DBConnection(Exporter exporter) {
        this.exporter = exporter;
    }

    /**
     * Connects to a database
     *
     * @throws ClassNotFoundException if driver failed to load
     * @throws java.sql.SQLException           if there was a connection error
     */
    public void connect() throws ClassNotFoundException, SQLException {
        LOG.info("Connecting to database");
        Class.forName("com.mysql.jdbc.Driver");
        this.con = DriverManager.getConnection("jdbc:mysql://" + exporter.getHostname() + "/" + exporter.getDatabase(), exporter.getUsername(), exporter.getPassword());
    }

    /**
     * Creates the statement
     *
     * @return ResultSet with all fields
     * @throws java.sql.SQLException if something is wrong with our query
     * @throws java.io.IOException  if writing to file failed
     */
    public ResultSet query() throws SQLException, IOException {
        LOG.info("Executing query");
        // build query
        // Hinweis: Der Grund warum ich keine PreparedStatements verwende ist, dass bei PreparedStatements die Felder, die selected werden sollen, nicht mit einem Platzhalter freigehalten werden koennen.
        String query = StatementBuilder.createBuilder(exporter.getTablename()).setFields(exporter.getFields()).setCondition(exporter.getCondition()).setOrderBy(exporter.getSortingField()).setOrderDir(exporter.getSortingDir()).build();
        LOG.debug("Query: " + query);
        return con.createStatement().executeQuery(query);
    }


    /**
     * Closes the connection
     */
    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            LOG.error("Could not close connection");
        }
    }
}