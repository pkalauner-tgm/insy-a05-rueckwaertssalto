package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.Table;
import at.kalaunerritter.TableCreator;

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
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/A01", "root", "");
        TableCreator tc = new TableCreator(con);
        tc.loadTables();

        for (Table cur : tc.getTables()) {
            System.out.println(cur.getTablename() + ":");
            System.out.println(cur.getAttributes().toString());
            System.out.println();
        }
    }
}