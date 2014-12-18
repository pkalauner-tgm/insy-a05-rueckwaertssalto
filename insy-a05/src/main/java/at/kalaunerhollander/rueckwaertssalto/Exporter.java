package at.kalaunerhollander.rueckwaertssalto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Exporter
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141207.1
 */
public class Exporter {
    private static final Logger LOG = LogManager.getLogger(Exporter.class);

    private String hostname;
    private String username;
    private String password;
    private String database;
    private String sortingField;
    private String sortingDir;
    private String condition;
    private String tablename;
    private char delimiter;
    private String[] fields;
    private String outputFileName;

    /**
     * Initializes the exporter
     */
    public Exporter() {
        this.hostname = "localhost";
        this.username = System.getProperty("user.name"); // gets name of logged in user
        this.sortingDir = "ASC";
        this.delimiter = ';';
        this.password = "";
    }

    /**
     * exports content
     */
    public void export() {
        LOG.info("Starting export");
        try (DBConnection connection = new DBConnection(this)) {
            connection.connect();
            this.writeToFileOrConsole(connection.query());
        } catch (ClassNotFoundException e) {
            LOG.error("Could not load JDBC driver", e);
        } catch (SQLException e) {
            LOG.error("SQL Error: " + e.getMessage());
        } catch (IOException e) {
            LOG.error("Error while writing to file", e);
        }
    }

    /**
     * Writes to file or console
     * <p/>
     * Note: This method is public because of testing purposes.
     *
     * @param rs result set of the SQL query
     * @throws java.io.IOException  if writing to file failed
     * @throws java.sql.SQLException if something is wrong with the result set
     */
    public void writeToFileOrConsole(ResultSet rs) throws IOException, SQLException {
        LOG.info("Writing to file or console");
        PrintWriter writer;
        // if output file name is set, write to file
        // otherwise write to console
        if (this.getOutputFileName() != null) {
            File f = new File(this.getOutputFileName());
            // if file exists already, delete it
            if (f.exists()) {
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }
            writer = new PrintWriter(new FileWriter(this.getOutputFileName()));
        } else
            writer = new PrintWriter(System.out);

        // get the number of columns
        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= cols; i++) {
                // Only append delimiter if it is not the last column
                writer.print(rs.getString(i) + (i < cols ? this.getDelimiter() : ""));
            }
            writer.println();
        }
        writer.flush();
    }

    /**
     * @return hostname of the DBMS
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return name of the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @return sorting field
     */
    public String getSortingField() {
        return sortingField;
    }

    /**
     * @return sorting direction
     */
    public String getSortingDir() {
        return sortingDir;
    }

    /**
     * @return SQL condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @return delimiter
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * @return fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * @return name of the outputfile
     */
    public String getOutputFileName() {
        return outputFileName;
    }

    /**
     * @return name of the table
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename name of the table
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    /**
     * @param hostname hostname of the DBMS
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param database name of the database
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @param sortingField sorting field
     */
    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    /**
     * @param sortingDir sorting direction
     */
    public void setSortingDir(String sortingDir) {
        this.sortingDir = sortingDir;
    }

    /**
     * @param condition SQL condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @param delimiter delimiter
     */
    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * @param fields fields
     */
    public void setFields(String[] fields) {
        this.fields = fields;
    }

    /**
     * @param outputFileName name of the output file
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}