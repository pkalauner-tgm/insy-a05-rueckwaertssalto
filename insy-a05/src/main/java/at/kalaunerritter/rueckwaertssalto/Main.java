package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.rueckwaertssalto.connection.DBConnectionCreator;
import at.kalaunerritter.rueckwaertssalto.connection.MySQLConnectionCreator;
import at.kalaunerritter.rueckwaertssalto.dbloader.TableCreator;
import org.apache.commons.cli.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;

/**
 * Beinhaelt die main-Methode, wobei die Argumente verarbeitet werden und das Programm ausgefuehrt wird.
 * Dabei wird eine neue Verbindung zur Datenbank aufgebaut, die SQL-Abfrage durchegfuehrt und das Ergebnis ausgegeben.
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class Main {

    private static Logger logger = LogManager.getLogger(Main.class.getName());

    private static DBConnectionCreator connection;

    /**
     * Aufruf der Verarbeitung der eingegebenen Argumente.
     * Ausfuehrend des SQL-Statements + Ausgabe.
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {

        if (parseArgs(args)) {

            Connection con = connection.createConnection();

            TableCreator tc = new TableCreator(con);
            tc.loadTables();
            MyFileWriter fw = new MyFileWriter(tc.getTables());
            fw.writeRMToFile();
            fw.writeERDToFile();
        }
    }

    /**
     * Verarbeiten der eingegebenen Argumente.
     * Initialisieren des DBConnectionCreators, des DBSelectCreators und des Displays.
     *
     * @param args Kommandozeilenagrumente
     * @return Ob Argumente korrekt
     */
    public static boolean parseArgs(String[] args) {

        //Initialisieren der Optionen, HelpFormatter fuer Hilfe, CommandLineParser fuer das Parsen der Argumente
        Options options = getOptions();
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();

        try {

            CommandLine cmd = parser.parse(options, args);

            //Connection initialisieren (In diesem Fall fuer MySQL)
            connection = new MySQLConnectionCreator();

            //Eingegebene Argumente fuer den Connection-Creator setzen
            connection.setHost(cmd.getOptionValue("h"))
                    .setDatabase(cmd.getOptionValue("d"))
                    .setUser(cmd.getOptionValue("u"))
                    .setPassword(cmd.getOptionValue("p"));

            return true;

        } catch (ParseException e) {
            //Bei einem Fehler wahrend des Parsens wird eine Fehlermeldung ausgegeben
            logger.error(e.getMessage());
            hf.printHelp("java -jar Rueckwaerts.jar", options);
            return false;
        }

    }

    /**
     * Die Optionen zur Verarbeitung der Kommandozeilenargumente
     *
     * @return Optionen
     */
    @SuppressWarnings("AccessStaticViaInstance")
    private static Options getOptions() {

        //Options-Objekt initialisieren und anschliessend alle Optionen hinzufuegen
        Options options = new Options();

        options.addOption(OptionBuilder
                .hasArg(true)
                .withDescription("Hostname des DBMS. Standard: localhost")
                .create("h"));

        options.addOption(OptionBuilder
                .hasArg(true)
                .withDescription("Benutzername. Standard: Benutzername des im Betriebssystem angemeldeten Benutzers")
                .create("u"));

        options.addOption(OptionBuilder
                .hasArg(true)
                .withDescription("Passwort. Standard: keines")
                .create("p"));

        options.addOption(OptionBuilder
                .hasArg(true)
                .isRequired()
                .withDescription("Name der Datenbank")
                .create("d"));

        return options;

    }

    public static DBConnectionCreator getConnection() {
        return connection;
    }


}

