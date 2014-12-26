package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.MyFileWriter;
import at.kalaunerritter.TableCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Main class <br>
 * Start of the program
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141207.1
 */
public class Main {
    /**
     * Entry point of the program
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/A01", "root", "");
        TableCreator tc = new TableCreator(con);
        tc.loadTables();

        MyFileWriter.writeRmToFile(tc.getTables());
    }
}